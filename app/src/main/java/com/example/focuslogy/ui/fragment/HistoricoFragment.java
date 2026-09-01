package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.focuslogy.MainActivity;
import com.example.focuslogy.databinding.FragmentHistoricoBinding;
import com.example.focuslogy.ui.adapter.SessaoAdapter;
import com.example.focuslogy.viewmodel.SessaoViewModel;

public class HistoricoFragment extends Fragment {

    private FragmentHistoricoBinding binding;
    private SessaoViewModel sessaoViewModel;
    private SessaoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoricoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        sessaoViewModel = new ViewModelProvider(activity, activity.getFactory()).get(SessaoViewModel.class);

        adapter = new SessaoAdapter();
        binding.rvHistorico.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvHistorico.setAdapter(adapter);

        sessaoViewModel.getHistorico().observe(getViewLifecycleOwner(), historico -> {
            binding.progressBar.setVisibility(View.GONE);
            if (historico == null || historico.isEmpty()) {
                binding.tvVazio.setVisibility(View.VISIBLE);
                adapter.setLista(null);
            } else {
                binding.tvVazio.setVisibility(View.GONE);
                adapter.setLista(historico);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sessaoViewModel.carregarDados();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
