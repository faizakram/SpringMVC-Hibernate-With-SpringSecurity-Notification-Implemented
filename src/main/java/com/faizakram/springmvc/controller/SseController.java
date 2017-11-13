package com.faizakram.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.faizakram.springmvc.service.DataEmitterService;

import ch.rasc.sse.eventbus.SseEvent;
import ch.rasc.sse.eventbus.SseEventBus;

@RestController
public class SseController {
	@Autowired
	private SseEventBus eventBus;
	
	@Autowired
	private DataEmitterService dataEmitterService;
	
	/*public SseController(SseEventBus eventBus) {
		this.eventBus = eventBus;
	}*/
	
	@RequestMapping(value = "/register/{id}",method = RequestMethod.GET)
	public SseEmitter register(@PathVariable("id") String id, @RequestHeader(value = "User-Agent") String userAgent) {
		dataEmitterService.addListInfo(id);
		System.out.println("TEST");		
		if (userAgent.contains("Edge/")) {
			return this.eventBus.createSseEmitter(id, 180_000L, false, true, SseEvent.DEFAULT_EVENT);
		}
		return this.eventBus.createSseEmitter(id, SseEvent.DEFAULT_EVENT);
	}
	
	

}
