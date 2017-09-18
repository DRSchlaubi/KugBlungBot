package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class commandProfile implements Command {

    private String points;
    private String textbox;
    private String cookies;
    String level;
    private User user;

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


        if(!(message.getMentionedUsers().size() > 0)){
            user = event.getAuthor();
            return;
        } else {
            user = message.getMentionedUsers().get(0);
        }
        File profile = new File("PROFILES/" + user.getId() + "/profile.properties");
        File path = new File("PROFILES/" + user.getId() + "/");

        if(!path.exists()){
            path.mkdirs();
        }

        if(!profile.exists()) {
            try {
                profile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Properties properties = new Properties();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream( profile));
            properties.load(bis);
            this.points = properties.getProperty("points");
            this.textbox = properties.getProperty("textbox");
            this.cookies = properties.getProperty("cookies");
            this.level = properties.getProperty("level");

            if(points == null){
                this.points = "0";
                properties.setProperty("points", "0");
                properties.store(new FileOutputStream(profile), null);
            }


            if(textbox == null){
                this.textbox = STATIC.INFO;
                properties.setProperty("textbox", STATIC.INFO);
                properties.store(new FileOutputStream(profile), null);
            }

            if(cookies == null){
                this.cookies = "0";
                properties.setProperty("cookies", "0");
                properties.store(new FileOutputStream(profile), null);
            }

            if(level == null){
                this.level = "1";
                properties.setProperty("level", "1");
                properties.store(new FileOutputStream(profile), null);
            }

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    int nextLevel = Integer.parseInt(level) +1;
                    EmbedBuilder embed = new EmbedBuilder()
                            .setColor(Color.cyan)
                            .setThumbnail(user.getAvatarUrl())
                            .setTitle("__**" + user.getName() + "'s**__ profile")
                            .addField(":small_blue_diamond: Points:" , "`" + points + "`", true)
                            .addField(":cookie: Cookies:", "`" + cookies + "`", true)
                            .addField(":large_blue_diamond: Level", "`" + level + "`", true)
                            .addField(":large_blue_diamond: Next level", "`" + points + "/" + nextLevel * 100 * 2 +"`", true)
                            .addField(":pager: Textbox", textbox, false)
                            .addField(":computer:  Profile url:", "http://kuhblung.schlb.pw/" + author.getId() + " (soon)", false);
                    channel.sendMessage(embed.build()).queue();
                }
            }, 1000);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
