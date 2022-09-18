package com.gabrielprofirio_N568AC0.app_comp_soa.db;

public class CriaTabelasDDL {
    // CRUD
    public static String criaTabela() {
        StringBuilder codSql = new StringBuilder();

        codSql.append("CREATE TABLE IF NOT EXISTS TB_ABASTECIMENTO ( ");
        codSql.append("     ID_ABS INTEGER PRIMARY KEY AUTOINCREMENT, ");
        codSql.append("     APELIDO_POSTO VARCHAR(200) NOT NULL DEFAULT (''), ");
        codSql.append("     DATA_ABS TEXT NOT NULL DEFAULT (''), ");
        codSql.append("     TIPO_COMB VARCHAR(10) NOT NULL DEFAULT (''), ");
        codSql.append("     VALOR_LITRO TEXT NOT NULL DEFAULT (''), ");
        codSql.append("     TOTAL_LITROS TEXT NOT NULL DEFAULT (''), ");
        codSql.append("     VALOR_TOTAL TEXT NOT NULL DEFAULT (''))");



        return codSql.toString();
    }

}
