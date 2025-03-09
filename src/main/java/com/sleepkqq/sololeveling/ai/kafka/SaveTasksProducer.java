package com.sleepkqq.sololeveling.ai.kafka;

import com.sleepkqq.sololeveling.task.kafka.SaveTasksEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveTasksProducer {

  private static final String TOPIC = "save-tasks";

  private final KafkaTemplate<String, SaveTasksEvent> kafkaTemplate;

  public void saveTasks(SaveTasksEvent saveTasksEvent) {
    kafkaTemplate.send(TOPIC, saveTasksEvent);
  }
}
