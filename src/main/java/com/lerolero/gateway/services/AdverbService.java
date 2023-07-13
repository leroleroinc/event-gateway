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
public class AdverbService {

	@Autowired
	private StreamBridge streamBridge;
	private StringSink sink = new StringSink();
	private Flux<String> stream = Flux.create(sink).share();

	public Flux<String> randomAdverbList(Integer size) {
		Message<String> adverbCount = MessageBuilder.withPayload(size.toString()).build();
		streamBridge.send("adverbsupplier-out-0", adverbCount);
		System.out.println("GATEWAY.ADVERB: Producing " + size);
		return stream.take(size);
	}

	@Bean
	public Consumer<String> adverbconsumer() {
		return adverb -> {
			System.out.println("GATEWAY.ADVERB: Consuming " + adverb);
			sink.produce(adverb);
		};
	}

	private class StringSink implements Consumer<FluxSink<String>> {
		private FluxSink<String> sink;
		@Override
		public void accept(FluxSink<String> sink) {
			this.sink = sink;
		}
		public void produce(String s) {
			this.sink.next(s);
		}
	}

}
