package com.lerolero.gateway.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;

@Configuration
public class WebClientConfig {

//	@Autowired
//	@Bean
//	@Qualifier("loadBalancedWebClientBuilder")
//	public WebClient.Builder loadBalancedWebClientBuilder(ReactorLoadBalancerExchangeFilterFunction lbFilter) {
//		return WebClient.builder().filter(lbFilter);
//	}

	@Autowired
	@Bean
	@Qualifier("adverbsWebClient")
	public WebClient adverbsWebClient(ReactorLoadBalancerExchangeFilterFunction lbFilter, @Value("${webservice.adverbs.baseurl}") String baseURL) {
		return WebClient.builder().filter(lbFilter).baseUrl(baseURL).build();
	}

	@Autowired
	@Bean
	@Qualifier("verbsWebClient")
	public WebClient verbsWebClient(ReactorLoadBalancerExchangeFilterFunction lbFilter, @Value("${webservice.verbs.baseurl}") String baseURL) {
		return WebClient.builder().filter(lbFilter).baseUrl(baseURL).build();
	}

	@Autowired
	@Bean
	@Qualifier("nounsWebClient")
	public WebClient nounsWebClient(ReactorLoadBalancerExchangeFilterFunction lbFilter, @Value("${webservice.nouns.baseurl}") String baseURL) {
		return WebClient.builder().filter(lbFilter).baseUrl(baseURL).build();
	}

}
