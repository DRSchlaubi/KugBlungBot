package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class commandTextbox implements Command{

    private String input;

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

        Properties properties = new Properties();
        File profile = new File("PROFILES/" + author.getId() + "/profile.properties");
        File path = new File("PROFILES/" + author.getId() + "/");

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

        if(!(args.length > 0)){
            EmbedSender.sendEmbed("Usage: `" + STATIC.PREFIX + "status <text>`", channel, Color.red);
            return;
        }


        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream( profile));
            properties.load(bis);
            String textbox = properties.getProperty("textbox");
            if(textbox == null){
                properties.setProperty("textbox", STATIC.INFO);
                properties.store(new FileOutputStream(profile), null);
            }

            for (int i = 0; i < args.length; i++){
                input += args[i] + " ";
            }

            properties.setProperty("textbox", input.replaceFirst("null", ""));
            properties.store(new FileOutputStream(profile), null);

            EmbedSender.sendEmbed(":white_check_mark: Succesfully set your textbox to `" + input.replaceFirst("null", "") + "` !", channel, Color.green);

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
