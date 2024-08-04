package com.smirnov.carwashmessage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Сообщение.
 */
@Getter
@Setter
@Document(collection = "message")
@ToString
public class Message {
    /**
     * Идентификатор сообщения.
     */
    @Id
    private String id;

    /**
     * Тема сообщения.
     */
    private String subject;

    /**
     * Текст сообщения.
     */
    private String text;
}
