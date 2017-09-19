package net.Schlaubi.KuhBlungBot.commands;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class commandShop implements Command {
    private String points;
    private String cookies;
    private String money;
    private int exchange;

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

        Properties properties = new Properties();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(profile));
            properties.load(bis);
            this.points = properties.getProperty("points");
            this.cookies = properties.getProperty("cookies");
            this.money = properties.getProperty("money");

            if (points == null) {
                this.points = "0";
                properties.setProperty("points", "0");
                properties.store(new FileOutputStream(profile), null);
            }


            if (cookies == null) {
                this.cookies = "0";
                properties.setProperty("cookies", "0");
                properties.store(new FileOutputStream(profile), null);
            }

            if(money == null){
                this.money = "0";
                properties.setProperty("money", "0");
                properties.store(new FileOutputStream(profile), null);
            }

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
                        .addField(":money_with_wings: Money: ", "10 Cookies = 1 $ `" + STATIC.PREFIX + "shop exchange <amount of cookies>`", false);
                channel.sendMessage(embed.build()).queue();
                return;
            }

            switch (args[0]){
                case "exchange":
                    if(args.length > 1){
                        try{
                            exchange = Integer.parseInt(args[1]);
                            if(exchange > Integer.parseInt(cookies)){
                                EmbedSender.sendEmbed(":warning: You don't have enough cookies", channel, Color.red);
                                return;

                            }
                            properties.setProperty("money", String.valueOf(Integer.parseInt(money) + (exchange / 10)));
                            properties.setProperty("cookies", String.valueOf(Integer.parseInt(cookies) - exchange));
                            properties.store(new FileOutputStream(profile), null);
                            EmbedSender.sendEmbed(":white_check_mark:  You successfully bought `" + exchange /10 + "`$ Thank you!", channel, Color.green);
                        } catch (NumberFormatException e){
                            EmbedSender.sendEmbed(":warning:: Please provide a valid number", channel, Color.red);
                        }
                    } else {
                        EmbedSender.sendEmbed("Usage: `" + STATIC.PREFIX + "shop exchange <amount of cookies>`", channel, Color.red);
                    }

                    break;
            }
        } catch (IOException e){
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
