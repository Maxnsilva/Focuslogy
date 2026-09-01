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
import com.example.focuslogy.databinding.FragmentEsqueceuSenhaBinding;
import com.example.focuslogy.viewmodel.AuthViewModel;

public class EsqueceuSenhaFragment extends Fragment {

    private FragmentEsqueceuSenhaBinding binding;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEsqueceuSenhaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        authViewModel = new ViewModelProvider(activity, activity.getFactory()).get(AuthViewModel.class);

        binding.btnRedefinir.setOnClickListener(v -> {
            String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
            String novaSenha = binding.etNovaSenha.getText() != null ? binding.etNovaSenha.getText().toString().trim() : "";
            String confirmarSenha = binding.etConfirmarNovaSenha.getText() != null ? binding.etConfirmarNovaSenha.getText().toString().trim() : "";

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(novaSenha) || TextUtils.isEmpty(confirmarSenha)) {
                binding.tvErro.setText("Preencha todos os campos");
                binding.tvErro.setVisibility(View.VISIBLE);
                return;
            }

            if (!novaSenha.equals(confirmarSenha)) {
                binding.tvErro.setText("As senhas não coincidem");
                binding.tvErro.setVisibility(View.VISIBLE);
                return;
            }

            authViewModel.redefinirSenha(email, novaSenha, sucesso -> {
                if (sucesso) {
                    Toast.makeText(requireContext(), "Senha redefinida com sucesso!", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigateUp();
                } else {
                    binding.tvErro.setText("E-mail não encontrado ou erro na conexão");
                    binding.tvErro.setVisibility(View.VISIBLE);
                }
            });
        });

        binding.btnVoltar.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
