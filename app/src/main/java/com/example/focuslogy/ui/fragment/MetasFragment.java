package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.focuslogy.MainActivity;
import com.example.focuslogy.databinding.FragmentMetasBinding;
import com.example.focuslogy.ui.adapter.MetaAdapter;
import com.example.focuslogy.viewmodel.MateriaViewModel;

public class MetasFragment extends Fragment {

    private FragmentMetasBinding binding;
    private MateriaViewModel materiaViewModel;
    private MetaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMetasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        materiaViewModel = new ViewModelProvider(activity, activity.getFactory()).get(MateriaViewModel.class);

        binding.btnVoltar.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        adapter = new MetaAdapter();
        binding.rvMetas.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMetas.setAdapter(adapter);

        materiaViewModel.getMaterias().observe(getViewLifecycleOwner(), materias -> {
            adapter.setLista(materias);
        });
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
