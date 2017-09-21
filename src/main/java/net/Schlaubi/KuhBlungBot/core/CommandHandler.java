package net.Schlaubi.KuhBlungBot.core;

import net.Schlaubi.KuhBlungBot.commands.Command;

import java.util.HashMap;

public class CommandHandler {

    public static final CommandParser parser = new CommandParser();
    static HashMap<String, Command> commands = new HashMap<>();

    public static void handleCommand(CommandParser.commandContainer cmd) {

        if (commands.containsKey(cmd.invoke.toLowerCase())) {

            boolean safe = commands.get(cmd.invoke.toLowerCase()).called(cmd.args, cmd.event);

            if (!safe) {
                commands.get(cmd.invoke.toLowerCase()).action(cmd.args, cmd.event);
                commands.get(cmd.invoke.toLowerCase()).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke.toLowerCase()).executed(safe, cmd.event);
            }

        }

    }
}
