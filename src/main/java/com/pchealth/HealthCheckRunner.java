package com.pchealth;

import com.google.common.util.concurrent.AbstractScheduledService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.Data;
import org.apache.http.HttpStatus;
import sx.blah.discord.handle.obj.IChannel;

public class HealthCheckRunner extends AbstractScheduledService {

    private static HealthCheckRunner instance;
    private IChannel discordChannel;

    private HealthCheckRunner() {
    }

    public void init(IChannel discordChannel) {
        this.discordChannel = discordChannel;
    }

    public static synchronized HealthCheckRunner getInstance() {
        if (instance == null) {
            instance = new HealthCheckRunner();
        }
        return instance;
    }

    @Override
    protected void runOneIteration() throws Exception {
        if (canDoOperation()) {
            BotUtils.sendMessage(this.discordChannel,
                    "PC OK ! [" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "]");
        } else {
            System.out.println("FATAL - PC can not do simple operation !!!");
        }
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 3, TimeUnit.MINUTES);
    }

    private boolean canDoOperation() {
        // simple health check - ping some public www rest api - https://rest.ensembl.org/documentation/info/ping
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("https://rest.ensembl.org/");
        WebTarget pingWebTarget = webTarget.path("info/ping");

        Response response = pingWebTarget.request(MediaType.APPLICATION_JSON).get();
        Ping ping = response.readEntity(Ping.class);

        return HttpStatus.SC_OK == response.getStatus() && ping.isOK();
    }

}

@Data
class Ping {

    int ping;

    public boolean isOK() {
        return ping == 1;
    }
}
