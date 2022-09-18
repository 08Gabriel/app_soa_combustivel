package com.gabrielprofirio_N568AC0.app_comp_soa.model.reps;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.gabrielprofirio_N568AC0.app_comp_soa.model.entidade.Abastecimento;

import java.util.ArrayList;
import java.util.List;



public class Rep_abastecimento {

    private SQLiteDatabase conexao;

    public Rep_abastecimento(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }


    // CRUD

    public void createAbastecimento(Abastecimento abs) {
        ContentValues cv = new ContentValues();

        cv.put("APELIDO_POSTO", abs.APELIDO_POSTO);
        cv.put("DATA_ABS", abs.DATA_ABS);
        cv.put("TIPO_COMB", abs.TIPO_COMB);
        cv.put("VALOR_LITRO", abs.VALOR_LITRO);
        cv.put("TOTAL_LITROS", abs.TOTAL_LITROS);
        cv.put("VALOR_TOTAL", abs.VALOR_TOTAL);

        // INSERT COM ALERTA CASO ALGO DE ERRADO
        conexao.insertOrThrow("TB_ABASTECIMENTO", null, cv);
    }

    public void deleteAbastecimento(int id ) {
        String[] idAbs = new String[1];
        idAbs[0] =  String.valueOf(id);

        conexao.delete("TB_ABASTECIMENTO", "ID_ABS = ?", idAbs);
    }

    public List<Abastecimento> readTodosAbastecimento(){
        List<Abastecimento> abs = new ArrayList<Abastecimento>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ID_ABS, APELIDO_POSTO, DATA_ABS, TIPO_COMB, VALOR_LITRO, TOTAL_LITROS, VALOR_TOTAL ");
        sql.append("FROM TB_ABASTECIMENTO");


        Cursor res = conexao.rawQuery(sql.toString(), null);

        if (res.getCount() > 0) {
            // PRIMEIRO REGISTRO
            res.moveToFirst();

            do {
                Abastecimento abastecimento = new Abastecimento();

                abastecimento.ID_ABS = res.getInt(res.getColumnIndexOrThrow("ID_ABS"));
                abastecimento.APELIDO_POSTO = res.getString(res.getColumnIndexOrThrow("APELIDO_POSTO"));
                abastecimento.DATA_ABS = res.getString(res.getColumnIndexOrThrow("DATA_ABS"));
                abastecimento.TIPO_COMB = res.getString(res.getColumnIndexOrThrow("TIPO_COMB"));
                abastecimento.VALOR_LITRO = res.getDouble(res.getColumnIndexOrThrow("VALOR_LITRO"));
                abastecimento.TOTAL_LITROS = res.getDouble(res.getColumnIndexOrThrow("TOTAL_LITROS"));
                abastecimento.VALOR_TOTAL = res.getDouble(res.getColumnIndexOrThrow("VALOR_TOTAL"));

                abs.add(abastecimento);

            } while (res.moveToNext());

        }

        return abs;
    }

    public Abastecimento readAbatecimento(int id) {
        Abastecimento abs = new Abastecimento();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ID_ABS, APELIDO_POSTO, DATA_ABS, TIPO_COMB, VALOR_LITRO, TOTAL_LITROS, VALOR_TOTAL ");
        sql.append("FROM TB_ABASTECIMENTO");
        sql.append("WHERE ID_ABS = ?");

        String[] idAbs = new String[1];
        idAbs[0] =  String.valueOf(id);


        Cursor res = conexao.rawQuery(sql.toString(), idAbs);


            if (res.getCount() > 0) {
                // PRIMEIRO REGISTRO
                res.moveToFirst();

                abs.ID_ABS = res.getInt(res.getColumnIndexOrThrow("ID_ABS"));
                abs.APELIDO_POSTO = res.getString(res.getColumnIndexOrThrow("APELIDO_POSTO"));
                abs.DATA_ABS = res.getString(res.getColumnIndexOrThrow("DATA_ABS"));
                abs.TIPO_COMB = res.getString(res.getColumnIndexOrThrow("TIPO_COMB"));
                abs.VALOR_LITRO = res.getDouble(res.getColumnIndexOrThrow("VALOR_LITRO"));
                abs.TOTAL_LITROS = res.getDouble(res.getColumnIndexOrThrow("TOTAL_LITROS"));
                abs.VALOR_TOTAL = res.getDouble(res.getColumnIndexOrThrow("VALOR_TOTAL"));


            }

            if (abs != null) {
                return abs;
            }

            return null;
        }

    public void updateAbastecimento(Abastecimento abs) {
        ContentValues cv = new ContentValues();

        cv.put("APELIDO_POSTO", abs.APELIDO_POSTO);
        cv.put("DATA_ABS", abs.DATA_ABS);
        cv.put("TIPO_COMB", abs.TIPO_COMB);
        cv.put("VALOR_LITRO", abs.VALOR_LITRO);
        cv.put("TOTAL_LITROS", abs.TOTAL_LITROS);
        cv.put("VALOR_TOTAL", abs.VALOR_TOTAL);

        // INSERT COM ALERTA CASO ALGO DE ERRADO
        conexao.insertOrThrow("TB_ABASTECIMENTO", null, cv);

        String[] idAbs = new String[1];
        idAbs[0] =  String.valueOf(abs.ID_ABS);
        conexao.update("TB_ABASTECIMENTO", cv, "ID_ABS = ?", idAbs);
    }

}
