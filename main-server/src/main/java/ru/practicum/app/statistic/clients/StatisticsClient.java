//package ru.practicum.app.statistic.clients;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.DefaultUriBuilderFactory;
//
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Map;
//
//
//@Service
//public class StatisticsClient {
//
//    @Autowired
//    public StatisticsClient(@Value("${ewm-stats-service.url}") String url, RestTemplateBuilder builder) {
//        super(
//                builder
//                        .uriTemplateHandler(new DefaultUriBuilderFactory(url))
//                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
//                        .build()
//        );
//    }
//
//    public ResponseEntity<Object> saveHit(EndpointHit endpointHit) {
//        return post("/hit", endpointHit);
//    }
//
//    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end,
//                                           List<String> uris, Boolean unique) {
//        Map<String, Object> parameters = Map.of(
//                "start", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                "end", end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                "uris", uris.get(0),
//                "unique", unique
//        );
//        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
//    }
////    private final RestTemplate rest;
////
//////    @Autowired
//////    public StatisticsClient(RestTemplate rest) {
//////        this.rest = rest;
//////    }
////
////    @Autowired
////    public StatisticsClient(@Value("${ewm-stats-service.url}") String statServerUrl, RestTemplateBuilder builder) {
////        rest = builder.uriTemplateHandler(new DefaultUriBuilderFactory(statServerUrl)).build();
////    }
////
////    public void sendHit(StatHitDto statHitDto) {
////        rest.postForEntity("/hit", statHitDto, Object.class);
////    }
////
////    public StatEntry getEventStat(int id, String start, String end) {
////        StatEntry[] result = rest.getForObject("/stats?start=" + URLEncoder.encode(start, StandardCharsets.UTF_8) +
////                "&end=" + URLEncoder.encode(end, StandardCharsets.UTF_8) +
////                "&uris=" + URLEncoder.encode("/events/" + id, StandardCharsets.UTF_8), StatEntry[].class);
//////        if (result == null) {
//////            throw new EntryNotFoundException("Отсутствует статистика");
//////        }
////        return result[0];
////    }
//
//
//}
