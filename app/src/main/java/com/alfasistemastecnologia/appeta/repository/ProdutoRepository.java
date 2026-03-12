package com.alfasistemastecnologia.appeta.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.alfasistemastecnologia.appeta.database.AppDatabase;
import com.alfasistemastecnologia.appeta.database.ProdutoDao;
import com.alfasistemastecnologia.appeta.model.Produto;
import com.alfasistemastecnologia.appeta.model.ProdutoComCalculadoras;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProdutoRepository {

    private final ProdutoDao produtoDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ProdutoRepository(Context context) {
        AppDatabase db = AppDatabase.getInstancia(context);
        produtoDao = db.produtoDao();
    }

    public ProdutoRepository(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public LiveData<List<Produto>> listarTodos() {
        return produtoDao.listarTodos();
    }

    public LiveData<Produto> buscarPorId(long id) {
        return produtoDao.buscarPorId(id);
    }

    public LiveData<ProdutoComCalculadoras> buscarProdutoComCalculadoras(long id) {
        return produtoDao.buscarProdutoComCalculadoras(id);
    }

    public void adicionar(Produto produto) {
        executor.execute(() -> produtoDao.adicionar(produto));
    }

    public void atualizar(Produto produto) {
        executor.execute(() -> produtoDao.atualizar(produto));
    }

    public void deletar(Produto produto) {
        executor.execute(() -> produtoDao.deletar(produto));
    }
}
