package com.flink.example;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.ConsumerConfig;

/** Hello world! */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Flink Job");
        
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> source =
                KafkaSource.<String>builder()
                        .setBootstrapServers("one-node-external.flink-poc.svc.cluster.local:9092")
                        .setTopics("flink-poc")
                        .setGroupId("my-group-2")
                        .setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest")
                        .setValueOnlyDeserializer(new SimpleStringSchema())
                        .build();

        env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source")
                .map(
                        new MapFunction<String, String>() {

                            @Override
                            public String map(String s) throws Exception {
                                Thread.sleep(1000);
                                return s;
                            }
                        })
                .print();

        env.execute();
    }
}
