package event_processor_advanced;

import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.Windowed;


public class MyProcessor implements ForeachAction<Windowed<String>, Double>{

	@Override
	public void apply(Windowed<String> key, Double value) {
		System.out.println("Added event to the Dashboard: Products:"+key+"; Average order quantity:"+value);
	}

}
