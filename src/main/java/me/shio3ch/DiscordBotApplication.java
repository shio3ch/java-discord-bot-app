package me.shio3ch;

import me.shio3ch.discordbot.DiscordBotRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscordBotApplication implements ApplicationRunner {

    @Autowired
    private DiscordBotRunner discordBotRunner;

    public static void main(String[] args) {
        SpringApplication.run(DiscordBotApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        discordBotRunner.run();
    }

}
