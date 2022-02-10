package examples.azure.integration.eventhubs.eventhubtocosmosdb;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.spring.integration.core.EventHubHeaders;
import com.azure.spring.integration.core.api.reactor.Checkpointer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static com.azure.spring.integration.core.AzureHeaders.CHECKPOINTER;

@Component
public class CosmosDBReciever implements EventReceiver {

    public static final Logger LOGGER = LoggerFactory.getLogger(examples.azure.integration.eventhubs.eventhubtocosmosdb.CosmosDBReciever.class);

    /**
     * Stores the Cosmos DB URI.
     */
    @Value("${azure.cosmos.uri}")
    private String uri;

    /**
     * Stores the Cosmos DB key.
     */
    @Value("${azure.cosmos.key}")
    private String key;

    /**
     * Stores the Cosmos DB database.
     */
    @Value("${azure.cosmos.database}")
    private String databaseName;

    /**
     * Stores the Cosmos DB container.
     */
    @Value("${azure.cosmos.container}")
    private String containerName;

    /**
     * Stores the Cosmos DB container.
     */
    private CosmosContainer container;

    /**
     * Initialize the component.
     */
    @PostConstruct
    public void initialize() {
        CosmosClient client = new CosmosClientBuilder()
                .endpoint(uri)
                .key(key)
                .buildClient();
        CosmosDatabase database = client.getDatabase(databaseName);
        container = database.getContainer(containerName);
    }

    @Bean
    @Override
    public Consumer<Message<Event>> consume() {
        return message -> {
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            executorService.submit(() -> {
                CosmosItemRequestOptions options = new CosmosItemRequestOptions();
                var response= container.upsertItem(message.getPayload(), options);
                // User needs to handle the case when the Upsert fails
            });

            Checkpointer checkpointer = (Checkpointer) message.getHeaders().get(CHECKPOINTER);

            checkpointer.success()
                    .doOnSuccess(success -> LOGGER.info("Message '{}' successfully checkpointed", message.getPayload().getMessage()))
                    .doOnError(error -> LOGGER.error("Exception found", error))
                    .subscribe();

        };

    }
}
