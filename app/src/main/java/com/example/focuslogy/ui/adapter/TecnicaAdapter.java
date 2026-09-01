package com.example.focuslogy.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focuslogy.databinding.ItemTecnicaBinding;

import java.util.List;

public class TecnicaAdapter extends RecyclerView.Adapter<TecnicaAdapter.TecnicaViewHolder> {

    public static class TecnicaInfo {
        public final String nome;
        public final String descricao;

        public TecnicaInfo(String nome, String descricao) {
            this.nome = nome;
            this.descricao = descricao;
        }
    }

    private final List<TecnicaInfo> lista;

    public TecnicaAdapter(List<TecnicaInfo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public TecnicaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTecnicaBinding binding = ItemTecnicaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TecnicaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TecnicaViewHolder holder, int position) {
        TecnicaInfo item = lista.get(position);
        holder.binding.tvNomeTecnica.setText(item.nome);
        holder.binding.tvDescricaoTecnica.setText(item.descricao);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class TecnicaViewHolder extends RecyclerView.ViewHolder {
        final ItemTecnicaBinding binding;

        public TecnicaViewHolder(@NonNull ItemTecnicaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
