package event_processor_advanced;

import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.Windowed;
import java.time.Instant;

public class MyProcessor implements ForeachAction<Windowed<String>, Double> {

	@Override
	public void apply(Windowed<String> key, Double value) {
		String windowStart = Instant.ofEpochMilli(key.window().start()).toString();
		String windowEnd = Instant.ofEpochMilli(key.window().end()).toString();

		System.out.println("Window [" + windowStart + " - " + windowEnd + "] "
				+ "Added event to the Dashboard: Product: " + key.key()
				+ "; Average order quantity: " + value);
	}

}
