//package ru.practicum.app.statistic.clients;
//
//import org.springframework.http.*;
//import org.springframework.lang.Nullable;
//import org.springframework.web.client.HttpStatusCodeException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Map;
//
//public class BaseClient {
//    protected final RestTemplate rest;
//
//    public BaseClient(RestTemplate rest) {
//        this.rest = rest;
//    }
//
//    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
//        return prepareRequest(HttpMethod.GET, path, parameters, null);
//    }
//
//    protected <T> ResponseEntity<Object> post(String path, T body) {
//        return prepareRequest(HttpMethod.POST, path, null, body);
//    }
//
//    private <T> ResponseEntity<Object> prepareRequest(HttpMethod method, String path,
//                                                      @Nullable Map<String, Object> parameters,
//                                                      @Nullable T body) {
//        ResponseEntity<Object> responseEntity;
//        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
//        try {
//            if (parameters != null) {
//                responseEntity = rest.exchange(path, method, requestEntity, Object.class, parameters);
//            } else {
//                responseEntity = rest.exchange(path, method, requestEntity, Object.class);
//            }
//        } catch (HttpStatusCodeException e) {
//            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
//        }
//        return prepareResponse(responseEntity);
//    }
//
//    private static ResponseEntity<Object> prepareResponse(ResponseEntity<Object> responseEntity) {
//        if (responseEntity.getStatusCode().is2xxSuccessful()) return responseEntity;
//        ResponseEntity.BodyBuilder builder = ResponseEntity.status(responseEntity.getStatusCode());
//        if (responseEntity.hasBody()) return builder.body(responseEntity.getBody());
//        return builder.build();
//    }
//
//    private HttpHeaders defaultHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//        return headers;
//    }
//}
