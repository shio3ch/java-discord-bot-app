package me.shio3ch.discordbot.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * BOTユーザーの設定値を扱うクラス.
 */
@Component
public class BotUser {

    @Getter
    private final String token;

    @Getter
    private final String ownerId;

    @Autowired
    public BotUser(@Value("${app.token}") String token, @Value("${app.id}") String ownerId) {
        Assert.notNull(token, "token");
        Assert.notNull(ownerId, "ownerId");
        this.token = token;
        this.ownerId = ownerId;
    }

}
