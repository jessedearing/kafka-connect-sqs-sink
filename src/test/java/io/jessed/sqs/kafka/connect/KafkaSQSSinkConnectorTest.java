package io.jessed.sqs.kafka.connect.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.connector.ConnectorContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.easymock.PowerMock;

import io.jessed.sqs.kafka.connect.KafkaSQSSink;
import io.jessed.sqs.kafka.connect.KafkaSQSSinkConnector;

public class KafkaSQSSinkConnectorTest {
	private KafkaSQSSinkConnector connector;
	private ConnectorContext context;

	@Before
	public void setup() {
		connector = new KafkaSQSSinkConnector();
		context = PowerMock.createMock(ConnectorContext.class);
	}

	private Map<String,String> createSinkProps() {
		final Map<String,String> sinkProps = new HashMap<>();
		sinkProps.put("sqs.queue.url", "http://foo/bar");
		return sinkProps;
	}

	@Test
	public void testSetUrl() {
		PowerMock.replayAll();

		Map<String,String> sinkProps = createSinkProps();

		connector.start(sinkProps);
		List<Map<String,String>> taskConfigs = connector.taskConfigs(1);
		Assert.assertEquals("http://foo/bar", taskConfigs.get(0).get("sqs.queue.url"));
		PowerMock.verifyAll();
	}

	@Test
	public void testTaskClass() {
		PowerMock.replayAll();
		Map<String,String> sinkProps = createSinkProps();
		connector.start(sinkProps);

		Assert.assertEquals(KafkaSQSSink.class, connector.taskClass());
		PowerMock.verifyAll();
	}
}
