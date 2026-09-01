package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.focuslogy.MainActivity;
import com.example.focuslogy.R;
import com.example.focuslogy.data.entity.MateriaEntity;
import com.example.focuslogy.databinding.FragmentMateriasBinding;
import com.example.focuslogy.ui.adapter.MateriaAdapter;
import com.example.focuslogy.viewmodel.MateriaViewModel;

public class MateriasFragment extends Fragment {

    private FragmentMateriasBinding binding;
    private MateriaViewModel materiaViewModel;
    private MateriaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMateriasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        materiaViewModel = new ViewModelProvider(activity, activity.getFactory()).get(MateriaViewModel.class);

        adapter = new MateriaAdapter(new MateriaAdapter.OnMateriaClickListener() {
            @Override
            public void onEdit(MateriaEntity materia) {
                Bundle args = new Bundle();
                args.putInt("materiaId", materia.getId());
                Navigation.findNavController(view).navigate(R.id.formularioMateriaFragment, args);
            }

            @Override
            public void onDelete(MateriaEntity materia) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Excluir Matéria")
                        .setMessage("Tem certeza que deseja excluir '" + materia.getNome() + "'?")
                        .setPositiveButton("Excluir", (dialog, which) -> {
                            materiaViewModel.excluir(materia.getId(), null);
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        binding.rvMaterias.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMaterias.setAdapter(adapter);

        materiaViewModel.getMaterias().observe(getViewLifecycleOwner(), materias -> {
            binding.progressBar.setVisibility(View.GONE);
            if (materias == null || materias.isEmpty()) {
                binding.tvVazio.setVisibility(View.VISIBLE);
                adapter.setLista(null);
            } else {
                binding.tvVazio.setVisibility(View.GONE);
                adapter.setLista(materias);
            }
        });

        binding.fabAddMateria.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.formularioMateriaFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        materiaViewModel.carregarMaterias();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
