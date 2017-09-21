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
                     int nextLevel = Integer.parseInt(level) +1;
                    EmbedBuilder embed = new EmbedBuilder()
                            .setColor(Color.cyan)
                            .setThumbnail(user.getAvatarUrl())
                            .setTitle("__**" + user.getName() + "'s**__ profile")
                            .addField(":moneybag: Money: ", money, true)
                            .addField(":cookie: Cookies:", "`" + cookies + "`", true)
                            .addField(":small_blue_diamond: Points:" , "`" + points + "`", true)
                            .addField(":large_blue_diamond: Level", "`" + level + "`", true)
                            .addField(":large_blue_diamond: Next level", "`" + points + "/" + nextLevel * 100 * 2 +"`", true)
                            .addField(":pager: Status", status, false)
                            .addField("Social Media: ", "SOON", false)
                            .addField(":computer:  Profile url:", "http://kuhblung.schlb.pw/" + author.getId() + " (BETA)", false);
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
