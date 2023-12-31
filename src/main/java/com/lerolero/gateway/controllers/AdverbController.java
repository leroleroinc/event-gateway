package com.lerolero.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;

import reactor.core.publisher.Flux;

import com.lerolero.gateway.services.AdverbService;

@RestController
@RequestMapping("/adverbs")
public class AdverbController {

	@Autowired
	private AdverbService adverbService;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> get(@RequestParam(defaultValue = "1") Integer size) {
		return adverbService.randomAdverbList(size);
	}

}
