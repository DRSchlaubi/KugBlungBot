package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.MySQL;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import java.awt.*;

public class commandShop implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        GuildController gcon = new GuildController(event.getGuild());
        channel.sendTyping().queue();
        message.delete().queue();


        String points = MySQL.getValue(author, "points");
        String cookies = MySQL.getValue(author, "cookies");
        String money = MySQL.getValue(author, "money");



            if (!(args.length > 0)) {
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("KuhBlung shop")
                        .setThumbnail(event.getGuild().getSelfMember().getUser().getAvatarUrl())
                        .setFooter("©  Schlaubi 2017 KuhBlung", event.getGuild().getSelfMember().getUser().getAvatarUrl())
                        .setColor(Color.cyan)
                        .addField(":small_blue_diamond: Points:" , "`" + points + "`", true)
                        .addField(":cookie: Cookies: ", "`" + cookies + "`", true)
                        .addField(":moneybag: Money: ", money, true)
                        .addBlankField(true)
                        .addField("**Products: **", "", false)
                        .addField(":money_with_wings: Money: ", "10 Cookies = 1 $ `" + STATIC.PREFIX + "shop exchange <amount of cookies>`", false)
                        .addField("Pink color role: ",  "Price: 10$ `" + STATIC.PREFIX + "shop buy pinkcolor`", false)
                        .addField("Stammspieler role: ",  "Price: 50$ `" + STATIC.PREFIX + "shop buy stammspieler`", false)
                        .addField("OG: ",  "Price: 100$ `" + STATIC.PREFIX + "shop buy og`", false);




                channel.sendMessage(embed.build()).queue();
                return;
            }

            switch (args[0]){
                case "exchange":
                    if(args.length > 1){
                        try{
                            int exchange = Integer.parseInt(args[1]);
                            if(exchange > Integer.parseInt(cookies)){
                                EmbedSender.sendEmbed(":warning: You don't have enough cookies", channel, Color.red);
                                return;

                            }
                            MySQL.updateValue(author, "money" , String.valueOf(Integer.parseInt(money) + (exchange / 10)));
                            MySQL.updateValue(author, "cookies", String.valueOf(Integer.parseInt(cookies) - exchange));
                            EmbedSender.sendEmbed(":white_check_mark:  You successfully bought `" + exchange /10 + "`$ Thank you!", channel, Color.green);
                        } catch (NumberFormatException e){
                            EmbedSender.sendEmbed(":warning:: Please provide a valid number", channel, Color.red);
                        }
                    } else {
                        EmbedSender.sendEmbed("Usage: `" + STATIC.PREFIX + "shop exchange <amount of cookies>`", channel, Color.red);
                    }
                    break;
                case "buy":
                    switch (args[1].toLowerCase()){
                        case "pinkcolor":
                            if(Integer.parseInt(money) >= 10){
                                Role pinkcolor = event.getGuild().getRoleById("352157314926641152");
                                if(!event.getMember().getRoles().contains(pinkcolor)) {
                                    MySQL.updateValue(author, "money", String.valueOf(Integer.parseInt(money) - 10));
                                    gcon.addRolesToMember(event.getMember(), pinkcolor).queue();
                                    EmbedSender.sendEmbed(":white_check_mark: You successfully bought `1x pink color (10$)`! Thank you!", channel, Color.green);
                                } else {
                                    EmbedSender.sendEmbed("You have already bought that product", channel, Color.red);
                                }
                            } else {
                                EmbedSender.sendEmbed(":warning: You don't have enough money to buy this", channel, Color.red);
                            }
                            break;
                        case "stammspieler":
                            if(Integer.parseInt(money) >= 50){
                                Role stammspieler = event.getGuild().getRoleById("352830292777762816");
                                if(!event.getMember().getRoles().contains(stammspieler)) {
                                    MySQL.updateValue(author, "money", String.valueOf(Integer.parseInt(money) - 50));
                                    gcon.addRolesToMember(event.getMember(), stammspieler).queue();
                                    EmbedSender.sendEmbed(":white_check_mark: You successfully bought `1x stammspieler (50$)`! Thank you!", channel, Color.green);
                                } else {
                                    EmbedSender.sendEmbed("You have already bought that product", channel, Color.red);
                                }
                            } else {
                                EmbedSender.sendEmbed(":warning: You don't have enough money to buy this", channel, Color.red);
                            }
                            break;
                        case "og":
                            if(Integer.parseInt(money) >= 1000){
                                Role og = event.getGuild().getRoleById("356117876761034752");
                                if(!event.getMember().getRoles().contains(og)) {
                                    MySQL.updateValue(author, "money", String.valueOf(Integer.parseInt(money) - 1000));
                                    gcon.addRolesToMember(event.getMember(), og).queue();
                                    EmbedSender.sendEmbed(":white_check_mark: You successfully bought `1x OG role (1000$)`! Thank you!", channel, Color.green);
                                } else {
                                    EmbedSender.sendEmbed("You have already bought that product", channel, Color.red);
                                }
                            } else {
                                EmbedSender.sendEmbed(":warning: You don't have enough money to buy this", channel, Color.red);
                            }
                            break;
                        default:
                            EmbedSender.sendEmbed(":warning: That product don't exsists", channel, Color.red);
                            break;
                    }
                    break;
                default:
                    EmbedSender.sendEmbed(":warning: That command don't exsists", channel, Color.red);
                    break;
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
