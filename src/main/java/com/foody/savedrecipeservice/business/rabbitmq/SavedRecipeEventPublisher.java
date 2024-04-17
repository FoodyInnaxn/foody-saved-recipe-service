package com.foody.savedrecipeservice.business.rabbitmq;

import com.foody.savedrecipeservice.business.rabbitmq.event.SavedRecipeEvent;
import com.foody.savedrecipeservice.configuration.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SavedRecipeEventPublisher {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void publishSavedRecipeEvent(SavedRecipeEvent savedRecipeEvent) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, savedRecipeEvent);
    }

}
