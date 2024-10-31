package event_processor_basic;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

public class EventProcessorBasic {

	public static void main(String[] args) {
		String group = "group3";

		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream_processor-" + group);
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.10:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		System.out.println("*** NOTE: it may take a while until the first events arive");
		
		final StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> source = builder.stream(group + "__orders");
		

		// lets assume that a order quantity > 3 is already noteworthy
		source.filter( (key,value) -> Double.parseDouble(value) > 3 ).foreach(new MyProcessor());;
		
		
		
		final Topology topology = builder.build();
		final KafkaStreams streams = new KafkaStreams(topology, props);
		final CountDownLatch latch = new CountDownLatch(1);

		// attach shutdown handler to catch control-c
		Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
			@Override
			public void run() {
				streams.close();
				latch.countDown();
			}
		});

		try {
			streams.start();
			latch.await();
		} catch (Throwable e) {
			System.exit(1);
		}
		System.exit(0);
	}
	

}
