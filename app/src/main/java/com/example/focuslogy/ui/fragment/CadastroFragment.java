package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.focuslogy.databinding.FragmentCadastroBinding;
import com.example.focuslogy.viewmodel.AuthViewModel;

public class CadastroFragment extends Fragment {

    private FragmentCadastroBinding binding;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = new ViewModelProvider(requireActivity(), ((MainActivity) requireActivity()).getFactory()).get(AuthViewModel.class);

        authViewModel.getErro().observe(getViewLifecycleOwner(), erro -> {
            if (!TextUtils.isEmpty(erro)) {
                binding.tvErro.setText(erro);
                binding.tvErro.setVisibility(View.VISIBLE);
            } else {
                binding.tvErro.setVisibility(View.GONE);
            }
        });

        binding.btnCadastrar.setOnClickListener(v -> {
            String nome = binding.etNome.getText() != null ? binding.etNome.getText().toString().trim() : "";
            String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
            String senha = binding.etSenha.getText() != null ? binding.etSenha.getText().toString().trim() : "";

            if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
                binding.tvErro.setText("Preencha todos os campos");
                binding.tvErro.setVisibility(View.VISIBLE);
                return;
            }

            authViewModel.cadastrar(nome, email, senha, () -> {
                Navigation.findNavController(view).navigate(R.id.nav_dashboard);
            });
        });

        binding.btnVoltarLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigateUp();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
