package com.sleepkqq.sololeveling.ai.kafka;

import com.sleepkqq.sololeveling.ai.service.ChatService;
import com.sleepkqq.sololeveling.avro.task.GenerateTasksEvent;
import com.sleepkqq.sololeveling.avro.task.SaveTasksEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateTasksConsumer {

  private final ChatService chatService;
  private final SaveTasksProducer saveTasksProducer;

  @KafkaListener(topics = "generate-tasks", groupId = "task-group")
  public void listen(GenerateTasksEvent event) {
    log.info(">> Start tasks generation | transactionId={}", event.getTransactionId());
    var generatedTasks = StreamEx.of(event.getInputs())
        .parallel()
        .map(input -> chatService.generateTask(input.getTopics(), input.getRarity()))
        .toList();

    log.info("<< Tasks successfully generated | transactionId={}", event.getTransactionId());
    saveTasksProducer.send(new SaveTasksEvent(
        event.getTransactionId(),
        event.getUserId(),
        generatedTasks
    ));
  }
}
