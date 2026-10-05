package org.code.bluetick.ai.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "feature.ai.enabled", havingValue = "true")
@Slf4j
public class SpringAIModelProvider implements AIModelProvider {

    private final ChatClient chatClient;
    private final ChatModel chatModel;

    public SpringAIModelProvider(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }

    @Override
    public String generate(String systemPrompt, String userPrompt) {
        log.debug("Generating AI response via {}", providerName());
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();
    }

    @Override
    public String providerName() {
        return chatModel.getClass().getSimpleName()
                .replace("ChatModel", "")
                .toLowerCase();
    }
}
