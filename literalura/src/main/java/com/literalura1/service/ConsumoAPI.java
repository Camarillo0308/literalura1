package com.literalura1.service;

import com.google.gson.Gson;
import com.literalura1.model.RespuestaAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    public RespuestaAPI buscarLibroPorTitulo(String titulo) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + titulo.replace(" ", "%20")))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            return gson.fromJson(response.body(), RespuestaAPI.class);

        } catch (Exception e) {
            throw new RuntimeException("Error al consumir la API", e);
        }
    }
}
