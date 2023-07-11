package com.lerolero.gateway.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class VerbService {

	@Autowired
	@Qualifier("verbsWebClient")
	private WebClient webClient;

//	@Autowired
//	private VerbService(@Value("${webservice.verbs.baseurl}") String baseURL) {
//		this.webClient = WebClient.create(baseURL);
//	}

	public Flux<String> randomVerbList(Integer size) {
		return webClient.get()
			.uri("/verbs?size=" + size)
			.retrieve()
			.bodyToFlux(String.class);
	}

	public Flux<String> randomVerbEvents(Integer interval) {
		return webClient.get()
			.uri("/verbs/events?interval=" + interval)
			.retrieve()
			.bodyToFlux(String.class);
	}

}
