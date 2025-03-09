package com.sleepkqq.sololeveling.ai.kafka;

import com.sleepkqq.sololeveling.ai.service.ChatService;
import com.sleepkqq.sololeveling.task.kafka.GenerateTasksEvent;
import com.sleepkqq.sololeveling.task.kafka.SaveTasksEvent;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateTasksConsumer {

  private final ChatService chatService;
  private final SaveTasksProducer saveTasksProducer;

  @KafkaListener(topics = "generate-tasks", groupId = "task-group")
  public void listen(GenerateTasksEvent event) {
    var generatedTasks = StreamEx.of(event.getInputs())
        .map(input -> chatService.generateTask(input.getTopics(), input.getRarity()))
        .toList();

    saveTasksProducer.saveTasks(new SaveTasksEvent(
        event.getTransactionId(),
        event.getUserId(),
        generatedTasks
    ));
  }
}
