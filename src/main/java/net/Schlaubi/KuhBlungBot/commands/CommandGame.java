package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import java.awt.*;

public class CommandGame implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        GuildController gcon = new GuildController(guild);
        Member member = event.getMember();
        channel.sendTyping().queue();
        message.delete().queue();

        if(args.length > 0){
            Role minecraft = guild.getRoleById("318048085244641281");
            Role steam = guild.getRoleById("318762244009623552");
            Role overwatch = guild.getRoleById("318048014109245440");
            if(args[0].equalsIgnoreCase("minecraft")){
                if(!member.getRoles().contains(minecraft)){
                    gcon.addRolesToMember(member, minecraft).queue();
                    EmbedSender.sendEmbed(":white_check_mark: Succesfully assigned role `Meinkraft`", channel, Color.green);
                } else {
                    gcon.removeRolesFromMember(member, minecraft).queue();
                    EmbedSender.sendEmbed(":white_check_mark: Succesfully removed role `Meinkraft`", channel, Color.green);


                }
            }
            if(args[0].equalsIgnoreCase("steam")){
                if(!member.getRoles().contains(minecraft)){
                    gcon.addRolesToMember(member, steam).queue();
                    EmbedSender.sendEmbed(":white_check_mark: Succesfully assigned role `Steam Player`", channel, Color.green);
                } else {
                    gcon.removeRolesFromMember(member, steam).queue();
                    EmbedSender.sendEmbed(":white_check_mark: Succesfully removed role `Steam Player`", channel, Color.green);


                }
            }
            if(args[0].equalsIgnoreCase("overwatch")){
                if(!member.getRoles().contains(minecraft)){
                    gcon.addRolesToMember(member, overwatch).queue();
                    EmbedSender.sendEmbed(":white_check_mark: Succesfully assigned role `Overwatch`", channel, Color.green);
                } else {
                    gcon.removeRolesFromMember(member, overwatch).queue();
                    EmbedSender.sendEmbed(":white_check_mark: Succesfully removed role `Overwatch`", channel, Color.green);


                }
            }

        } else {
            EmbedSender.sendEmbed("Usage: `" + STATIC.PREFIX + "game <game>", channel, Color.red);
        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

        System.out.println("[INFO] Command '" + STATIC.PREFIX + "game' was executed by " + event.getAuthor().getName());

    }

    @Override
    public String help() {
        return null;
    }
}
