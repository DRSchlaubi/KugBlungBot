package net.Schlaubi.KuhBlungBot.listeners;

import net.Schlaubi.KuhBlungBot.util.EmbedSender;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.io.*;
import java.util.*;

public class levellistener extends ListenerAdapter {

    private String points;
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


                File profile = new File("PROFILES/" + author.getId() + "/profile.properties");
                File path = new File("PROFILES/" + author.getId() + "/");

                if (!path.exists()) {
                    path.mkdirs();
                }

                if (!profile.exists()) {
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

                    points = properties.getProperty("points");
                    money = properties.getProperty("money");
                    cookies = properties.getProperty("cookies");

                    if (points == null) {
                        this.points = "0";
                        properties.setProperty("points", "0");
                        properties.store(new FileOutputStream(profile), null);
                    }

                    if (level == null) {
                        this.level = "1";
                        properties.setProperty("level", "1");
                        properties.store(new FileOutputStream(profile), null);
                    }
                    if (money == null) {
                        this.money = "0";
                        properties.setProperty("money", "0");
                        properties.store(new FileOutputStream(profile), null);
                    }
                    if (cookies == null) {
                        this.cookies = "0";
                        properties.setProperty("cookies", "0");
                        properties.store(new FileOutputStream(profile), null);
                    }


                    Random gen = new Random();
                    int random = gen.nextInt(50);
                    int moneyreward = Integer.parseInt(money) * random;

                    properties.setProperty("points", String.valueOf(moneyreward));


                    properties.store(new FileOutputStream(profile), null);
                    cooldowned.add(author.getId());

                    this.level = properties.getProperty("level");

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            int nextlevel = Integer.parseInt(level) + 1;
                            int actualpoints = Integer.parseInt(properties.getProperty("points"));
                            Random gen = new Random();
                            int random = gen.nextInt(50);
                            int moneyreward = nextlevel * random;
                            int cookiereward = nextlevel + random;
                            properties.setProperty("money", String.valueOf(Integer.parseInt(money) + moneyreward));
                            properties.setProperty("cookies", String.valueOf(Integer.parseInt(cookies) + cookiereward));
                            try {
                                properties.store(new FileOutputStream(profile), null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            PrivateChannel prich = author.openPrivateChannel().complete();


                            if(actualpoints >= nextlevel * 100 * 2){
                                properties.setProperty("level", String.valueOf(nextlevel));

                                try {
                                    properties.store(new FileOutputStream(profile), null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

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


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
