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

import com.example.focuslogy.MainActivity;
import com.example.focuslogy.data.entity.SessaoEntity;
import com.example.focuslogy.databinding.FragmentRelatoriosBinding;
import com.example.focuslogy.viewmodel.SessaoViewModel;

import java.util.List;

public class RelatoriosFragment extends Fragment {

    private FragmentRelatoriosBinding binding;
    private SessaoViewModel sessaoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRelatoriosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        sessaoViewModel = new ViewModelProvider(activity, activity.getFactory()).get(SessaoViewModel.class);

        binding.btnVoltar.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        sessaoViewModel.getTempoHoje().observe(getViewLifecycleOwner(), tempo -> {
            binding.tvMinutosHoje.setText("Minutos: " + tempo);
        });

        sessaoViewModel.getSessoesHoje().observe(getViewLifecycleOwner(), sessoes -> {
            binding.tvSessoesHoje.setText("Sessões: " + sessoes);
        });

        sessaoViewModel.getHistorico().observe(getViewLifecycleOwner(), historico -> {
            if (historico != null) {
                binding.tvTotalSessoesGeral.setText("Sessões Realizadas: " + historico.size());
                int totalMinutos = 0;
                for (SessaoEntity s : historico) {
                    totalMinutos += s.getDuracaoMinutos();
                }
                binding.tvTempoTotalGeral.setText("Tempo Total: " + totalMinutos + " minutos");
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
