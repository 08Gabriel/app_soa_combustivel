package com.gabrielprofirio_N568AC0.app_comp_soa.util;

import android.widget.EditText;

public class Validacao {

    public static boolean validaEditText(EditText editText){
        boolean validado = true;

        String valor = editText.getText().toString();
        int qtdVirgulas = 0;

        // TAMANHO = 0
        // TEXTO = . ,
        if (valor.length() == 0) {
            return false;
        }

        for (int i=0; i < valor.length(); i++) {
            if (valor.charAt(0) == ','){
                validado = false;
            }

            if (qtdVirgulas > 1) {
                validado = false;
            }

            if (valor.charAt(i) == ',') {
                qtdVirgulas++;
            }


        }

        return validado;
    }


}
