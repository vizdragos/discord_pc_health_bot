# Discord PC Health Bot
A basic click-to-run bot using Discord4J v2.

When activated, it send every X minutes a message to the discord channel to inform PC is still running (not freezed).

## Commands

`/start` - start sending messages

`/stop` - stop sending messages


## How to run

Import the project as a maven project into your IDE of choice.

Once imported, either build it as a jar or run it from your IDE. When
unning the bot, the first argument of the exec must be your token.

When running as a jar: `java -jar builtjar.jar TOKENHERE`

When running from an IDE specify token as program argument.


## How to build

A fat jar can be built through maven simply by running the `mvn package`
goal in whatever manner your IDE requires.



### Util links:
https://www.quora.com/How-would-I-go-about-making-a-discord-bot-using-Java

https://www.digitaltrends.com/gaming/how-to-make-a-discord-bot/

https://github.com/Discord4J/Discord4J

https://github.com/decyg/d4jexamplebot/

https://github.com/SinisterRectus/Discordia/wiki/Setting-up-a-Discord-application

https://github.com/Chikachi/DiscordIntegration/wiki/How-to-get-a-token-and-channel-ID-for-Discord

