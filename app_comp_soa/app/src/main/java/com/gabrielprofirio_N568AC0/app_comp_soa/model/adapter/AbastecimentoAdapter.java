package com.gabrielprofirio_N568AC0.app_comp_soa.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielprofirio_N568AC0.app_comp_soa.R;
import com.gabrielprofirio_N568AC0.app_comp_soa.model.entidade.Abastecimento;

import java.util.ArrayList;
import java.util.List;

public class AbastecimentoAdapter extends RecyclerView.Adapter<AbastecimentoAdapter.MyViewHolder> {

    private List<Abastecimento> abastecimentos;

    public AbastecimentoAdapter(List<Abastecimento> lista_abs) {
        this.abastecimentos = lista_abs;
    }

    // VISUALIZAÇÃO
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_abastecimento, parent, false);

        return new MyViewHolder(item);
    }


    // EXIBICAO DOS ITENS
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Abastecimento abastecimento = abastecimentos.get(position);

        holder.tvPosto.setText(abastecimento.APELIDO_POSTO);
        holder.tvData.setText(abastecimento.DATA_ABS);
        holder.tvComb.setText(abastecimento.TIPO_COMB);
        holder.tvValorLitro.setText(String.valueOf(abastecimento.VALOR_LITRO));
        holder.tvTotLitros.setText(String.valueOf(abastecimento.TOTAL_LITROS));
        holder.tvValorTotal.setText(String.valueOf(abastecimento.VALOR_TOTAL));

    }

    // QUANTIDADE DE CARDS: DINAMICAMENTE COM O TAMANHO DO ARRAY QUE É RETORNADO DO BD
    @Override
    public int getItemCount() {
        return abastecimentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Itens dos Cards

        private ImageView ivImagem;
        private TextView tvPosto;
        private TextView tvData;
        private TextView tvComb;
        private TextView tvTotLitros;
        private TextView tvValorLitro;
        private TextView tvValorTotal;


        // ITENS DO CARD
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImagem = itemView.findViewById(R.id.ivImagem);
            tvPosto = itemView.findViewById(R.id.tvPosto);
            tvData = itemView.findViewById(R.id.tvData);
            tvComb = itemView.findViewById(R.id.tvComb);
            tvTotLitros = itemView.findViewById(R.id.tvTotLitros);
            tvValorLitro = itemView.findViewById(R.id.tvValorLitro);
            tvValorTotal = itemView.findViewById(R.id.tvValorTotal);
        }
    }
}
