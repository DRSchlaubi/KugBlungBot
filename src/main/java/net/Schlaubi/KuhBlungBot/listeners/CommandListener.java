package net.Schlaubi.KuhBlungBot.listeners;

import net.Schlaubi.KuhBlungBot.core.CommandHandler;
import net.Schlaubi.KuhBlungBot.util.STATIC;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Objects;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContent().startsWith(STATIC.PREFIX) && !Objects.equals(event.getMessage().getAuthor().getId(), event.getJDA().getSelfUser().getId())) {
            CommandHandler.handleCommand(CommandHandler.parser.parse(event.getMessage().getContent(), event));
        }

    }
}
