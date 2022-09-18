package com.gabrielprofirio_N568AC0.app_comp_soa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.gabrielprofirio_N568AC0.app_comp_soa.db.Dados;
import com.gabrielprofirio_N568AC0.app_comp_soa.model.adapter.AbastecimentoAdapter;
import com.gabrielprofirio_N568AC0.app_comp_soa.model.entidade.Abastecimento;
import com.gabrielprofirio_N568AC0.app_comp_soa.model.reps.Rep_abastecimento;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class telaHistorico extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Abastecimento> abastecimentos = new ArrayList<>();
    private SQLiteDatabase db_conexao;
    private Dados dados;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_historico);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_preto)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.new_preto));
        getWindow().setTitle(getResources().getString(R.string.lblTelaHist));

        // CHAMADA DE FUNÇÕES
        conexaoDB();
        carregaTodosAbastecimentos();

        // INSTANCIAS
        recyclerView = findViewById(R.id.recyclerView);

        // Layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // Adapter
        AbastecimentoAdapter abastecimentoAdapter = new AbastecimentoAdapter(abastecimentos);
        recyclerView.setAdapter(abastecimentoAdapter);
    }

    private void carregaTodosAbastecimentos() {
        Rep_abastecimento rep_abs = new Rep_abastecimento(db_conexao);
        this.abastecimentos = rep_abs.readTodosAbastecimento();

    }

    private void conexaoDB() {
        try {
            dados = new Dados(this);

            db_conexao = dados.getWritableDatabase();

            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setMessage("Conexão Ok!");
            msg.show();
        } catch (SQLException ex) {
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setMessage("Erro na conexão: " + ex);
            msg.show();
            ex.printStackTrace();
        }
    }
}