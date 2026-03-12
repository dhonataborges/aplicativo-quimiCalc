package com.alfasistemastecnologia.appeta.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

public class ProdutoComCalculadoras {

    @Embedded
    public Produto produto;

    @Relation(
            parentColumn = "idProduto",
            entityColumn = "idProduto"
    )
    public List<Calculadora> calculadoras;
}
