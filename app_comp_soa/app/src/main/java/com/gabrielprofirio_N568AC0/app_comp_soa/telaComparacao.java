package com.gabrielprofirio_N568AC0.app_comp_soa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gabrielprofirio_N568AC0.app_comp_soa.util.Validacao;

public class telaComparacao extends AppCompatActivity {

    Button comparar;
    EditText etEtanol, etGasolina;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_comparacao);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_preto)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.new_preto));
        getWindow().setTitle(getResources().getString(R.string.lblTelaComp));


        comparar = findViewById(R.id.btnComparar);
        etEtanol = findViewById(R.id.etValorEtanol);
        etGasolina = findViewById(R.id.etValorGas);



        comparar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tlResultado = new Intent(telaComparacao.this, telaResultado.class);

                if (fu_ConferirEdit_Text()) {
                    String vlGasolina = etGasolina.getText().toString().replace(',', '.');
                    String vlEtanol = etEtanol.getText().toString().replace(',','.');

                    tlResultado.putExtra("valorGasolina", vlGasolina);
                    tlResultado.putExtra("valorEtanol", vlEtanol);
                    startActivity(tlResultado);
                }


            }
        });

    }

    private boolean fu_ConferirEdit_Text() {
        boolean validado = false;

        if (Validacao.validaEditText(etEtanol) && Validacao.validaEditText(etGasolina)) {
            validado = true;
        }

        return validado;
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