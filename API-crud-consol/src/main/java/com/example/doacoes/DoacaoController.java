package com.example.APIcrudconsol.doacoes;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("doacoes")
public class DoacaoController {


    ArrayList<Doacao> doacoesListaTeste = new ArrayList<>();
    Doacao doacaoTeste1 = new Doacao(1,"Doacao cestaBasica",LocalDate.of(2024,02,20),1);
    Doacao doacaoTeste2 = new Doacao(3,"Doacao cestaBasica",LocalDate.of(2024,04,20),1);

    int idDoacao = 1;

    @PostMapping
    public ResponseEntity<Doacao> cadastrarDoacoes(@RequestBody Doacao doacao){
        doacoesListaTeste.add(doacaoTeste1);
        doacoesListaTeste.add(doacaoTeste2);
        doacao.setDataDoacao(LocalDate.now());
        doacao.setIdDoacao(++idDoacao);
        doacoesListaTeste.add(doacao);



        return ResponseEntity.status(201).body(doacao);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Doacao>> vizualizarDoacoes(){
      if (doacoesListaTeste.isEmpty()){
          return ResponseEntity.status(400).build();
      }

       return ResponseEntity.status(200).body(doacoesListaTeste);
    }

    @GetMapping("/{fkUsuario}")
    public ResponseEntity<ArrayList<Doacao>> vizualizarDoacoesUsuario(@PathVariable int fkUsuario){
        ArrayList<Doacao> doacoesPorUsuario = new ArrayList<>();

        for (int i = 0; i < doacoesListaTeste.size(); i++) {
            if (doacoesListaTeste.get(i).getFkUsuario() == fkUsuario){
                doacoesPorUsuario.add(doacoesListaTeste.get(i));
            }
        }
        
        ordenarDoacoes(doacoesPorUsuario);
        return ResponseEntity.status(201).body(doacoesPorUsuario);
        
    }
    
    
    public void ordenarDoacoes(ArrayList<Doacao> v) {
        for (int i = 0; i < v.size() - 1; i++) {

            for (int j = i + 1; j < v.size(); j++) {

                if (v.get(i).getDataDoacao().isBefore(v.get(j).getDataDoacao())){

                    Doacao aux = v.get(i);
                    v.set(i,v.get(j));
                    v.set(j,aux);

                }
            }

        }

    }
    

}
