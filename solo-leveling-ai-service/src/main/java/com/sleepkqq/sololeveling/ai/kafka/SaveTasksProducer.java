package com.sleepkqq.sololeveling.ai.kafka;

import com.sleepkqq.sololeveling.avro.task.SaveTasksEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveTasksProducer {

  private static final String TOPIC = "save-tasks";

  private final KafkaTemplate<String, SaveTasksEvent> kafkaTemplate;

  public void saveTasks(SaveTasksEvent event) {
    kafkaTemplate.send(TOPIC, event);
    log.info("<< Save tasks event sent | transactionId={}", event.getTransactionId());
  }
}
