package com.gabrielprofirio_N568AC0.app_comp_soa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dados extends SQLiteOpenHelper {

    public Dados(@Nullable Context context) {
        super(context, "DADOS", null, 1 );
    }

    // CRIA TABELAS
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CriaTabelasDDL.criaTabela());
    }

    // ATUALIZA TABELAS
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
