package com.pchealth;

import com.google.common.util.concurrent.AbstractScheduledService;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import sx.blah.discord.handle.obj.IChannel;

public class HealthCheckRunner extends AbstractScheduledService {

    private static HealthCheckRunner instance;
    private IChannel discordChannel;

    private HealthCheckRunner() {
    }

    public void init(IChannel discordChannel) {
        this.discordChannel = discordChannel;
    }

    @Override
    protected void runOneIteration() throws Exception {
        BotUtils.sendMessage(this.discordChannel , "PC OK ! [" + LocalDateTime.now() + "]");
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 3, TimeUnit.MINUTES);
    }

    public static synchronized HealthCheckRunner getInstance() {
        if (instance == null) {
            instance = new HealthCheckRunner();
        }
        return instance;
    }
}
