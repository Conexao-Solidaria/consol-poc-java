package com.example.doacoes;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("doacoes")
public class DoacaoController {
    ArrayList<Doacao> doacoesListaTeste = new ArrayList<>();
    Doacao doacaoTeste1 = new Doacao(1,"Doacao cestaBasica",LocalDate.of(2024,02,20),1);
    Doacao doacaoTeste2 = new Doacao(2,"Doacao cestaBasica",LocalDate.of(2024,04,20),1);

    int idDoacao = 2;

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
        
        mergeSort(0, doacoesPorUsuario.size(), doacoesPorUsuario);
        return ResponseEntity.status(201).body(doacoesPorUsuario);
        
    }

    @GetMapping("/pegar-um-usuario/{valor}")
    public ResponseEntity<Doacao> pegarUmaDoacao(@PathVariable int valor){
        ordenarDoacoes(doacoesListaTeste);

        Doacao doacaoAchada = binaria(doacoesListaTeste, valor);

        if(doacaoAchada == null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(doacaoAchada);
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

    static public Doacao binaria(ArrayList<Doacao> v, int x){
        int indInicio = 0;
        int indFim = v.size()-1;

        while(indInicio <= indFim){
            int indMedio = (indInicio + indFim)/2;

            if (x == v.get(indMedio).getIdDoacao()){
                return v.get(indMedio);

            } else if (x > v.get(indMedio).getIdDoacao()) {
                indInicio = indMedio + 1;

            }else{
                indFim = indMedio - 1;

            }
        }
        return null;
    }

    public static void mergeSort(int p, int r, ArrayList<Doacao> v){
        if(p < r-1) {
            int q = (p + r) / 2;
            mergeSort(p, q, v);
            mergeSort(q, r, v);
            intercala(p,q,r,v);
        }
    }

    public static void intercala(int p, int q, int r, ArrayList<Doacao> v){
        int i, j, k;
        Doacao[] w = new Doacao[r-p];

        i = p;
        j = q;
        k = 0;

        while(i < q && j < r){
            if(v.get(i).getDataDoacao().isBefore(v.get(j).getDataDoacao())){
                w[k++] = v.get(i++);
            }
            else{
                w[k++] = v.get(j++);
            }
        }

        while(i < q){
            w[k++] = v.get(i++);
        }
        while(j < r){
            w[k++] = v.get(j++);
        }

        for (int l = p; l < r ; l++) {
            v.set(l, w[l - p]);
        }
    }
}
