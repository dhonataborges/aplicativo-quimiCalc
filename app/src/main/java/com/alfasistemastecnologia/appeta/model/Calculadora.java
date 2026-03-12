package com.alfasistemastecnologia.appeta.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "tb_calculadora",
        foreignKeys = @ForeignKey(
                entity = Produto.class,
                parentColumns = "idProduto",
                childColumns = "idProduto",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("idProduto")}
)
public class Calculadora {
    @PrimaryKey(autoGenerate = true)
    private long idCalculadora;
    private long idProduto;
    private double vazao;
    private double dosagem;
    private double resultadoMG;

    public Calculadora() {
        // Construtor vazio obrigatório para o Room
    }

    public Calculadora(double vazao, double dosagem, double resultadoMG) {
        this.vazao = vazao;
        this.dosagem = dosagem;
        this.resultadoMG = resultadoMG;
    }

    public long getIdCalculadora() {
        return idCalculadora;
    }

    public void setIdCalculadora(long idCalculadora) {
        this.idCalculadora = idCalculadora;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public double getVazao() {
        return vazao;
    }

    public void setVazao(double vazao) {
        this.vazao = vazao;
    }

    public double getDosagem() {
        return dosagem;
    }

    public void setDosagem(double dosagem) {
        this.dosagem = dosagem;
    }

    public double getResultadoMG() {
        return resultadoMG;
    }

    public void setResultadoMG(double resultadoMG) {
        this.resultadoMG = resultadoMG;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Calculadora that = (Calculadora) o;
        return idCalculadora == that.idCalculadora && idProduto == that.idProduto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCalculadora, idProduto);
    }

    @Override
    public String toString() {
        return "Calculadora{" +
                "vazao=" + vazao +
                ", dosagem=" + dosagem +
                ", resultadoMG=" + resultadoMG +
                '}';
    }
}
