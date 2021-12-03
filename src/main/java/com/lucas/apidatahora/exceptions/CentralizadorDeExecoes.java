package com.lucas.apidatahora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.zone.ZoneRulesException;

@RestControllerAdvice
public class CentralizadorDeExecoes {

    private Erro buildErro(Exception e) {
        return Erro.newBuilder()
                .withDataHoraErro(LocalDateTime.now())
                .withMensagem(e.getMessage());
    }

    @ExceptionHandler(ZoneRulesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Erro handleZoneRulesException(ZoneRulesException e) {
        //Monta o erro que será retornado
        //return buildErro(e);
        return buildErro(new RuntimeException("Por favor, forneca um timezone valido"));
    }

    @ExceptionHandler(DateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Erro handleDateTimeException(DateTimeException e) {
        //Monta o erro que será retornado
        //return buildErro(e);
        return buildErro(new RuntimeException("Por favor, forneca um timezone valido"));
    }
}


class Erro {
    private LocalDateTime dataHoraErro;
    private String mensagem;

    public Erro() {
    }

    public static Erro newBuilder() {
        return new Erro();
    }

    public Erro withDataHoraErro(final LocalDateTime dataHoraErro) {
        this.dataHoraErro = dataHoraErro;
        return this;
    }

    public Erro withMensagem(final String mensagem) {
        this.mensagem = mensagem;
        return this;
    }


    public LocalDateTime getDataHoraErro() {
        return dataHoraErro;
    }

    public void setDataHoraErro(LocalDateTime dataHoraErro) {
        this.dataHoraErro = dataHoraErro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}