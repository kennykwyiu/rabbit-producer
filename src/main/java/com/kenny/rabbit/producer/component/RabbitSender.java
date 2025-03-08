package com.kenny.rabbit.producer.component;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * Callback method triggered after the message is sent to the broker.
         *
         * @param correlationData Correlation data associated with the message (used for tracking)
         * @param ack Indicates whether the broker successfully received the message:
         *            - true: Message was successfully received by the broker.
         *            - false: Message was not received.
         * @param cause If ack is false, this provides the reason for the failure.
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        }
    };


}
