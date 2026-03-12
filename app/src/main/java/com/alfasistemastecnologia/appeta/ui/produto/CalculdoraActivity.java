package com.alfasistemastecnologia.appeta.ui.produto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alfasistemastecnologia.appeta.R;
import com.alfasistemastecnologia.appeta.model.Produto;
import com.alfasistemastecnologia.appeta.repository.ProdutoRepository;
import com.alfasistemastecnologia.appeta.viewmodel.ProdutoViewModel;

/**
 * Activity responsável por listar, editar e excluir produtos.
 *
 * Arquitetura:
 * - MVVM (Activity -> ViewModel -> Repository -> Room)
 * - Observa LiveData para atualização automática da UI
 */
public class CalculdoraActivity extends AppCompatActivity {
    private EditText inputVazao, inputDosagem;
    private RadioGroup groupTipoMedida;
    private TextView labelResultado;
    private Button btnCalcular;
    private Button btnLimpar;
    private AutoCompleteTextView autoProduto;
    private ProdutoViewModel produtoViewModel;
    private ProdutoRepository produtoRepository;

    // VARIÁVEL CHAVE: Guarda o objeto Produto que o usuário escolheu
    private Produto produtoSelecionado;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        // 1. Vincular componentes
        autoProduto = findViewById(R.id.autoProduto);
        inputVazao = findViewById(R.id.inputVazao);
        inputDosagem = findViewById(R.id.inputDosagem);
        labelResultado = findViewById(R.id.labelResultado);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);
        groupTipoMedida = findViewById(R.id.groupTipoMedida);

        // 2. INICIALIZAR a ViewModel (Sem isso, o método não funciona)
        produtoViewModel = new ViewModelProvider(this).get(ProdutoViewModel.class);
        produtoRepository = new ProdutoRepository(getApplication());

        // 3. Capturar qual produto foi clicado no seletor
        autoProduto.setOnItemClickListener((parent, view, position, id) -> {
            // Aqui pegamos o OBJETO Produto real da lista
            produtoSelecionado = (Produto) parent.getItemAtPosition(position);
        });

        // 4. Configurar a lista no seletor
        produtoRepository.listarTodos().observe(this, produtos -> {
            if (produtos != null) {
                ArrayAdapter<Produto> adapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_dropdown_item_1line, produtos);
                autoProduto.setAdapter(adapter);
            }
        });

        // 5. O CLIQUE DO CÁLCULO
        btnCalcular.setOnClickListener(v -> {
            executarCalculo();
        });

        btnLimpar.setOnClickListener(v -> {
            // 1. Limpa os campos de digitação
            inputVazao.setText("");
            inputDosagem.setText("");

            // 2. Limpa o seletor de produto
            autoProduto.setText("");
            produtoSelecionado = null; // Muito importante para não calcular com o produto antigo

            // 3. Limpa o campo de resultado
            labelResultado.setText("Resultado: 0.00");

            // 4. (Opcional) Coloca o foco de volta no primeiro campo
            inputVazao.requestFocus();

            Toast.makeText(this, "Campos limpos", Toast.LENGTH_SHORT).show();
        });

        autoProduto.setOnClickListener(v -> autoProduto.showDropDown());
    }

    private double executarCalculo() {
        if (produtoSelecionado == null) {
            Toast.makeText(this, "Selecione um produto", Toast.LENGTH_SHORT).show();
            return 0;
        }

        // Validação simples para evitar erro de formato antes de tentar converter
        if (inputVazao.getText().toString().isEmpty() || inputDosagem.getText().toString().isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return 0;
        }

        try {
            double vazao = Double.parseDouble(inputVazao.getText().toString());
            double dosagem = Double.parseDouble(inputDosagem.getText().toString());

            double resultado;
            String textoFormatado;

            if (groupTipoMedida.getCheckedRadioButtonId() == R.id.radioMiligamaPorLitro) {
                resultado = produtoViewModel.calcularProdutoMiligramaPorLitro(produtoSelecionado, vazao, dosagem);
                // Com duas casas decimais para mg/L
                textoFormatado = String.format("Resultado: %.2f mg/L", resultado);
            } else {
                resultado = produtoViewModel.calcularProdutoMilitro(produtoSelecionado, vazao, dosagem);
                // %.0f remove todas as casas decimais e arredonda para o inteiro mais próximo
                textoFormatado = String.format("Resultado: %.0f mL", resultado);
            }

            labelResultado.setText(textoFormatado);
            Toast.makeText(this, "Cálculo realizado!", Toast.LENGTH_SHORT).show();

            return resultado;

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Use pontos em vez de vírgulas para decimais", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}