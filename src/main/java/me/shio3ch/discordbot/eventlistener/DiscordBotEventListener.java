package me.shio3ch.discordbot.eventlistener;

import net.dv8tion.jda.api.hooks.EventListener;

/**
 * イベントリスナとしてDIするためのインタフェース.
 *
 * <p>
 * EventListener としてDIすると、 ListenerAdapter も対象になってしまうため、
 * 独自にインタフェースを用意している。
 * </p>
 */
public interface DiscordBotEventListener extends EventListener {
}
