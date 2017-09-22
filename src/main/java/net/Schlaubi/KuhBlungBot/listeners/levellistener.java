package net.Schlaubi.KuhBlungBot.listeners;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.MySQL;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.*;

public class levellistener extends ListenerAdapter {

    private ArrayList<String> cooldowned = new ArrayList<>();
    private String level;
    private String money;
    private String cookies;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String msg = event.getMessage().getContent();
        User author = event.getAuthor();
        if(!author.isBot()) {

            if (!msg.startsWith("-") && !msg.startsWith(".") && !msg.startsWith("::") && !msg.startsWith("!!") && !cooldowned.contains(author.getId())) {

                this.money = MySQL.getValue(author, "money");
                this.cookies = MySQL.getValue(author, "cookies");
                String points = MySQL.getValue(author, "points");




                    Random gen = new Random();
                    int random = gen.nextInt(50);
                    int moneyreward = Integer.parseInt(money) * random;

                    MySQL.updateValue(author, "points", String.valueOf(Integer.parseInt(points) + random));

                    cooldowned.add(author.getId());

                    this.level = MySQL.getValue(author, "level");

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            int nextlevel = Integer.parseInt(level) + 1;
                            int actualpoints = Integer.parseInt(MySQL.getValue(author, "points"));
                            Random gen = new Random();
                            int random = gen.nextInt(50);
                            int moneyreward = nextlevel * random;
                            int cookiereward = nextlevel + random;
                            PrivateChannel prich = author.openPrivateChannel().complete();


                            if(actualpoints >= nextlevel * 100 * 2){
                                MySQL.updateValue(author, "level", String.valueOf(nextlevel));


                                MySQL.updateValue(author, "money", String.valueOf(Integer.parseInt(money) + moneyreward));
                                MySQL.updateValue(author, "cookies", String.valueOf(Integer.parseInt(cookies) + cookiereward));

                                EmbedSender.sendPermanentEmbed("Congratulations you have spamed enough to get level `" + nextlevel + "`  ", prich, Color.cyan);
                                EmbedSender.sendPermanentEmbed("Rewards: \n :cookie: +" + cookiereward + " Cookies \n  :money_mouth: Money + " + moneyreward + " $", prich, Color.cyan);
                            }

                        }
                    }, 10000);

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            cooldowned.remove(author.getId());
                        }
                    }, STATIC.COOLDOWN * 1000);



            }
        }
    }
}
