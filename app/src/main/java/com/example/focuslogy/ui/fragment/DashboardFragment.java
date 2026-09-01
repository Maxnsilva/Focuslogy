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
import com.example.focuslogy.R;
import com.example.focuslogy.data.entity.UsuarioEntity;
import com.example.focuslogy.databinding.FragmentDashboardBinding;
import com.example.focuslogy.viewmodel.AuthViewModel;
import com.example.focuslogy.viewmodel.MateriaViewModel;
import com.example.focuslogy.viewmodel.SessaoViewModel;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private AuthViewModel authViewModel;
    private MateriaViewModel materiaViewModel;
    private SessaoViewModel sessaoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        authViewModel = new ViewModelProvider(activity, activity.getFactory()).get(AuthViewModel.class);
        materiaViewModel = new ViewModelProvider(activity, activity.getFactory()).get(MateriaViewModel.class);
        sessaoViewModel = new ViewModelProvider(activity, activity.getFactory()).get(SessaoViewModel.class);

        authViewModel.getUsuarioLogado().observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                binding.tvSaudacao.setText("Olá, " + usuario.getNome() + "!");
                materiaViewModel.setUsuarioId(usuario.getId());
                sessaoViewModel.setUsuarioId(usuario.getId());
            }
        });

        sessaoViewModel.getTempoHoje().observe(getViewLifecycleOwner(), tempo -> {
            binding.tvTempoHoje.setText(tempo + "m");
            UsuarioEntity user = authViewModel.getUsuarioLogado().getValue();
            int meta = user != null ? user.getMetaDiariaMinutos() : 60;
            binding.tvStatusMeta.setText(tempo + " de " + meta + " minutos concluídos");
            int percent = meta > 0 ? (int) ((tempo * 100.0) / meta) : 0;
            binding.progressMeta.setProgress(Math.min(percent, 100));
        });

        sessaoViewModel.getSessoesHoje().observe(getViewLifecycleOwner(), sessoes -> {
            binding.tvSessoesHoje.setText(String.valueOf(sessoes));
        });

        sessaoViewModel.getHistorico().observe(getViewLifecycleOwner(), historico -> {
            if (historico != null && !historico.isEmpty()) {
                com.example.focuslogy.data.entity.SessaoEntity ultima = historico.get(0);
                binding.tvUltimoAssunto.setText(ultima.getAssunto());
                binding.tvUltimaDuracaoTecnica.setText(ultima.getDuracaoMinutos() + " min • " + ultima.getTecnica());
                binding.tvUltimaDuracaoTecnica.setVisibility(View.VISIBLE);
            } else {
                binding.tvUltimoAssunto.setText("Nenhuma sessão recente registrada.");
                binding.tvUltimaDuracaoTecnica.setVisibility(View.GONE);
            }
        });

        binding.btnPerfil.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.perfilFragment));
        binding.btnComecarEstudo.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.novaSessaoFragment));
        binding.btnTecnicas.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.tecnicasFragment));
        binding.btnRelatorios.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.relatoriosFragment));
        binding.btnMetas.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.metasFragment));

        // Lógica para expandir/recolher o Card de Guia e Propósito
        binding.cardGuia.setOnClickListener(v -> {
            if (binding.conteudoGuia.getVisibility() == View.GONE) {
                binding.conteudoGuia.setVisibility(View.VISIBLE);
                binding.ivArrowGuia.setRotation(180f);
            } else {
                binding.conteudoGuia.setVisibility(View.GONE);
                binding.ivArrowGuia.setRotation(0f);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sessaoViewModel.carregarDados();
        materiaViewModel.carregarMaterias();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
