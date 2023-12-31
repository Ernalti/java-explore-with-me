package ru.practicum.stat_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statdto.StatDto;

import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

	@Autowired
	public StatsClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
		super(
				builder
						.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
						.requestFactory(HttpComponentsClientHttpRequestFactory::new)
						.build()
		);
	}

	public ResponseEntity<Object> getStatistics(String start, String end, List<String> uris, boolean unique) {
		Map<String, Object> parameters = Map.of(
				"start", start,
				"end", end,
				"uris", uris,
				"unique", unique
		);
		return get("/stats?start={start}&end={end}&unique={unique}", parameters);
	}

	public ResponseEntity<Object> postHit(StatDto stat) {
		return post("/hit", stat);
	}

}
