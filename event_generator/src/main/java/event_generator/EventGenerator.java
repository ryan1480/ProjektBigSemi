package event_generator;

import java.time.Duration;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class EventGenerator {

	public static void main(String[] args) {
		String group = "group3";
		String topic = group + "__orders";

		// connect to Kafka and create a producer that lets us send events
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.111.10:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		@SuppressWarnings("resource")
		Producer<String, String> producer = new KafkaProducer<>(props);

		// Events are created when an Eco Tee or a pack of Eco Socks are sold
		// event is structured as follows:

		for (;;) { // endless loop

			Random random = new Random();
			boolean randomizer = random.nextBoolean();
			int randMili = (int) Math.floor(Math.random() * 2000); // random miliseconds: 0 <= randMili < 2000
			
			if (randomizer == true) 
			{
				String key = "Eco-Socks (Pack of 3)";
				String value = "" + Math.floor(Math.random() * 10 + 1); // a random quantity

				// Create event and send to kafka
				ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
				producer.send(record, (RecordMetadata metadata, Exception exception) -> {
					if (exception != null) {
						exception.printStackTrace();
					} else {
						System.out.printf("Generated Event:(Product: \"%s\"; Quantity: %s)%n", key, value);
					}
				});

				// wait a little
				try {
					Thread.sleep(randMili);
				} catch (InterruptedException e) 
				{
				}			
			}
			else 
			{
				String key = "The Eco-Tee";
				String quantity = "" + Math.floor(Math.random() * 10 + 1); // a random quantity

				// Create event and sent to kafka
				ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, quantity);
				producer.send(record, (RecordMetadata metadata, Exception exception) -> {
					if (exception != null) {
						exception.printStackTrace();
					} else {
						System.out.printf("Generated Event:(Product: \"%s\"; Quantity: %s)%n", key, quantity);
					}
				});

				// wait a little
				try {
					Thread.sleep(randMili);
				} catch (InterruptedException e) 
				{
				}
			}
			
			
		}
	}

}
