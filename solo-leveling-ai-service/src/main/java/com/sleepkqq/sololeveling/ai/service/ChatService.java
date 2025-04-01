package com.sleepkqq.sololeveling.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sleepkqq.sololeveling.avro.task.GenerateTask;
import com.sleepkqq.sololeveling.avro.task.SaveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import static com.sleepkqq.sololeveling.ai.prompt.TaskPrompts.GENERATE_TASK_PROMPT;
import static java.lang.String.format;

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
  public SaveTask generateTask(GenerateTask generateTask) {
    var json = chatClient.prompt(
            new Prompt(
                new UserMessage(
                    format(GENERATE_TASK_PROMPT, generateTask.getTopics(), generateTask.getRarity())
                ),
                ChatOptions.builder()
                    .temperature(1.5)
                    .build()
            )
        )
        .call()
        .content();

    return getTaskOrThrow(json, generateTask);
  }

  private SaveTask getTaskOrThrow(String json, GenerateTask generateTask) {
    try {
      var task = objectMapper.readValue(parseJson(json), SaveTask.class);
      task.setTaskId(generateTask.getTaskId());
      task.setRarity(generateTask.getRarity());
      task.setTopics(generateTask.getTopics());

      return task;
    } catch (Exception e) {
      log.error("Error while parsing json: {}", json);
      throw new IllegalArgumentException(e);
    }
  }

  private String parseJson(String json) {
    if (json.startsWith("```json")) {
      json = json.substring(7);
    }

    if (json.endsWith("```")) {
      json = json.substring(0, json.length() - 3);
    }

    return json.trim();
  }
}