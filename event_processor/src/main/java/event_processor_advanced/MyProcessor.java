package event_processor_advanced;

import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.Windowed;


/* Just a silly example */
public class MyProcessor implements ForeachAction<Windowed<String>, Double>{

	@Override
	public void apply(Windowed<String> key, Double value) {
		System.out.println("MyProcessor::got message  K:"+key+"   V:"+value);
	}

}