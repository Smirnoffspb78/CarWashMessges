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
@Document(collection = "message")
@ToString
public class Message {
    /**
     * Идентификатор сообщения.
     */
    @Id
    @Setter
    private String id;

    /**
     * Наименование сообщения.
     */
    private String name;

    /**
     * Тема сообщения.
     */
    private String subject;

    /**
     * Текст сообщения.
     */
    private String text;

    public void setName(String name) {
        checkNullAndBlank(name);
        this.name = name;
    }

    public void setSubject(String subject) {
        checkNullAndBlank(name);
        this.subject = subject;
    }

    public void setText(String text) {
        checkNullAndBlank(name);
        this.text = text;
    }

    private void checkNullAndBlank(String value){
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("value is null or empty");
        }
    }
}
