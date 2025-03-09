package com.sleepkqq.sololeveling.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sleepkqq.sololeveling.task.kafka.SaveTask;
import com.sleepkqq.sololeveling.task.kafka.TaskRarity;
import com.sleepkqq.sololeveling.task.kafka.TaskTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sleepkqq.sololeveling.ai.prompt.TaskPrompts.GENERATE_TASK_PROMPT;

@Slf4j
@Service
public class ChatService {

  private final ChatClient chatClient;
  private final ObjectMapper objectMapper;

  public ChatService(
      ChatClient.Builder chatClientBuilder,
      ObjectMapper objectMapper
  ) {
    this.chatClient = chatClientBuilder.build();
    this.objectMapper = objectMapper;
  }

  @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
  public SaveTask generateTask(List<TaskTopic> topics, TaskRarity rarity) {
    var json = chatClient.prompt(
            new Prompt(
                new UserMessage(
                    GENERATE_TASK_PROMPT.formatted(topics, rarity)
                ),
                ChatOptions.builder()
                    .temperature(1.5)
                    .build()
            )
        )
        .call()
        .content();

    return getTaskOrThrow(json, topics, rarity);
  }

  private SaveTask getTaskOrThrow(String json, List<TaskTopic> topics, TaskRarity rarity) {
    try {
      var task = objectMapper.readValue(json, SaveTask.class);
      task.setRarity(rarity);
      task.setTopics(topics);

      return task;
    } catch (Exception e) {
      log.error("Error while parsing json: {}", json);
      throw new IllegalArgumentException(e);
    }
  }
}