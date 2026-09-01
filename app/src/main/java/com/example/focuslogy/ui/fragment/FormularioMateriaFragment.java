package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.focuslogy.MainActivity;
import com.example.focuslogy.data.entity.MateriaEntity;
import com.example.focuslogy.data.entity.UsuarioEntity;
import com.example.focuslogy.databinding.FragmentFormularioMateriaBinding;
import com.example.focuslogy.viewmodel.AuthViewModel;
import com.example.focuslogy.viewmodel.MateriaViewModel;

public class FormularioMateriaFragment extends Fragment {

    private FragmentFormularioMateriaBinding binding;
    private MateriaViewModel materiaViewModel;
    private AuthViewModel authViewModel;
    private int materiaId = 0;
    private String corSelecionada = "#6750A4";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFormularioMateriaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        materiaViewModel = new ViewModelProvider(activity, activity.getFactory()).get(MateriaViewModel.class);
        authViewModel = new ViewModelProvider(activity, activity.getFactory()).get(AuthViewModel.class);

        if (getArguments() != null) {
            materiaId = getArguments().getInt("materiaId", 0);
        }

        if (materiaId > 0) {
            materiaViewModel.buscarPorId(materiaId, materia -> {
                if (materia != null) {
                    binding.etNome.setText(materia.getNome());
                    binding.etDescricao.setText(materia.getDescricao());
                    binding.etMetaSemanal.setText(String.valueOf(materia.getMetaSemanalMinutos()));
                    corSelecionada = materia.getCor();
                }
            });
        }

        binding.btnCorRoxo.setOnClickListener(v -> corSelecionada = "#6750A4");
        binding.btnCorVerde.setOnClickListener(v -> corSelecionada = "#006400");
        binding.btnCorVermelho.setOnClickListener(v -> corSelecionada = "#FF0000");
        binding.btnCorAzul.setOnClickListener(v -> corSelecionada = "#0000FF");
        binding.btnCorLaranja.setOnClickListener(v -> corSelecionada = "#FFA500");

        binding.btnVoltar.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        binding.btnSalvar.setOnClickListener(v -> {
            String nome = binding.etNome.getText() != null ? binding.etNome.getText().toString().trim() : "";
            String descricao = binding.etDescricao.getText() != null ? binding.etDescricao.getText().toString().trim() : "";
            String metaStr = binding.etMetaSemanal.getText() != null ? binding.etMetaSemanal.getText().toString().trim() : "120";

            if (TextUtils.isEmpty(nome)) {
                Toast.makeText(requireContext(), "Informe o nome da matéria", Toast.LENGTH_SHORT).show();
                return;
            }

            UsuarioEntity usuario = authViewModel.getUsuarioLogado().getValue();
            int usuarioId = usuario != null ? usuario.getId() : 0;
            int metaSemanal = TextUtils.isEmpty(metaStr) ? 120 : Integer.parseInt(metaStr);

            MateriaEntity materia = new MateriaEntity(materiaId, usuarioId, nome, descricao, corSelecionada, metaSemanal, System.currentTimeMillis());

            Runnable onDone = () -> Navigation.findNavController(view).navigateUp();

            if (materiaId > 0) {
                materiaViewModel.atualizar(materia, onDone);
            } else {
                materiaViewModel.cadastrar(materia, onDone);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
