package event_processor_advanced;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class CountAndSum implements Serializable {
	private static final long serialVersionUID = 1L;
	public int count;
	public double sum;

	public CountAndSum() {
	}

	public CountAndSum(double sum, int count) {
		this.count = count;
		this.sum = sum;
	}

	public double getAverage() {
		return sum / count;
	}

	static class CountAndSumSerializer implements Serializer<CountAndSum> {

		@Override
		public byte[] serialize(String topic, CountAndSum data) {
			try {
				ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
				objectOut.writeObject(data);
				objectOut.flush();
				byte[] byteArray = byteOut.toByteArray();
				objectOut.close();
				byteOut.close();
				return byteArray;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	static class CountAndSumDeserializer implements Deserializer<CountAndSum> {

		@Override
		public CountAndSum deserialize(String topic, byte[] data) {
			try {
				ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
				ObjectInputStream objectIn = new ObjectInputStream(byteIn);
				CountAndSum myObject = (CountAndSum) objectIn.readObject();
				objectIn.close();
				byteIn.close();
				return myObject;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

	}
}
