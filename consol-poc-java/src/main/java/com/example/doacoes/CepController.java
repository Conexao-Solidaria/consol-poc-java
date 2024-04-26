package com.example.doacoes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/cep")
public class CepController {
    private final String CEP_API_URL = "https://viacep.com.br/ws/";


//    @GetMapping
//    public String pegarCep() throws IOException {
//        StringBuilder result = new StringBuilder();
//        URL url = new URL("https://viacep.com.br/ws/01001000/json/");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        try (BufferedReader reader = new BufferedReader(
//                new InputStreamReader(con.getInputStream()))) {
//            for (String line; (line = reader.readLine()) != null; ) {
//                result.append(line);
//            }
//        }
//        return result.toString();
//    }

    @GetMapping("/{cep}")
    public ResponseEntity<String> pegarCep(@PathVariable String cep) {
        String url = CEP_API_URL + cep + "/json";
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);

        if(forObject == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(restTemplate.getForObject(url, String.class));
    }
}
