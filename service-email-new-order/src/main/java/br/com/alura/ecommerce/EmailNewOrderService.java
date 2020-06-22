package br.com.alura.ecommerce;

import br.com.alura.ecommerce.consumer.KafkaService;
import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class EmailNewOrderService {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var emailNewOrderService = new EmailNewOrderService();

        try (var service = new KafkaService<>(EmailNewOrderService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                emailNewOrderService::parse,
                new HashMap<String, String>())) {

            service.run();
        }
    }

    private final KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<>();

    private void parse(ConsumerRecord<String, Message<Order>> record) throws ExecutionException, InterruptedException {
        System.out.println("------------------------------------------");
        System.out.println("Processing new Order, preparing e-mail");
        System.out.println(record.value());

        var order = record.value().getPayload();
        var emailCode = "Thank tou for your order! We are processing your order!";
        emailDispatcher.send("ECOMMERCE_SEND_EMAIL",
                order.getEmail(),
                record.value().getId().continueWith(EmailNewOrderService.class.getSimpleName()),
                emailCode);
    }
}
