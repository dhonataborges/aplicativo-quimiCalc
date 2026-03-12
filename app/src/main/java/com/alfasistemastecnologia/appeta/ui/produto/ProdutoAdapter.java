package com.alfasistemastecnologia.appeta.ui.produto;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfasistemastecnologia.appeta.R;
import com.alfasistemastecnologia.appeta.model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private List<Produto> listaProdutos;
    private OnProdutoClickListener listener;

    // Interface para capturar os cliques na Activity
    public interface OnProdutoClickListener {
        void onEditClick(Produto produto);
        void onDeleteClick(Produto produto);
    }

    // Seu construtor atual
    public ProdutoAdapter(List<Produto> lista, OnProdutoClickListener listener) {
        this.listaProdutos = lista;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProdutos(List<Produto> novosProdutos) {
        this.listaProdutos = novosProdutos;
        notifyDataSetChanged(); // Isso avisa a lista para se redesenhar com os novos dados
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.txtNome.setText(produto.getNome());
        holder.txtConcetracaoProdutoLista.setText(String.valueOf((int) produto.getConcentracaoFinal()));

        // Clique no Ícone Editar
        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(produto));

        // Clique no Ícone Deletar
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(produto));
    }

    @Override
    public int getItemCount() { return listaProdutos.size(); }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome,txtConcetracaoProdutoLista;
        ImageView btnEdit, btnDelete;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeProdutoLista);
            txtConcetracaoProdutoLista = itemView.findViewById(R.id.txtConcentracaoProdutoLista);
            btnEdit = itemView.findViewById(R.id.btnEditar);
            btnDelete = itemView.findViewById(R.id.btnDeletar);
        }
    }
}