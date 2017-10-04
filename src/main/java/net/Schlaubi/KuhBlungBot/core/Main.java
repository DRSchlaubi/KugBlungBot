package net.Schlaubi.KuhBlungBot.core;


import net.Schlaubi.KuhBlungBot.commands.*;
import net.Schlaubi.KuhBlungBot.listeners.CommandListener;
import net.Schlaubi.KuhBlungBot.listeners.GuildMemberJoinListener;
import net.Schlaubi.KuhBlungBot.listeners.levellistener;
import net.Schlaubi.KuhBlungBot.setup.Configuration;
import net.Schlaubi.KuhBlungBot.util.MySQL;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final Configuration SECRETS = new Configuration("secrets.json");

    public static void main(String[] Args){
        System.out.println("[KuhBlungBot] Starting ...");
        connectMySQL();
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken((String) SECRETS.getValue("token"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setGame(Game.of(STATIC.GAME));
        builder.addEventListener(new CommandListener());
        builder.addEventListener(new levellistener());
        builder.addEventListener(new GuildMemberJoinListener());
        CommandHandler.commands.put("game", new CommandGame());
        CommandHandler.commands.put("profile", new commandProfile());
        CommandHandler.commands.put("status", new commandStatus());
        CommandHandler.commands.put("cookie", new commandCookie());
        CommandHandler.commands.put("shop", new commandShop());
        resetCooldown();


        try {
            builder.buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
        System.out.println("[KuhBlungBot] Bot started");


    }

    private static void resetCooldown() {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    commandCookie.users.clear();
                }
            }, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

    }

    private static void connectMySQL() {
        MySQL.connect();
    }


}
