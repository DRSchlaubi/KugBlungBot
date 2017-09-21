package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.MySQL;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
public class commandStatus implements Command{

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

        if(!MySQL.isUserExsists(author)){
            MySQL.createUser(author);
        }

        if(!(args.length > 0)){
            EmbedSender.sendEmbed("Usage: `" + STATIC.PREFIX + "status <text>`", channel, Color.red);
            return;
        }

        for (String arg : args) {
            input += arg + " ";
        }

        MySQL.updateValue(author, "status", input.replaceFirst("null", ""));

        EmbedSender.sendEmbed(":white_check_mark: Succesfully set your textbox to `" + input.replaceFirst("null", "") + "` !", channel, Color.green);


        }


    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
