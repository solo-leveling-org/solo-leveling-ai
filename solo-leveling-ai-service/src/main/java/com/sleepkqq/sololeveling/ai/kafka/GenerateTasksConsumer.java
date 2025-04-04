package com.sleepkqq.sololeveling.ai.kafka;

import static com.sleepkqq.sololeveling.avro.constants.KafkaGroupIds.AI_GROUP_ID;
import static com.sleepkqq.sololeveling.avro.constants.KafkaTaskTopics.GENERATE_TASKS_TOPIC;

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

  @KafkaListener(topics = GENERATE_TASKS_TOPIC, groupId = AI_GROUP_ID)
  public void listen(GenerateTasksEvent event) {
    log.info(">> Start tasks generation | transactionId={}", event.getTransactionId());
    var generatedTasks = StreamEx.of(event.getInputs())
        .parallel()
        .map(chatService::generateTask)
        .toList();

    log.info("<< Tasks successfully generated | transactionId={}", event.getTransactionId());
    saveTasksProducer.send(new SaveTasksEvent(
        event.getTransactionId(),
        event.getPlayerId(),
        generatedTasks
    ));
  }
}
