package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.MySQL;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.HashMap;

public class commandCookie implements Command{

    private static HashMap<String, Integer> users = new HashMap<>();

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

        if(!users.containsKey(author.getId())){
            users.put(author.getId(), 0);
        }


        if(!(message.getMentionedUsers().size() > 0)){
            EmbedSender.sendEmbed("Usage: `" + STATIC.PREFIX + "cookie <@Mention>`", channel, Color.red);
            return;
        }

        if(message.getMentionedUsers().get(0) == event.getAuthor()){
            EmbedSender.sendEmbed(":warning: You can't give a cookie to yourselve", channel, Color.red);
            return;
        }



        if(users.get(author.getId()) != 5) {
            int usages = users.get(author.getId());
            User user = message.getMentionedUsers().get(0);
            if(!MySQL.isUserExsists(user)){
                MySQL.createUser(user);
            }
            if(user.isBot()){
                EmbedSender.sendEmbed(":warning: You can't give a cookie to a bot SORRIIIEE", channel, Color.red);
                return;
            }


            String cookies = MySQL.getValue(user, "cookies");




            int currentCookies = Integer.parseInt(cookies);
            MySQL.updateValue(user, "cookies", String.valueOf(currentCookies + 1));
            PrivateChannel privch = user.openPrivateChannel().complete();
            EmbedSender.sendPermanentEmbed(event.getAuthor().getAsMention() + " gave you one :cookie: now you have `" + (currentCookies + 1) + "` cookies", privch, Color.cyan);
            EmbedSender.sendEmbed(event.getAuthor().getAsMention() + "You gave " + user.getAsMention() + " one cookie", channel, Color.green);
            users.replace(author.getId(), usages + 1);
        } else {

            EmbedSender.sendEmbed(":warning: You can only use this Command 5 times a day", channel, Color.red);
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
