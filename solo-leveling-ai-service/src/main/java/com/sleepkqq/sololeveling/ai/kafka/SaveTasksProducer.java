package com.sleepkqq.sololeveling.ai.kafka;

import static com.sleepkqq.sololeveling.avro.constants.KafkaTaskTopics.SAVE_TASKS_TOPIC;

import com.sleepkqq.sololeveling.avro.task.SaveTasksEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveTasksProducer {

  private final KafkaTemplate<String, SaveTasksEvent> kafkaTemplate;

  public void send(SaveTasksEvent event) {
    kafkaTemplate.send(SAVE_TASKS_TOPIC, event);
    log.info("<< Save tasks event sent | transactionId={}", event.getTransactionId());
  }
}
