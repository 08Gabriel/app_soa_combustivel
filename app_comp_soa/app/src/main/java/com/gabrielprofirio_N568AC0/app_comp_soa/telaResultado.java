package com.gabrielprofirio_N568AC0.app_comp_soa;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class telaResultado extends AppCompatActivity {

    ImageView img;
    TextView tvResultado;
    Button btnVoltar;
    double valorEtanol, valorGasolina, valorLitroCombustivelRecomendado;
    String resultado;
    Drawable imgEtanol, imgGasolina;
    CheckBox cbGravar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_preto)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.new_preto));
        getWindow().setTitle(getResources().getString(R.string.lblTelaRes));

        img = findViewById(R.id.imagemResultado);
        tvResultado = findViewById(R.id.textViewLblResultado);
        btnVoltar = findViewById(R.id.btnSalvar);
        imgEtanol = getResources().getDrawable(R.drawable.etanol);
        imgGasolina = getResources().getDrawable(R.drawable.gasolina);
        cbGravar = findViewById(R.id.cbSalvarDados);


        valorEtanol = Double.parseDouble(getIntent().getStringExtra("valorEtanol"));
        valorGasolina = Double.parseDouble(getIntent().getStringExtra("valorGasolina"));


        resultado = fu_calculaResultado(valorEtanol, valorGasolina);

        // EXIBINDO RESULTADOS
        if (resultado.equals("ETANOL")) {
            img.setImageDrawable(imgEtanol);
            tvResultado.setText(resultado.toUpperCase());
            tvResultado.setTextColor(getResources().getColor(R.color.verde));
            valorLitroCombustivelRecomendado = valorEtanol;
        } else if (resultado.equals("GASOLINA")) {
            img.setImageDrawable(imgGasolina);
            tvResultado.setText(resultado);
            tvResultado.setTextColor(getResources().getColor(R.color.laranja));
            valorLitroCombustivelRecomendado = valorGasolina;
        }

        // BOTAO
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbGravar.isChecked()) {
                    Intent telaSalvarDados = new Intent(telaResultado.this,
                            com.gabrielprofirio_N568AC0.app_comp_soa.telaSalvarDados.class);

                    // PASSANDO DADOS PARA PROXIMA INTENT
                    telaSalvarDados.putExtra("combRecomendado", resultado.toString());
                    telaSalvarDados.putExtra("valorLitroCombRecomendado", Double.toString(valorLitroCombustivelRecomendado));
                    startActivity(telaSalvarDados);
                } else if (cbGravar.isChecked() == false) {
                    finish();
                }
            }
        });

    }

    // APLICADO FORMULA PARA CALCULAR QUAL COMBUSTIVEL COMPENSA
    private String fu_calculaResultado(double valorEtanol, double valorGasolina) {
        // PRECO LITRO ETANOL / PRECO GASOLINA < 0,85 = GASOLINA
        String resultado = null;

        if ( valorEtanol / valorGasolina < 0.85 ) {
            resultado = "ETANOL";
        } else {
            resultado = "GASOLINA";
        }

        return resultado.toUpperCase();
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