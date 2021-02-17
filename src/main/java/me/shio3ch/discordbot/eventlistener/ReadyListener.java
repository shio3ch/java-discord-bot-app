package me.shio3ch.discordbot.eventlistener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class ReadyListener implements DiscordBotEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ReadyListener.class.getName());

    @Override
    public void onEvent(@Nonnull GenericEvent event) {
        if (event instanceof ReadyEvent)
            logger.info("API is ready!");
    }

}
