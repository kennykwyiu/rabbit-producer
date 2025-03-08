package com.kenny.rabbit.producer.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
@Slf4j
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

    /**
     * Sends a message to the RabbitMQ exchange with custom properties and confirmation handling.
     *
     * @param message    The actual message payload to send.
     * @param properties Additional properties (headers) to be included in the message.
     * @throws Exception If any error occurs during message processing.
     */
    public void send (Object message, Map<String, Object> properties) throws Exception {

        MessageHeaders mhs = new MessageHeaders(properties);
        Message<?> msg = MessageBuilder.createMessage(message, mhs);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        MessagePostProcessor mpp = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                log.info("---> post to do: "  + message);
                return message;
            }
        };

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.convertAndSend("exchange-1",
                "springboot.rabbit",
                msg,
                mpp,
                correlationData
        );

    }

}
