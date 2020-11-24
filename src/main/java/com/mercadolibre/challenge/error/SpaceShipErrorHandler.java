package com.mercadolibre.challenge.error;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.IOUtils;


public class SpaceShipErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if (response.getStatusCode() != HttpStatus.OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                String body = IOUtils.toString(response.getBody(), StandardCharsets.UTF_8.name());
                Map map = objectMapper.readValue(body, Map.class);
                throw new SpaceShipConflictError(body, response.getStatusCode());
            }
        }
}}
