package com.alfasistemastecnologia.appeta.ui.produto;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alfasistemastecnologia.appeta.model.Produto;
import com.alfasistemastecnologia.appeta.repository.ProdutoRepository;

import java.util.List;

/**
 * ViewModel responsável pela regra de negócio de Produto.
 *
 * Responsabilidades:
 * - Expor dados para a UI via LiveData
 * - Delegar persistência ao Repository
 * - NÃO acessar UI
 * - NÃO acessar banco diretamente
 */
public class ProdutoViewModel extends AndroidViewModel {

    // Repository que encapsula acesso ao Room
    private final ProdutoRepository produtoRepository;

    /**
     * Construtor obrigatório para AndroidViewModel.
     * O Application é necessário para inicializar o Repository.
     */
    public ProdutoViewModel(@NonNull Application application) {
        super(application);
        produtoRepository = new ProdutoRepository(application);
    }

    /**
     * Retorna a lista de produtos observável pela UI.
     * Sempre que o banco mudar, a tela será atualizada automaticamente.
     */
    public LiveData<List<Produto>> listarTodos() {
        return produtoRepository.listarTodos();
    }

    /**
     * Retorna um produto específico por ID.
     * Ideal para telas de detalhe ou edição.
     */
    public LiveData<Produto> buscarPorId(long idProduto) {
        return produtoRepository.buscarPorId(idProduto);
    }

    /**
     * Solicita a inclusão de um novo produto.
     *
     * IMPORTANTE:
     * - Não retorna Produto
     * - A UI deve observar o LiveData da lista
     */
    public void adicionar(Produto produto) {
        produtoRepository.adicionar(produto);
    }

    /**
     * Solicita atualização de um produto existente.
     */
    public void atualizar(Produto produto) {
        produtoRepository.atualizar(produto);
    }

    /**
     * Solicita exclusão de um produto.
     *
     * ATENÇÃO:
     * - Exclusão usa CASCADE
     * - Todas as calculadoras vinculadas ao produto serão removidas
     */
    public void deletar(Produto produto) {
        produtoRepository.deletar(produto);
    }
}
