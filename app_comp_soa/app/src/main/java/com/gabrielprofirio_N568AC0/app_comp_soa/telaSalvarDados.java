package com.gabrielprofirio_N568AC0.app_comp_soa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.gabrielprofirio_N568AC0.app_comp_soa.db.Dados;
import com.gabrielprofirio_N568AC0.app_comp_soa.model.entidade.Abastecimento;
import com.gabrielprofirio_N568AC0.app_comp_soa.model.reps.Rep_abastecimento;
import com.gabrielprofirio_N568AC0.app_comp_soa.util.Validacao;
import com.google.android.material.snackbar.Snackbar;
import com.santalu.maskara.Mask;
import com.santalu.maskara.MaskChangedListener;
import com.santalu.maskara.MaskStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class telaSalvarDados extends AppCompatActivity  {
    // VARIAVEIS
    Button btnSalvar;
    RadioGroup rgroup;
    RadioButton rbEtanol, rbGasolina, rbSelecionado;
    EditText etPosto, etData, etValorLitro, etTotLitros, etValorTotal;
    Abastecimento a;
    Rep_abastecimento rep_abs;

    ScrollView svSalvarDados;

    private SQLiteDatabase db_conexao;
    private Dados dados;






    // INICIO ON CREATE
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_salvar_dados);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_preto)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.new_preto));
        getWindow().setTitle(getResources().getString(R.string.lblTelaSalvar));
        // INSTANCIAS
        btnSalvar = findViewById(R.id.btnSalvar);
        rgroup = findViewById(R.id.radioGroup);
        rbEtanol = findViewById(R.id.rbEtanol);
        rbGasolina = findViewById(R.id.rbGasolina);
        etPosto = findViewById(R.id.etApelidoPosto);
        etData = findViewById(R.id.etData);
        etValorLitro = findViewById(R.id.etValorLitro);
        etTotLitros = findViewById(R.id.etTotLitros);
        etValorTotal = findViewById(R.id.etValorTotal);
        svSalvarDados = findViewById(R.id.svSalvarDados);

        // ALERTAS
        AlertDialog.Builder msg = new AlertDialog.Builder(this);


        // MASCARA EDITTEXT
        Mask mascaraEtData = new Mask("__/__/____", '_', MaskStyle.PERSISTENT);
        MaskChangedListener listener = new MaskChangedListener(mascaraEtData);
        etData.addTextChangedListener(listener);


        // CHAMADA DE FUNCÕES
        fu_carregaCombRecomendado(etValorLitro, rbEtanol, rbGasolina);
        fu_insereData(etData);
        conexaoDB();


        // EVENTO DE TEXTO ALTERADO - TOTAL LITROS
        etTotLitros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fu_calculaValoresCombustivel(etValorLitro, etTotLitros, etValorTotal);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // RADIO BUTTON
        int rgSelecionado = rgroup.getCheckedRadioButtonId();

        if ( rgSelecionado != -1) {
            rbSelecionado = findViewById(rgSelecionado);
        }

        // BOTAO SALVAR
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SALVAR BANCO DE DADOS
                try {
                     //SALVO COM SUCESSO
                    if (fu_salvaBancoDados(etPosto, etData, rbSelecionado, etValorLitro, etTotLitros, etValorTotal)) {
//                        msg.setTitle("Sucesso");
//                        msg.setMessage("Dados salvos com sucesso!");
//                        msg.show();
                        Intent intent = new Intent(telaSalvarDados.this, telaComparacao.class);
                        startActivity(intent);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    // FIM ON CREATE


    // VALIDAÇÃO DOS CAMPOS
    private boolean fu_validaCampos(EditText etPosto, EditText etData, EditText etValorLitro, EditText etTotLitros, EditText etValorTotal) throws ParseException {
        boolean camposValidados = false;

        if (Validacao.validaEditText(etPosto) && Validacao.validaEditText(etData) && Validacao.validaEditText(etValorLitro) && Validacao.validaEditText(etTotLitros) && Validacao.validaEditText(etValorTotal) )  {
            camposValidados = true;
        }
        return camposValidados;
    }

    // INSERÇÃO DA DATA
    private void fu_insereData(EditText etData) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String atualData = sdf.format(data);

        etData.setText(atualData);
    }

    // CARREGA DADOS DO COMBUSTÍVEL RECMENDADO
    private void fu_carregaCombRecomendado(EditText etValorLitro, RadioButton rbEtanol, RadioButton rbGasolina) {
        // Esta função busca o combustivel recomendado e o preço do litro, atualiza campos
        String comb = getIntent().getStringExtra("combRecomendado").toUpperCase();
        String valorLitro = getIntent().getStringExtra("valorLitroCombRecomendado");

        valorLitro = valorLitro.replace('.', ',');
        if (comb.equals("ETANOL")) {
            rbEtanol.setChecked(true);
        } else {
            rbGasolina.setChecked(true);
        }
        etValorLitro.setText(valorLitro);
    }

    private void fu_calculaValoresCombustivel(EditText valorLitro, EditText totLitros, EditText valorTotal) {
        double res = 0, double_valorLitro = 0, double_totLitros = 0, double_valorTotal = 0;
        String resultadoFormatado = null;

        // VALOR LITRO * TOTAL DE LITROS -> PREENCHE VALOR TOTAL
        if (Validacao.validaEditText(valorLitro) && Validacao.validaEditText(totLitros)) {
            res = 0;
            String vlitro = valorLitro.getText().toString().replace(",",".");
            String vTotLitros = totLitros.getText().toString().replace(",",".");

            double_valorLitro = Double.parseDouble(vlitro);
            double_totLitros = Double.parseDouble(vTotLitros);

            res = double_valorLitro * double_totLitros;
            resultadoFormatado = String.format("%,2f", res);

            valorTotal.setText(resultadoFormatado);
        }
    }

    // SALVAR DADOS NO BANCO DE DADOS
    private boolean fu_salvaBancoDados(EditText etPosto, EditText etData, RadioButton rb, EditText etValorLitro, EditText etTotLitros, EditText etValorTotal) throws ParseException {
        boolean campos_validados = false, dados_salvos = false;



        AlertDialog.Builder msg = new AlertDialog.Builder(this);


        campos_validados = fu_validaCampos(etPosto, etData, etValorLitro, etTotLitros, etValorTotal);

        // VALIDAÇÃO DE DADOS
        if (campos_validados) {
           try {
               a = new Abastecimento();
               rep_abs = new Rep_abastecimento(db_conexao);

               a.APELIDO_POSTO = etPosto.getText().toString();
               a.DATA_ABS = etData.getText().toString();
               a.TIPO_COMB = rb.getText().toString();
               a.VALOR_LITRO = Double.parseDouble(etValorLitro.getText().toString().replace(',', '.'));
               a.TOTAL_LITROS = Double.parseDouble(etTotLitros.getText().toString().replace(',', '.'));
               a.VALOR_TOTAL = Double.parseDouble(etValorTotal.getText().toString().replace(',', '.'));

               rep_abs.createAbastecimento(a);

               dados_salvos = true;

           } catch (SQLException ex) {
               msg.setTitle("Erro");
               msg.setMessage("Erro na inserção:" + ex);
               msg.show();
               ex.printStackTrace();
           }

        } else if (!campos_validados){
            msg.setTitle("Atenção");
            msg.setMessage("Preencha todos os campos!");
            msg.show();
        }

        return dados_salvos;
    }

    private void conexaoDB() {
        try {
            dados = new Dados(this);

            db_conexao = dados.getWritableDatabase();

            Snackbar.make(svSalvarDados, "Conexão OK!", Snackbar.LENGTH_SHORT).setAction("OK", null).show();
        } catch (SQLException ex) {
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setMessage("Erro na conexão: " + ex);
            msg.show();
            ex.printStackTrace();
        }
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        // retirar esse msg
        AlertDialog.Builder msg = new AlertDialog.Builder(this);


        switch (item.getItemId()) {
            case R.id.itemMeuHist:
                intent = new Intent(this, telaHistorico.class);
                startActivity(intent);
                break;

            case R.id.itemBluetooh:
                // Bluetooh
                msg.setMessage("Compartilhar dados por Bluetooh");
                msg.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}