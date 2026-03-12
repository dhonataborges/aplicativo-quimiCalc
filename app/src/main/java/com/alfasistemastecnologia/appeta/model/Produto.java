package com.alfasistemastecnologia.appeta.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_produto")
public class Produto {
    @PrimaryKey(autoGenerate = true)
    private long idProduto;
    private String nome;
    private double densidade;
    private double concentracao;
    private double concentracaoFinal;

    public Produto() {
        // Construtor vazio obrigatório para o Room
    }

    public  Produto(String nome, double densidade, double concentracao, double concentracaoFinal) {
        this.nome = nome;
        this.densidade = densidade;
        this.concentracao = concentracao;
        this.concentracaoFinal = concentracaoFinal;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDensidade() {
        return densidade;
    }

    public void setDensidade(double densidade) {
        this.densidade = densidade;
    }
    public double getConcentracao() {
        return concentracao;
    }

    public void setConcentracao(double concentracao) {
        this.concentracao = concentracao;
    }

    public double getConcentracaoFinal() {
        return concentracaoFinal;
    }

    public void setConcentracaoFinal(double concentracaoFinal) {
        this.concentracaoFinal = concentracaoFinal;
    }

    @Override
    public String toString() {
        // Retorna apenas o nome para que o seletor fique limpo
        return nome;
    }
}
