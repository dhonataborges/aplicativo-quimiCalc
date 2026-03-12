package com.alfasistemastecnologia.appeta.viewmodel;

import androidx.lifecycle.ViewModel;

import com.alfasistemastecnologia.appeta.model.Produto;

public class ProdutoViewModel extends ViewModel {

    public double calcularProdutoMilitro(Produto produto, double vazao, double dosagem) {
        double vazaoMinuto = calcularVazaoMinuto(vazao);
        // Evita divisão por zero caso a concentração não esteja preenchida
        if (produto.getConcentracaoFinal() == 0) return 0;

        return (dosagem * vazaoMinuto) / produto.getConcentracaoFinal();
    }

    public double calcularProdutoMiligramaPorLitro(Produto produto, double vazao, double dosagem) {
        double vazaoMinuto = calcularVazaoMinuto(vazao);
        // Evita divisão por zero caso a vazão seja zero
        if (vazaoMinuto == 0) return 0;

        return (dosagem * produto.getConcentracaoFinal()) / vazaoMinuto;
    }

    // Tornar privado se apenas esta classe usa, e renomear para clareza
    private double calcularVazaoMinuto(double vazao) {
        return vazao * 6;
    }
}

