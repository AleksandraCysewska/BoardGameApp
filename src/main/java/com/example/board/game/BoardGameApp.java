package com.example.board.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.board.game")
@EntityScan(basePackages = "com.example.board.game.model.entities")
@SpringBootApplication
public class BoardGameApp {

	public static void main(String[] args) {
		SpringApplication.run(BoardGameApp.class, args);
	}
}
