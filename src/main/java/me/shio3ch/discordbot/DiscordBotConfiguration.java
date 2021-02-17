package me.shio3ch.discordbot;

import me.shio3ch.discordbot.eventlistener.DiscordBotEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.List;

@Component
public class DiscordBotConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DiscordBotConfiguration.class.getName());

    @Value("${app.token}")
    private String token;

    @Autowired
    List<DiscordBotEventListener> eventListeners;

    @Autowired
    List<ListenerAdapter> listenerAdapters;

    public void run() {
        logger.info("Run Discord Bot application!");
        try {
            JDA jda = build();
            jda.addEventListener(listenerAdapters.toArray());
            jda.awaitReady();

        } catch (Exception e) {
            logger.warn("エラったので終了します");
        }
    }

    public JDA build() {
        logger.info("START configuration.");

        JDABuilder builder = JDABuilder.createDefault(token);
        setupConfigure(builder);
        setupConfigureMemoryUsage(builder);
        setupEventListener(builder);

        try {
            return builder.build();
        } catch (LoginException lEx) {
            logger.error("ログインが出来ないんですわ");
            throw new RuntimeException(lEx);
        } catch (Exception ex) {
            logger.error("エラーになっちゃった！");
            throw new RuntimeException(ex);
        }
    }

    private void setupConfigure(JDABuilder builder) {
        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));
    }

    public void setupConfigureMemoryUsage(JDABuilder builder) {
        // Disable cache for member activities (streaming/games/spotify)
        builder.disableCache(CacheFlag.ACTIVITY);

        // Only cache members who are either in a voice channel or owner of the guild
        builder.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER));

        // Disable member chunking on startup
        builder.setChunkingFilter(ChunkingFilter.NONE);

        // Disable presence updates and typing events
        //builder.disableIntents(GatewayIntent.GUILD_PRESENCE, GatewayIntent.GUILD_MESSAGE_TYPING);

        // Consider guilds with more than 50 members as "large".
        // Large guilds will only provide online members in their setup and thus reduce bandwidth if chunking is disabled.
        builder.setLargeThreshold(50);
    }

    private void setupEventListener(JDABuilder builder) {
        builder.addEventListeners(eventListeners.toArray());
    }

}
