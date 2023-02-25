package me.shio3ch.discordbot.eventlistener;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * ReadyEvent を検知してAPIの起動開始を通知するイベントリスナ.
 */
@Component
public class ReadyListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ReadyListener.class.getName());

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        logger.info("API is ready!");
    }

}
