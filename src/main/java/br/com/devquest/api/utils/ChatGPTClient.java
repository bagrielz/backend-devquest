package br.com.devquest.api.utils;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class ChatGPTClient {

  private ChatClient chatClient;
  private String exercisePrompt;

  public ChatGPTClient(ChatClient.Builder chatClientBuilder,
                       String exercisePrompt) {

    this.chatClient = chatClientBuilder.build();
    this.exercisePrompt = exercisePrompt;
  }

  public String generateExerciseString(Technology technology, Difficulty difficulty) {
    String clonedPrompt = clonePrompt(exercisePrompt, technology, difficulty);
    return chatClient
            .prompt()
            .user(clonedPrompt)
            .call()
            .content();
  }

  private String clonePrompt(String prompt, Technology technology, Difficulty difficulty) {
    var newPrompt = prompt;
    return String.format(newPrompt, technology, difficulty);
  }

}
