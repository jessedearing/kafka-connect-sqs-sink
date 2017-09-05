package io.jessed.sqs.kafka.connect;

import java.util.Map;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

public class KafkaSQSConnectorConfig extends AbstractConfig {
	public static ConfigDef baseConfigDef() {
		return new ConfigDef()
			.define("sqs.queue.url", ConfigDef.Type.STRING, ConfigDef.Importance.HIGH,
					"The SQS URL");
			//.define("aws.key", ConfigDef.Type.STRING, ConfigDef.Importance.HIGH,
					//"AWS IAM Key")
			//.define("aws.secret", ConfigDef.Type.STRING, ConfigDef.Importance.HIGH,
					//"AWS IAM Secret");
	}

	public static final ConfigDef config = baseConfigDef();

	public KafkaSQSConnectorConfig(Map<String, String> props) {
		super(config, props);
	}
}
