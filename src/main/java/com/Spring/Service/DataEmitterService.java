package com.Spring.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ch.rasc.sse.eventbus.SseEvent;

@Service
@Transactional
public class DataEmitterService {

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	private List<String> list = new ArrayList<String>();
	private final static Random random = new Random();

	public void addListInfo(String id) {
		list.add(id);
	}

	private String randomData() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < 5; i++) {
			sb.append(random.nextInt(31));
			sb.append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	@Scheduled(initialDelay = 2000, fixedRate = 5_000)
	public void sendData() {

		for (String str : list) {
			if (list.indexOf(str) == 0) // this will help different Client

				// this.eventPublisher.publishEvent(SseEvent.builder().addClientId(str).event("DataOnline").data("Goa").build());//
				// You Call also pass with event name

				this.eventPublisher.publishEvent(SseEvent.builder().addClientId(str).data(randomData()).build()); // Default
																								// Event
			// name
			// (message)
			else
				// this.eventPublisher.publishEvent(SseEvent.builder().addClientId(str).event("DataOnline").data("Goa").build());//
				// You Call also pass with event name
				this.eventPublisher.publishEvent(SseEvent.builder().addClientId(str).data(randomData()).build()); // Default
																													// Event
			// name
			// (message)
		}

	}

}
