package com.example.focuslogy.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focuslogy.data.entity.MateriaEntity;
import com.example.focuslogy.databinding.ItemMateriaBinding;

import java.util.ArrayList;
import java.util.List;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MateriaViewHolder> {

    public interface OnMateriaClickListener {
        void onEdit(MateriaEntity materia);
        void onDelete(MateriaEntity materia);
    }

    private List<MateriaEntity> lista = new ArrayList<>();
    private final OnMateriaClickListener listener;

    public MateriaAdapter(OnMateriaClickListener listener) {
        this.listener = listener;
    }

    public void setLista(List<MateriaEntity> lista) {
        this.lista = lista != null ? lista : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMateriaBinding binding = ItemMateriaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MateriaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaViewHolder holder, int position) {
        MateriaEntity materia = lista.get(position);
        holder.binding.tvNome.setText(materia.getNome());
        holder.binding.tvDescricao.setText(materia.getDescricao());

        try {
            holder.binding.viewCor.setBackgroundColor(Color.parseColor(materia.getCor()));
        } catch (Exception e) {
            holder.binding.viewCor.setBackgroundColor(Color.GRAY);
        }

        holder.binding.btnEditar.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(materia);
        });

        holder.binding.btnExcluir.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(materia);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class MateriaViewHolder extends RecyclerView.ViewHolder {
        final ItemMateriaBinding binding;

        public MateriaViewHolder(@NonNull ItemMateriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
