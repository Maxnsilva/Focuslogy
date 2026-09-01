package com.example.focuslogy.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focuslogy.data.entity.MateriaEntity;
import com.example.focuslogy.databinding.ItemMetaBinding;

import java.util.ArrayList;
import java.util.List;

public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.MetaViewHolder> {

    private List<MateriaEntity> lista = new ArrayList<>();

    public void setLista(List<MateriaEntity> lista) {
        this.lista = lista != null ? lista : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMetaBinding binding = ItemMetaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MetaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        MateriaEntity materia = lista.get(position);
        holder.binding.tvNomeMateria.setText(materia.getNome());
        holder.binding.tvMetaSemanal.setText("Meta: " + materia.getMetaSemanalMinutos() + " minutos/semana");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class MetaViewHolder extends RecyclerView.ViewHolder {
        final ItemMetaBinding binding;

        public MetaViewHolder(@NonNull ItemMetaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
