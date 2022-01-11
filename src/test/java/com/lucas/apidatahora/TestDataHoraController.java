package com.lucas.apidatahora;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSender;
import net.bytebuddy.agent.VirtualMachine;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDataHoraController {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void deveRetornarDataHoraComTimezoneDefault() {
        var metodo = "GET";
        var endpoint = "/data-hora";

        var response = when()
                .request(metodo, endpoint)
                .then()
                .extract()
                .response();

        var json = response.jsonPath();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        assertNotNull(json.get("dataHora"));
    }

    @Test
    void deveLancarExecaoTimezoneInvalido() {
        var metodo = "GET";
        var endpoint = "/data-hora?timezone=teste";

        var response = when()
                .request(metodo, endpoint)
                .then()
                .extract()
                .response();

        var json = response.jsonPath();

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode());

        assertNull(json.get("message"));
    }


}
