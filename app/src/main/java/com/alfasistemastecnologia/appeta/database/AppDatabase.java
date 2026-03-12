package com.alfasistemastecnologia.appeta.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alfasistemastecnologia.appeta.model.Calculadora;
import com.alfasistemastecnologia.appeta.model.Produto;

@Database(
        entities = {
                Produto.class,
                Calculadora.class
        },
        version = 2,
        exportSchema = true
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProdutoDao produtoDao();

    private static volatile AppDatabase INSTANCIA;

    public static AppDatabase getInstancia(Context context) {
        if (INSTANCIA == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "bd_appeta.db"
                            )
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCIA;
    }

    /**
     * Migration da versão 1 para 2
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            // 🔹 Recria tb_produto com idProduto
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS tb_produto_novo (" +
                            "idProduto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "nome TEXT, " +
                            "concentracao REAL NOT NULL)"
            );

            database.execSQL(
                    "INSERT INTO tb_produto_novo (idProduto, nome, concentracao) " +
                            "SELECT id, nome, concentracao FROM tb_produto"
            );

            database.execSQL("DROP TABLE tb_produto");

            database.execSQL(
                    "ALTER TABLE tb_produto_novo RENAME TO tb_produto"
            );

            // 🔹 CRIA tb_calculadora (FALTAVA ISSO)
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS tb_calculadora (" +
                            "idCalculadora INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "idProduto INTEGER NOT NULL, " +
                            "valor REAL NOT NULL, " +
                            "FOREIGN KEY(idProduto) REFERENCES tb_produto(idProduto) ON DELETE CASCADE)"
            );

            database.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_tb_calculadora_idProduto " +
                            "ON tb_calculadora(idProduto)"
            );
        }
    };
}