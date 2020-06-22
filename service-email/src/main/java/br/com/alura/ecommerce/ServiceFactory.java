package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;

public interface ServiceFactory<T> {

    ConsumerService<T> create();

}
