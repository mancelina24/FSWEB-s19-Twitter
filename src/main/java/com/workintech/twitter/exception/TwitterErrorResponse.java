package com.workintech.twitter.exception;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TwitterErrorResponse {
    private String message;
    private int status;
    private long timestamp;
// private LocalDateTime localDateTime;
    public TwitterErrorResponse(String message) {
        this.message = message;
    }
}
//REST mimarisinde: Request geldiğinde bir hata var ise Requestin beklediği bir Response olmalı
//Twitter exceptionda veya ondan türeyen bir hata olursa handle et
//Bizim dışımızda yani TwitterExceptiondan türemeyen olursa handle et. Çünkü dışarı giden mesaj okumak kolaylaşmalı