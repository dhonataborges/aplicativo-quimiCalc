package com.alfasistemastecnologia.appeta.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.alfasistemastecnologia.appeta.model.Produto;
import com.alfasistemastecnologia.appeta.model.ProdutoComCalculadoras;

import java.util.List;

@Dao
public interface ProdutoDao {

    @Insert
    void adicionar(Produto produto);

    @Update
    void atualizar(Produto produto);

    @Delete
    void deletar(Produto produto);

    @Query("SELECT * FROM tb_produto ORDER BY idProduto DESC")
    LiveData<List<Produto>> listarTodos();

    @Query("SELECT * FROM tb_produto WHERE idProduto = :id LIMIT 1")
    LiveData<Produto> buscarPorId(long id);

    @Transaction
    @Query("SELECT * FROM tb_produto WHERE idProduto = :id")
    LiveData<ProdutoComCalculadoras> buscarProdutoComCalculadoras(long id);

}
