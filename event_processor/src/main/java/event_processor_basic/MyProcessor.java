package event_processor_basic;

import org.apache.kafka.streams.kstream.ForeachAction;


/* Just a silly example which prints every event to the console */
public class MyProcessor implements ForeachAction<String, String>{
	@Override
	public void apply(String key, String value) {
		System.out.println("MyProcessor::got message  K:"+key+"   V:"+value);
	}

}