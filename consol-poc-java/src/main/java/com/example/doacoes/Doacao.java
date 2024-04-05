package com.example.doacoes;

import java.time.LocalDate;

public class Doacao {
    private int idDoacao;
    private String descricao;
    private LocalDate dataDoacao;
    private int fkUsuario;

    public Doacao() {
    }

    public Doacao(String descricao, int fkUsuario) {
        this.descricao = descricao;
        this.fkUsuario = fkUsuario;
    }

    public Doacao(int idDoacao, String descricao, LocalDate dataDoacao, int fkUsuario) {
        this.idDoacao = idDoacao;
        this.descricao = descricao;
        this.dataDoacao = dataDoacao;
        this.fkUsuario = fkUsuario;
    }

    public int getIdDoacao() {
        return idDoacao;
    }

    public void setIdDoacao(int idDoacao) {
        this.idDoacao = idDoacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descrição) {
        this.descricao = descrição;
    }

    public LocalDate getDataDoacao() {
        return dataDoacao;
    }

    public void setDataDoacao(LocalDate dataDoação) {
        this.dataDoacao = dataDoação;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }
}
