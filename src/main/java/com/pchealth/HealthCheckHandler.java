package com.pchealth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class HealthCheckHandler {

    // A static map of commands mapping from command string to the functional impl
    private static Map<String, Command> commandMap = new HashMap<>();


    // Statically populate the commandMap with the intended functionality
    // Might be better practise to do this from an instantiated objects constructor
    static {

        commandMap.put("start", (event, args) -> {
            HealthCheckRunner healthCheckRunner = HealthCheckRunner.getInstance();

            if (!healthCheckRunner.isRunning()) {
                healthCheckRunner.init(event.getChannel());
                event.getChannel().bulkDelete();
                healthCheckRunner.startAsync();
            }
        });

        commandMap.put("stop", (event, args) -> {
            HealthCheckRunner healthCheckRunner = HealthCheckRunner.getInstance();

            if (healthCheckRunner.isRunning()) {
                event.getChannel().bulkDelete();
                healthCheckRunner.stopAsync();
            }
        });

        commandMap.put("clear", (event, args) -> {
            event.getChannel().bulkDelete();
        });

        commandMap.put("status", (event, args) -> {
            HealthCheckRunner healthCheckRunner = HealthCheckRunner.getInstance();
            BotUtils.sendMessage(event.getChannel(), healthCheckRunner.isRunning() ? "Running" : "Not Running");
        });
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {

        // Given a message "/test arg1 arg2", argArray will contain ["/test", "arg1", "arg"]
        String[] argArray = event.getMessage().getContent().split(" ");

        // First ensure at least the command and prefix is present, the arg length can be handled by your command func
        if (argArray.length == 0) {
            return;
        }

        // Check if the first arg (the command) starts with the prefix defined in the utils class
        if (!argArray[0].startsWith(BotUtils.BOT_PREFIX)) {
            return;
        }

        // Extract the "command" part of the first arg out by ditching the amount of characters present in the prefix
        String commandStr = argArray[0].substring(BotUtils.BOT_PREFIX.length());

        // Load the rest of the args in the array into a List for safer access
        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0); // Remove the command

        // Instead of delegating the work to a switch, automatically do it via calling the mapping if it exists

        if (commandMap.containsKey(commandStr)) {
            commandMap.get(commandStr).runCommand(event, argsList);
        }

    }

}
