package com.heythere.video.video.message.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserListener {
    private final UserListenerService userListenerService;

    private static final String USER_UPDATE_TOPIC = "user-generate-video-server";
    private static final String USER_DELETED = "user-deleted";

    @KafkaListener(topics = {USER_UPDATE_TOPIC})
    public void onUserUpdate(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        userListenerService.processUpdateUserEvent(consumerRecord);
    }

    @KafkaListener(topics = {USER_DELETED})
    public void onUserDelete(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        userListenerService.processDeleteUserEvent(consumerRecord);
    }
}
