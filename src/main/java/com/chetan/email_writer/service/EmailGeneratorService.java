package com.chetan.email_writer.service;

import com.chetan.email_writer.entity.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private static final String API_PATH = "/v1beta/models/gemini-2.0-flash:generateContent";

    public EmailGeneratorService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getEmailReply(EmailRequest emailRequest) {
        try {
            String prompt = buildPrompt(emailRequest);

            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", prompt)
                                    )
                            )
                    )
            );

            String response = webClient.post()
                    .uri(uriBuilder -> uriBuilder.path(API_PATH)
                            .queryParam("key", geminiApiKey)
                            .build())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractResponse(response);

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 429) {
                // Rate limit exceeded
                return "Sorry, as we are using the base/free model of Google Gemini, the rate limit has been exceeded. Please try again after some time.";
            } else {
                return "Error from Google API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
            }
        } catch (Exception e) {
            return "Error generating reply: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        String tonePart = (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty())
                ? "Use a " + emailRequest.getTone() + " tone. "
                : "";
        return "Generate a professional email reply. " + tonePart + "Do not include a subject line. Original email: " + emailRequest.getEmailContent();
    }

    private String extractResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode candidates = rootNode.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                return candidates.get(0).path("content").path("parts").get(0).path("text").asText();
            }
            return "No valid response from API";
        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
    }
}
