package com.example.focuslogy.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focuslogy.data.entity.SessaoEntity;
import com.example.focuslogy.databinding.ItemSessaoBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SessaoAdapter extends RecyclerView.Adapter<SessaoAdapter.SessaoViewHolder> {

    private List<SessaoEntity> lista = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public void setLista(List<SessaoEntity> lista) {
        this.lista = lista != null ? lista : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SessaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSessaoBinding binding = ItemSessaoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SessaoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SessaoViewHolder holder, int position) {
        SessaoEntity sessao = lista.get(position);
        holder.binding.tvAssunto.setText(sessao.getAssunto());
        holder.binding.tvDuracao.setText(sessao.getDuracaoMinutos() + " min");
        holder.binding.tvTecnica.setText(sessao.getTecnica());
        holder.binding.tvData.setText(dateFormat.format(new Date(sessao.getData())));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class SessaoViewHolder extends RecyclerView.ViewHolder {
        final ItemSessaoBinding binding;

        public SessaoViewHolder(@NonNull ItemSessaoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
