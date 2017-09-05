package io.jessed.sqs.kafka.connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.utils.AppInfoParser;

import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import io.jessed.sqs.kafka.connect.KafkaSQSConnectorConfig;
import io.jessed.sqs.kafka.connect.KafkaSQSSink;

public class KafkaSQSSinkConnector extends SinkConnector {
	private Map<String, String> configProps;

	@Override
	public ConfigDef config() {
		return KafkaSQSConnectorConfig.config;
	}

	@Override
	public void start(Map<String, String> map) {
		this.configProps = map;
	}

	@Override
	public void stop() {
	}

	@Override
	public Class<? extends Task> taskClass() {
		return KafkaSQSSink.class;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int capacity) {
		List<Map<String,String>> taskConfigs = new ArrayList<>(capacity);
		Map<String,String> taskProps = new HashMap<>(this.configProps);
		for (int i = 0; i < capacity; i++) {
			taskConfigs.add(taskProps);
		}
		return taskConfigs;
	}

	@Override
	public String version() {
		return AppInfoParser.getVersion();
	}
}
