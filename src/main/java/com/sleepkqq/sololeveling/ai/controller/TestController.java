package com.sleepkqq.sololeveling.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class TestController {

  private final ChatClient chatClient;

  public TestController(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @GetMapping("/test")
  public String test() {
    return chatClient.prompt("Привет! Как дела?")
        .call()
        .content();
  }
}
