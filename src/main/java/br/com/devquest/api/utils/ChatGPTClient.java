package br.com.devquest.api.utils;

import br.com.devquest.api.enums.Difficulty;
import br.com.devquest.api.enums.Technology;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class ChatGPTClient implements IChatGPTClient {

  private ChatClient chatClient;
  private String exercisePrompt;
  private String questionPrompt;

  public ChatGPTClient(ChatClient.Builder chatClientBuilder,
                       String exercisePrompt,
                       String questionPrompt) {

    this.chatClient = chatClientBuilder.build();
    this.exercisePrompt = exercisePrompt;
    this.questionPrompt = questionPrompt;
  }

  public String generateExerciseString(Technology technology, Difficulty difficulty) {
    String clonedPrompt = clonePrompt(exercisePrompt, technology, difficulty);
    return chatClient
            .prompt()
            .user(clonedPrompt)
            .call()
            .content();
  }

  @Override
  public String generateQuestionString(Technology technology, Difficulty difficulty) {
    String clonedPrompt = clonePrompt(questionPrompt, technology, difficulty);
    return callChatGPTApiAndReturnResult(clonedPrompt);
  }

  private String clonePrompt(String prompt, Technology technology, Difficulty difficulty) {
    var newPrompt = prompt;
    return String.format(newPrompt, technology, difficulty);
  }

  private String callChatGPTApiAndReturnResult(String clonedPrompt) {
    return chatClient
            .prompt()
            .user(clonedPrompt)
            .call()
            .content();
  }

}
