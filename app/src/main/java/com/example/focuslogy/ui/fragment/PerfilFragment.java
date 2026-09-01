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
import com.example.focuslogy.R;
import com.example.focuslogy.data.entity.UsuarioEntity;
import com.example.focuslogy.databinding.FragmentPerfilBinding;
import com.example.focuslogy.viewmodel.AuthViewModel;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        authViewModel = new ViewModelProvider(activity, activity.getFactory()).get(AuthViewModel.class);

        binding.btnVoltar.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        authViewModel.getUsuarioLogado().observe(getViewLifecycleOwner(), usuario -> {
            if (usuario != null) {
                binding.etNome.setText(usuario.getNome());
                binding.etMetaDiaria.setText(String.valueOf(usuario.getMetaDiariaMinutos()));
            }
        });

        binding.btnSalvar.setOnClickListener(v -> {
            UsuarioEntity usuario = authViewModel.getUsuarioLogado().getValue();
            if (usuario == null) return;

            String nome = binding.etNome.getText() != null ? binding.etNome.getText().toString().trim() : "";
            String metaStr = binding.etMetaDiaria.getText() != null ? binding.etMetaDiaria.getText().toString().trim() : "60";

            if (TextUtils.isEmpty(nome)) {
                Toast.makeText(requireContext(), "Informe o nome", Toast.LENGTH_SHORT).show();
                return;
            }

            int meta = TextUtils.isEmpty(metaStr) ? 60 : Integer.parseInt(metaStr);
            authViewModel.atualizarPerfil(usuario.getId(), nome, meta);
            Toast.makeText(requireContext(), "Perfil atualizado!", Toast.LENGTH_SHORT).show();
        });

        binding.btnLogout.setOnClickListener(v -> {
            authViewModel.logout();
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
