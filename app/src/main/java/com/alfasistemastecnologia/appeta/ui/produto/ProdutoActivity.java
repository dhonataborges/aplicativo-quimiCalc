package com.alfasistemastecnologia.appeta.ui.produto;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alfasistemastecnologia.appeta.R;
import com.alfasistemastecnologia.appeta.model.Produto;
import com.alfasistemastecnologia.appeta.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Activity responsável por listar, editar e excluir produtos.
 *
 * Arquitetura:
 * - MVVM (Activity -> ViewModel -> Repository -> Room)
 * - Observa LiveData para atualização automática da UI
 */
public class ProdutoActivity extends AppCompatActivity {
    private Produto produtoEmEdicao = null;
    private ProdutoRepository produtoRepository;
    private ProdutoAdapter adapter;
    private RecyclerView recycler;
    private EditText inputProduto, inputDensidade, inputConcentracao, inputConcentracaoFinal;
    private RadioGroup groupDensidade;
    private Button btnCadastrar, btnCancelar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        // 1. Inicialização dos componentes
        produtoRepository = new ProdutoRepository(getApplication());

        inputProduto = findViewById(R.id.inputProduto);
        groupDensidade = findViewById(R.id.groupDensidade);
        inputDensidade = findViewById(R.id.inputDensidade);
        inputConcentracao = findViewById(R.id.inputConcentracao);
        inputConcentracaoFinal = findViewById(R.id.inputConcentracaoFinal);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCancelar = findViewById(R.id.btnCancelar);
        recycler = findViewById(R.id.recyclerViewProdutos);

        // No onCreate, logo após inicializar o 'recycler'
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutoAdapter(new ArrayList<>(), new ProdutoAdapter.OnProdutoClickListener() {
            @Override public void onEditClick(Produto p) { preencherCamposParaEdicao(p); }
            @Override public void onDeleteClick(Produto p) { confirmarExclusao(p); }
        });
        recycler.setAdapter(adapter);

        // 2. Ouvinte Único para o RadioGroup (Habilitar/Desabilitar e Bloquear Cálculo)
        groupDensidade.setOnCheckedChangeListener((group, checkedId) -> {
            boolean ativo = (checkedId == R.id.radioSim);

            // Habilita/Desabilita campos de entrada
            inputDensidade.setEnabled(ativo);
            inputConcentracao.setEnabled(ativo);

            // Define opacidade visual
            float alpha = ativo ? 1.0f : 0.5f;
            inputDensidade.setAlpha(alpha);
            inputConcentracao.setAlpha(alpha);

            if (ativo) {
                // Se SIM, o campo final vira apenas leitura (calculado)
                inputConcentracaoFinal.setEnabled(false);
                inputConcentracaoFinal.setAlpha(0.8f);
                calcularConcentracaoFinal();
            } else {
                // Se NÃO, limpa campos e permite digitar a concentração final manualmente
                inputDensidade.setText("");
                inputConcentracao.setText("");
                inputConcentracaoFinal.setEnabled(true);
                inputConcentracaoFinal.setAlpha(1.0f);
                inputConcentracaoFinal.setText("");
            }
        });

        // 3. Vigilante de texto para cálculo automático em tempo real
        TextWatcher autoCalculoWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularConcentracaoFinal();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        inputDensidade.addTextChangedListener(autoCalculoWatcher);
        inputConcentracao.addTextChangedListener(autoCalculoWatcher);

        // 4. Configuração dos Botões
        btnCadastrar.setOnClickListener(v -> salvarDados());
        btnCancelar.setOnClickListener(v -> finish());

        // 5. Observar dados do Banco para a Lista
        produtoRepository.listarTodos().observe(this, produtos -> {
            if (produtos != null) {
                // NÃO use 'new ProdutoAdapter' aqui. Use apenas o método abaixo:
                adapter.setProdutos(produtos);
            }
        });
    }

    private void calcularConcentracaoFinal() {
        if (groupDensidade.getCheckedRadioButtonId() == R.id.radioSim) {
            String denciStr = inputDensidade.getText().toString().trim();
            String concentStr = inputConcentracao.getText().toString().trim();

            if (!denciStr.isEmpty() && !concentStr.isEmpty()) {
                try {
                    double denci = Double.parseDouble(denciStr);
                    double concent = Double.parseDouble(concentStr);
                    double resultado = denci * concent;

                    // Formata com ponto como separador decimal para o Double.parseDouble aceitar depois
                    inputConcentracaoFinal.setText(String.format(Locale.US, "%.2f", resultado));
                } catch (NumberFormatException e) {
                    inputConcentracaoFinal.setText("");
                }
            }
        }
    }

    private void salvarDados() {
        String nome = inputProduto.getText().toString().trim();
        String dStr = inputDensidade.getText().toString().trim();
        String cStr = inputConcentracao.getText().toString().trim();
        String cfStr = inputConcentracaoFinal.getText().toString().trim();

        if (nome.isEmpty() || cfStr.isEmpty()) {
            Toast.makeText(this, "Nome e Conc. Final são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            boolean possuiD = groupDensidade.getCheckedRadioButtonId() == R.id.radioSim;
            Double densidade = (possuiD && !dStr.isEmpty()) ? Double.parseDouble(dStr) : 0.0;
            Double concentracao = (possuiD && !cStr.isEmpty()) ? Double.parseDouble(cStr) : 0.0;
            Double concFinal = Double.parseDouble(cfStr);

            if (produtoEmEdicao == null) {
                Produto novo = new Produto();
                configurarProduto(novo, nome, densidade, concentracao, concFinal);
                produtoRepository.adicionar(novo);
                Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                configurarProduto(produtoEmEdicao, nome, densidade, concentracao, concFinal);
                produtoRepository.atualizar(produtoEmEdicao);
                produtoEmEdicao = null;
                btnCadastrar.setText("CADASTRAR");
                Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            }

            limparCampos();

        } catch (Exception e) {
            Toast.makeText(this, "Erro: Verifique os valores numéricos", Toast.LENGTH_SHORT).show();
        }
    }

    private void configurarProduto(Produto p, String n, Double d, Double c, Double cf) {
        p.setNome(n);
        p.setDensidade(d);
        p.setConcentracao(c);
        p.setConcentracaoFinal(cf);
    }

    private void preencherCamposParaEdicao(Produto p) {
        produtoEmEdicao = p;
        inputProduto.setText(p.getNome());
        inputDensidade.setText(String.valueOf(p.getDensidade()));
        inputConcentracao.setText(String.valueOf(p.getConcentracao()));
        inputConcentracaoFinal.setText(String.valueOf(p.getConcentracaoFinal()));

        // Ativa o rádio correto
        if (p.getDensidade() > 0) {
            groupDensidade.check(R.id.radioSim);
        } else {
            groupDensidade.check(R.id.radioNao);
        }

        btnCadastrar.setText("ATUALIZAR");
    }

    private void limparCampos() {
        inputProduto.setText("");
        inputDensidade.setText("");
        inputConcentracao.setText("");
        inputConcentracaoFinal.setText("");
        groupDensidade.check(R.id.radioSim);
        inputProduto.requestFocus();
    }

    private void confirmarExclusao(Produto p) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir")
                .setMessage("Deseja excluir " + p.getNome() + "?")
                .setPositiveButton("Sim", (d, w) -> produtoRepository.deletar(p))
                .setNegativeButton("Não", null).show();
    }
}