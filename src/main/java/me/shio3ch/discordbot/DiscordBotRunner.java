package me.shio3ch.discordbot;

import me.shio3ch.discordbot.domain.BotUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * BOTを稼働させるためのクラス.
 */
@Component
public class DiscordBotRunner {

    private static final Logger logger = LoggerFactory.getLogger(DiscordBotRunner.class.getName());

    @Autowired
    private BotUser botUser;

    @Autowired
    private List<EventListener> eventListeners;

    public void run() {
        logger.info("Run Discord Bot application!");
        try {
            JDA jda = buildJDA();
            jda.awaitReady();

        } catch (LoginException e) {
            logger.error("Botのログインに失敗したため、終了します。", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("何かしらのエラーが発生したので終了します。", e);
            throw new RuntimeException(e);
        }
    }

    private JDA buildJDA() throws LoginException {
        logger.trace("START configuration.");

        JDABuilder builder = JDABuilder.createDefault(botUser.getToken());
        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));
        // Disable cache for member activities (streaming/games/spotify)
        builder.disableCache(CacheFlag.ACTIVITY);
        // Only cache members who are either in a voice channel or owner of the guild
        builder.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER));
        // Disable member chunking on startup
        builder.setChunkingFilter(ChunkingFilter.NONE);
        // Disable presence updates and typing events
        // builder.disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING);
        // Consider guilds with more than 50 members as "large".
        // Large guilds will only provide online members in their setup and thus reduce bandwidth if chunking is disabled.
        builder.setLargeThreshold(50);

        builder.addEventListeners(eventListeners.toArray());

        return builder.build();
    }

}
