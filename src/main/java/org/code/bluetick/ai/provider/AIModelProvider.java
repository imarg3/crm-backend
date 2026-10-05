package org.code.bluetick.ai.provider;

/**
 * Abstraction over the underlying AI model. Swap providers by changing the
 * Spring AI starter dependency (openai / anthropic / ollama) and updating
 * the corresponding spring.ai.* properties — no code changes required.
 */
public interface AIModelProvider {

    /**
     * Generates a response given a system instruction and a user message.
     */
    String generate(String systemPrompt, String userPrompt);

    /**
     * Returns a human-readable name of the active provider (e.g. "openai").
     */
    String providerName();
}
