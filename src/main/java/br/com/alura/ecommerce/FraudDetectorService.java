package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER")); //assina o topico

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100)); //Pergunta para o tópico se tem algum registro por 100 milisegundos

            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");

                for (var record : records) {
                    System.out.println("------------------------------------------");
                    System.out.println("Processing new Order, checking for fraud");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        //ignoring: é só uma simulação de tempo
                        e.printStackTrace();
                    }
                    System.out.println("Order processed");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());

        return properties;
    }
}
