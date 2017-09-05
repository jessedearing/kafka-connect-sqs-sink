package io.jessed.sqs.kafka.connect;

import com.amazonaws.regions.Regions;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.common.utils.AppInfoParser;

import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.kafka.connect.storage.Converter;
import org.apache.kafka.connect.storage.StringConverter;

public class KafkaSQSSink extends SinkTask
{
	private String queueUrl;
	private AmazonSQS client;

	public KafkaSQSSink() {
		super();
	}

	public KafkaSQSSink(AmazonSQS client) {
		super();
		this.client = client;
	}

	@Override
	public void put(Collection<SinkRecord> collection) {
		for (SinkRecord s :	collection) {
			client.sendMessage(this.queueUrl, s.value().toString());
		}
	}

	@Override
	public void start(Map<String, String> map) {
		if(client == null) {
			this.client = AmazonSQSClientBuilder.standard()
				.withRegion(Regions.US_EAST_1)
				.build();
		}

		this.queueUrl = map.get("sqs.queue.url");
	}

	@Override
	public void stop() {
		this.client.shutdown();
	}

	@Override
	public String version() {
		return AppInfoParser.getVersion();
	}
}
