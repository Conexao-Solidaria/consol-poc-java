package com.example.doacoes;

import com.example.ListaObj;
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
import java.util.List;

@RestController
@RequestMapping("/cep")
public class CepController {
    private final String CEP_API_URL = "https://viacep.com.br/ws/";

    private ListaObj<Cep> listaObj = new ListaObj<>(20);


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
    public ResponseEntity<Cep> pegarCep(@PathVariable String cep) {
        String url = CEP_API_URL + cep + "/json";
        RestTemplate restTemplate = new RestTemplate();
        Cep forObject = restTemplate.getForObject(url, Cep.class);

        if(forObject == null || forObject.getCep() == null){
            return ResponseEntity.notFound().build();
        }

        listaObj.adiciona(forObject);

        return ResponseEntity.ok().body(forObject);
    }

    @GetMapping
    public ResponseEntity<ListaObj<Cep>> pegarTodos(){
        if(listaObj.getTamanho() == 0){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listaObj);
    }

    @GetMapping("/ordenar-cep")
    public ResponseEntity<ListaObj<Cep>> pegarTodosOrdenados(){
        if(listaObj.getTamanho() == 0){
            return ResponseEntity.noContent().build();
        }

        ordenarCep(listaObj);

        return ResponseEntity.ok(listaObj);
    }

    public void ordenarCep (ListaObj<Cep> v) {
        for (int i = 0; i < v.getTamanho() - 1; i++) {

            for (int j = i + 1; j < v.getTamanho(); j++) {

                if (Integer.parseInt(v.getElemento(i).getIbge()) > Integer.parseInt(v.getElemento(j).getIbge())){
                    Cep aux = v.getElemento(i);
                    v.trocarPosicao(v.getElemento(i),v.getElemento(j));
                    v.trocarPosicao(v.getElemento(j),aux);
                }
            }
        }
    }
}
