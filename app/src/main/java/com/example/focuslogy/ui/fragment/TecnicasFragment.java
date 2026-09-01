package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.focuslogy.databinding.FragmentTecnicasBinding;
import com.example.focuslogy.ui.adapter.TecnicaAdapter;

import java.util.ArrayList;
import java.util.List;

public class TecnicasFragment extends Fragment {

    private FragmentTecnicasBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTecnicasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnVoltar.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        List<TecnicaAdapter.TecnicaInfo> lista = new ArrayList<>();
        lista.add(new TecnicaAdapter.TecnicaInfo("Pomodoro", "25 minutos de foco total seguidos por 5 minutos de descanso."));
        lista.add(new TecnicaAdapter.TecnicaInfo("Método Feynman", "Explique o conteúdo de forma simples, como se estivesse ensinando alguém."));
        lista.add(new TecnicaAdapter.TecnicaInfo("Recordação Ativa", "Tente lembrar do conteúdo sem consultar o material primeiro."));
        lista.add(new TecnicaAdapter.TecnicaInfo("Revisão Espaçada", "Revise o conteúdo em intervalos crescentes de tempo."));
        lista.add(new TecnicaAdapter.TecnicaInfo("Mapas Mentais", "Organize os conceitos visualmente com conexões e palavras-chave."));

        TecnicaAdapter adapter = new TecnicaAdapter(lista);
        binding.rvTecnicas.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTecnicas.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
