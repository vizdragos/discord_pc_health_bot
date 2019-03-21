package com.pchealth;

import sx.blah.discord.api.IDiscordClient;

public class PCHealthBot {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            System.exit(1);
        }

        IDiscordClient cli = BotUtils.getBuiltDiscordClient(args[0]);

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(new HealthCheckHandler());

        // Only login after all events are registered otherwise some may be missed.
        cli.login();
    }

}
