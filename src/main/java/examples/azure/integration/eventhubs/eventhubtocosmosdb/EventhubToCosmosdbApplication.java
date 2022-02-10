package examples.azure.integration.eventhubs.eventhubtocosmosdb;

import com.azure.spring.integration.core.EventHubHeaders;
import com.azure.spring.integration.core.api.reactor.Checkpointer;
import examples.azure.integration.eventhubs.eventhubtocosmosdb.CosmosDBReciever;
import examples.azure.integration.eventhubs.eventhubtocosmosdb.EventReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import static com.azure.spring.integration.core.AzureHeaders.CHECKPOINTER;

@SpringBootApplication
public class EventhubToCosmosdbApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(EventhubToCosmosdbApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EventhubToCosmosdbApplication.class, args);
    }
}