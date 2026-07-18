package com.interviewportal.ai;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewportal.entity.Difficulty;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.api.url}")
    private String apiUrl;

    @Override
    public List<String> generateQuestions(
            String domain,
            Difficulty difficulty) {

        String prompt =
        		"Generate exactly 5 interview questions on "
        				+ domain
        				+ " for "
        				+ difficulty
        				+ " level. Return one question per line and nothing else.";

        Message message =
                new Message("user", prompt);

        OpenRouterRequest request =
                new OpenRouterRequest(
                        "openrouter/auto",
                        List.of(message));

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("HTTP-Referer", "http://localhost:8080");
        headers.set("X-Title", "AI Interview Portal");

   
        HttpEntity<OpenRouterRequest> entity =
                new HttpEntity<>(request, headers);

        try {

        	System.out.println("URL : " + apiUrl);
        	System.out.println("Model : google/gemma-3-4b-it:free");
        	System.out.println("API Key Starts With : " + apiKey.substring(0,10));
        	
        	//String url = apiUrl;

        	ResponseEntity<String> response =
        	        restTemplate.postForEntity(
        	                apiUrl,
        	                entity,
        	                String.class);

            System.out.println("Response Status : "
                    + response.getStatusCode());

            System.out.println("Response Body : "
                    + response.getBody());

            
            JsonNode root =
                    objectMapper.readTree(response.getBody());

            String content =
                    root.path("choices")
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText();

            return Arrays.stream(content.split("\n"))
            		.filter(line -> !line.trim().isEmpty())
            		.map(line-> line.replaceFirst("^\\d+\\.\\s*", ""))
            		.toList();

        } catch (Exception e) {

            System.out.println("============== ERROR ==============");

            if (e instanceof HttpClientErrorException ex) {
                System.out.println("Status Code : " + ex.getStatusCode());
                System.out.println("Response Body : " + ex.getResponseBodyAsString());

                return List.of(
                        ex.getStatusCode().toString(),
                        ex.getResponseBodyAsString()
                );
            }

            e.printStackTrace();

            return List.of(e.getMessage());
        }
    }

    @Override
    public String evaluateAnswer(String prompt) {

        try {

            Message message = new Message("user", prompt);

            OpenRouterRequest request =
                    new OpenRouterRequest(
                            "openrouter/auto",
                            List.of(message));

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.set("HTTP-Referer", "http://localhost:8080");
            headers.set("X-Title", "AI Interview Portal");

            HttpEntity<OpenRouterRequest> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            apiUrl,
                            entity,
                            String.class);

            JsonNode root = objectMapper.readTree(response.getBody());

            return root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

         } catch (HttpClientErrorException ex) {

        	    System.out.println("=================================");
        	    System.out.println("Status Code : " + ex.getStatusCode());
        	    System.out.println("Response Body : ");
        	    System.out.println(ex.getResponseBodyAsString());
        	    System.out.println("=================================");

        	    return ex.getResponseBodyAsString();
        	} catch (Exception e) {

            e.printStackTrace();

            return e.getMessage();
        }
    }
}