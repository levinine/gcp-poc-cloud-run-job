package gcp.poc.cr.weatherdatapublishr.publisher;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class PubSubPublisher {

    private static final Logger logger = LoggerFactory.getLogger(PubSubPublisher.class);

    private static String projectId, topicId;

    public static void publish(String message)
            throws IOException, ExecutionException, InterruptedException {
        setSecrets();
        TopicName topicName = TopicName.of(projectId, topicId);
        Publisher publisher = null;
        logger.info(message);
        try {
            publisher = Publisher.newBuilder(topicName).build();

            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            String messageId = messageIdFuture.get();
            logger.info("Published message ID: " + messageId);
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }

    private static void setSecrets() {
        logger.info("Getting PubSub details from environment.");
        projectId = System.getenv("projectId");
        topicId = System.getenv("topicId");
    }
}
