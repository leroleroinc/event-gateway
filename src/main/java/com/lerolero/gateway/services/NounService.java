package com.lerolero.gateway.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import java.util.function.Consumer;

@Service
public class NounService {

//	@Autowired
//	@Qualifier("nounsWebClient")
//	private WebClient webClient;

	@Autowired
	private StreamBridge streamBridge;
	private WordSink nounSink = new WordSink();

	public Flux<String> randomNounList(Integer size) {
//		return webClient.get()
//			.uri("/nouns?size=" + size)
//			.retrieve()
//			.bodyToFlux(String.class);
		Message<String> nounCount = MessageBuilder.withPayload(size.toString()).build();
		streamBridge.send("nounsupplier-out-0", nounCount);
		System.out.println("GATEWAY: Producing " + size);
		return Flux.create(nounSink).take(size);
	}

//	public Flux<String> randomNounEvents(Integer interval) {
//		return webClient.get()
//			.uri("/nouns/events?interval=" + interval)
//			.retrieve()
//			.bodyToFlux(String.class);
//	}

	@Bean
	public Consumer<String> nounconsumer() {
		for (int i = 0; i < 200; i++) System.out.println("GATEWAY CONSUMER");
		return noun -> {
			System.out.println("GATEWAY: Consuming " + noun);
			nounSink.produce(noun);
		};
	}

	private class WordSink implements Consumer<FluxSink<String>> {
		private FluxSink<String> sink;
		@Override
		public void accept(FluxSink<String> sink) {
			this.sink = sink;
		}
		public void produce(String word) {
			this.sink.next(word);
		}
	}

}
