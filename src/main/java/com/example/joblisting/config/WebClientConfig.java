package com.example.joblisting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.http.client.HttpClient;


import java.util.concurrent.TimeUnit;


@Configuration
public class WebClientConfig {

	@Value("${external.service.timeout.connection}")
	private int connectionTimeout;

	@Value("${external.service.timeout.read}")
	private int readTimeout;

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create().tcpConfiguration(tcpClient ->
                                        tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)))
                                )
                ));
    }
}