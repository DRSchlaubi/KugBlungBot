package net.Schlaubi.KuhBlungBot.core;


import net.Schlaubi.KuhBlungBot.commands.*;
import net.Schlaubi.KuhBlungBot.listeners.CommandListener;
import net.Schlaubi.KuhBlungBot.listeners.GuildMemberJoinListener;
import net.Schlaubi.KuhBlungBot.listeners.levellistener;
import net.Schlaubi.KuhBlungBot.util.SECRETS;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] Args){
        System.out.println("[KuhBlungBot] Starting ...");
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(SECRETS.token);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setGame(Game.of(STATIC.GAME));
        builder.addEventListener(new CommandListener());
        builder.addEventListener(new levellistener());
        builder.addEventListener(new GuildMemberJoinListener());
        CommandHandler.commands.put("game", new CommandGame());
        CommandHandler.commands.put("profile", new commandProfile());
        CommandHandler.commands.put("status", new commandTextbox());
        CommandHandler.commands.put("cookie", new commandCookie());
        CommandHandler.commands.put("shop", new commandShop());

        try {
            builder.buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
        System.out.println("[KuhBlungBot] Bot started");


    }
}
