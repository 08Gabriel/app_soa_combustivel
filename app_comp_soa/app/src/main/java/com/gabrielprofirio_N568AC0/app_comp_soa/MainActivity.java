package com.gabrielprofirio_N568AC0.app_comp_soa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button comecar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.new_preto)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.new_preto));


        // procura pelo id do botao
        comecar = findViewById(R.id.btnCom);
        comecar.setOnClickListener(new View.OnClickListener() { // click
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, telaComparacao.class);
                startActivity(intent);
            }
        });

    }

}