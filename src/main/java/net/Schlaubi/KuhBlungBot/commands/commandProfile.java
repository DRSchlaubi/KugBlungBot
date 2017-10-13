package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.MySQL;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class commandProfile implements Command {

    private String points;
    private String status;
    private String cookies;
    private String level;
    private User user;
    private String money;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        channel.sendTyping().queue();
        message.delete().queue();


        if(message.getMentionedUsers().size() > 0){
            user = message.getMentionedUsers().get(0);
        } else {
            user = event.getAuthor();
        }


        if(!MySQL.isUserExsists(user)){
            MySQL.createUser(user);
        }

        this.level = MySQL.getValue(user, "level");
        this.points = MySQL.getValue(user, "points");
        this.money = MySQL.getValue(user, "money");
        this.cookies = MySQL.getValue(user, "cookies");
        this.status = MySQL.getValue(user, "status");

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    int levelint = Integer.parseInt(level);
                    int nextLevel = Integer.parseInt(level) +1;
                    int nextLevelPoints = 5*(levelint ^2)+50*levelint+100;
                    EmbedBuilder embed = new EmbedBuilder()
                            .setColor(Color.cyan)
                            .setThumbnail(user.getAvatarUrl())
                            .setTitle("__**" + user.getName() + "'s**__ profile")
                            .addField(":moneybag: Money: ", money, true)
                            .addField(":cookie: Cookies:", "`" + cookies + "`", true)
                            .addField(":small_blue_diamond: Points:" , "`" + points + "`", true)
                            .addField(":large_blue_diamond: Level", "`" + level + "`", true)
                            .addField(":large_blue_diamond: Next level", "`" + points + "/" + nextLevelPoints +"`", true)
                            .addField(":pager: Status", status, false)
                            .addField("Social Media: ", "", false);
                    if(!MySQL.getValue(user, "netdex").equals("0")){
                        embed.addField("<:netdex:360033528680808450> Netdex:", "http://netdex.co/" + MySQL.getValue(user, "netdex"), false);
                    }
                    if(!MySQL.getValue(user, "twitter").equals("0")){
                        embed.addField("<:twitter:360034158984298496> Twitter:", "http://twitter.com/" + MySQL.getValue(user, "twitter"), false);
                    }
                    if(!MySQL.getValue(user, "reddit").equals("0")){
                        embed.addField("<:reddit:361125527844814858> Reddit:", "http://reddit.com/u/" + MySQL.getValue(user, "reddit"), false);
                    }
                    if(!MySQL.getValue(user, "steam").equals("0")){
                        embed.addField("<:steam:361125793948237825> Steam:", MySQL.getValue(user, "steam"), false);
                    }
                    if(!MySQL.getValue(user, "twitch").equals("0")){
                        embed.addField("<:twitch:361125953184989185> Twitch:", "http://twitch.tv/" + MySQL.getValue(user, "twitch"), false);
                    }
                    embed.addField(":computer:  Profile url:", "http://kuhblung.schlb.pw/" + author.getId() + " (BETA)", false);
                    channel.sendMessage(embed.build()).queue();
                }
            }, 1000);




    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
