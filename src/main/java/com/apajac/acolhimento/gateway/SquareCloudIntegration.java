package com.apajac.acolhimento.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SquareCloudIntegration {

    private final RestTemplate restTemplate;

    @Value("${squarecloud.api.url}")
    private String apiUrl;

    @Value("${squarecloud.api.token}")
    private String token;

    public Map<String, Object> getExpiracaoInfo() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.GET, request, Map.class);

            Map<String, Object> body = response.getBody();
            if (body == null || !"success".equals(body.get("status"))) {
                throw new IllegalArgumentException("Resposta inválida da API SquareCloud.");
            }

            Map<String, Object> responseMap = (Map<String, Object>) body.get("response");
            Map<String, Object> userMap = (Map<String, Object>) responseMap.get("user");
            Map<String, Object> planMap = (Map<String, Object>) userMap.get("plan");

            Long durationMillis = ((Number) planMap.get("duration")).longValue();
            LocalDate expireDate = Instant.ofEpochMilli(durationMillis).atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate hoje = LocalDate.now();
            long diasRestantes = ChronoUnit.DAYS.between(hoje, expireDate);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Map<String, Object> result = new HashMap<>();
            result.put("expireDate", formatter.format(expireDate));
            result.put("aboutToExpire", diasRestantes <= 30);

            return result;

        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new IllegalArgumentException("Acesso não autorizado. Verifique o token.");
        } catch (Exception ex){
            throw new IllegalArgumentException("Erro ao processar a data de expiração: " + ex.getMessage());
        }
    }

}



