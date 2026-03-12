package com.alfasistemastecnologia.appeta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.alfasistemastecnologia.appeta.ui.produto.CalculdoraActivity;
import com.alfasistemastecnologia.appeta.ui.produto.ProdutoActivity;
import com.google.android.material.card.MaterialCardView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // 1. APENAS DECLARE as variáveis aqui. Não use o findViewById aqui!
    private MaterialCardView cardCadastrar;
    private MaterialCardView cardDosagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2. Primeiro você "infla" o layout XML
        setContentView(R.layout.activity_main);

        // 3. AGORA você vincula os IDs que estão no XML às variáveis Java
        cardCadastrar = findViewById(R.id.cardCadastrar);
        cardDosagem = findViewById(R.id.cardDosagem);

        // 4. Configura os cliques normalmente
        cardCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, ProdutoActivity.class);
                startActivity(intent1);
            }
        });

        cardDosagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, CalculdoraActivity.class);
                startActivity(intent2);
            }
        });
    }
}