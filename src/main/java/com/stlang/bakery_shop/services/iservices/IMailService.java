package com.stlang.bakery_shop.services.iservices;

import lombok.Builder;
import lombok.Data;

public interface IMailService {

    void send(MailModel mailModel);
    default void send(String to, String subject, String body) {
        MailModel mail = MailModel.builder().to(to).subject(subject).body(body).build();
        this.send(mail);
    }
    default void sendWelcome() {}
    default void sendPassword() {}
    default void sendOrder() {}

    @Builder
    @Data
    public static class MailModel{
        @Builder.Default
        String from = "Bakery <sondoquang3@gmail.com>";
        String to, cc, bcc, subject, body, filenames;
    }

}
