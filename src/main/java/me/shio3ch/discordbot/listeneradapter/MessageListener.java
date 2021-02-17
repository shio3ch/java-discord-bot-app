package me.shio3ch.discordbot.listeneradapter;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class.getName());

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        Message message = event.getMessage();
        logger.trace("Message received. (MessageId: " + event.getMessageId() + ")");
        logger.trace("Content : " + message.getContentDisplay());
        logger.trace("Raw     : " + message.getContentRaw());
        logger.trace("Stripped: " + message.getContentStripped());

        MessageChannel channel = event.getChannel();
        String responseMessage = message.getContentDisplay() + "って何だよ";

        MessageAction messageAction = channel.sendMessage(responseMessage);
        messageAction.queue();
    }

}
