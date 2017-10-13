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
import java.util.concurrent.ThreadLocalRandom;

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
            if(!MySQL.isUserExsists(author))
                MySQL.createUser(author);
            if (!msg.startsWith("-") && !msg.startsWith(".") && !msg.startsWith("::") && !msg.startsWith("!!") && !cooldowned.contains(author.getId())) {

                this.money = MySQL.getValue(author, "money");
                this.cookies = MySQL.getValue(author, "cookies");
                this.level = MySQL.getValue(author, "level");
                String points = MySQL.getValue(author, "points");




                    Random r = new Random();
                    int Low = 10;
                    int High = 100;
                    int random = ThreadLocalRandom.current().nextInt(15, 25 +1);
                    int moneyreward = Integer.parseInt(money) * random;

                    MySQL.updateValue(author, "points", String.valueOf(Integer.parseInt(points) + random));

                    cooldowned.add(author.getId());



                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            int nextLevel = Integer.parseInt(level) + 1;
                            int actualpoints = Integer.parseInt(MySQL.getValue(author, "points"));
                            int random = ThreadLocalRandom.current().nextInt(15,25 + 1);
                            int moneyreward = nextLevel * random;
                            int cookiereward = nextLevel + random;
                            int levelint = Integer.parseInt(level);
                            int nextLevelPoints = 2000000;
                            PrivateChannel prich = author.openPrivateChannel().complete();


                            if(actualpoints >= nextLevelPoints){
                                MySQL.updateValue(author, "level", String.valueOf(nextLevel));


                                MySQL.updateValue(author, "money", String.valueOf(Integer.parseInt(money) + moneyreward));
                                MySQL.updateValue(author, "cookies", String.valueOf(Integer.parseInt(cookies) + cookiereward));

                                EmbedSender.sendPermanentEmbed("Congratulations you have spamed enough to get level `" + nextLevel + "`  ", prich, Color.cyan);
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
