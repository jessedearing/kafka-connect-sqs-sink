package io.jessed.sqs.kafka.connect.test;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.sink.SinkRecord;

import org.easymock.EasyMock;

import org.junit.Before;
import org.junit.Test;

import org.powermock.api.easymock.PowerMock;

import io.jessed.sqs.kafka.connect.KafkaSQSSink;

public class KafkaSQSSinkTest {
	private KafkaSQSSink sink;
	private AmazonSQS sqs;

	@Before
	public void setup() {
		sqs = PowerMock.createMock(AmazonSQS.class);
		sink = new KafkaSQSSink(sqs);
	}

	@Test
	public void testSendingMessageToQueue() {
		EasyMock.expect(sqs.sendMessage("http://foo/bar", "asdf")).andReturn(new SendMessageResult());
		Map<String,String> props = new HashMap<>();
		props.put("sqs.queue.url", "http://foo/bar");

		Collection<SinkRecord> records = new ArrayList<>();
		Schema s = SchemaBuilder.struct().build();
		SinkRecord record = new SinkRecord("topic", 0, s,	"key", s, "asdf", 1);
		sink.start(props);
		records.add(record);


		PowerMock.replayAll();
		sink.put(records);
		PowerMock.verifyAll();
	}
}
