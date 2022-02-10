package examples.azure.integration.eventhubs.eventhubtocosmosdb;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

public interface EventReceiver {

    @Bean
    public Consumer<Message<Event>> consume();

}
