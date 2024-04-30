package com.settlement.settlement.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @Author: tiangua
 * @Date: 2024/3/27 17:44
 */
@Component
public class ApiRequestUtil {
    @Resource
    private RestTemplate restTemplate;

    public String getApiResponse(String url) {
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }

    public String postApiRequest(String url, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Charset", StandardCharsets.UTF_8.name());
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseBody = response.getBody();
        //判断响应数据的字符编码并进行转换
        if (response.getHeaders().getContentType() != null && response.getHeaders().getContentType().getCharset() != null) {
            responseBody = new String(response.getBody().getBytes(response.getHeaders().getContentType().getCharset()), StandardCharsets.UTF_8);
        } else {
            // 如果未指定字符编码，则默认按照ISO-8859-1进行转换
            responseBody = new String(response.getBody().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        return responseBody;
    }
}
