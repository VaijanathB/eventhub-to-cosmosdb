package examples.azure.integration.eventhubs.eventhubtocosmosdb;


public class Event {

    /**
     * Stores the hello.
     */
    private String message;

    /**
     * Stores the id.
     */
    private String id;


    /**
     * Constructor.
     */
    public Event() {
        id = "" + System.nanoTime();
        message = "Hello World!";
    }

    /**
     * Get the hello.
     *
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the message.
     *
     * @param message the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(String id) {
        this.id = id;
    }
}
