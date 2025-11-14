package com.example.events_service.messaging.events;

import com.example.events_service.messaging.RabbitConfig;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class UserCreatedListener {

    @RabbitListener(queues = RabbitConfig.NOTIFICATION_QUEUE_NAME)
    public void onUserCreated(UserCreatedEvent event) {
        System.out.println("Usuário recebido: " + event);
    }

}
