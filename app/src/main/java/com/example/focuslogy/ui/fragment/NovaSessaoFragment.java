package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.focuslogy.MainActivity;
import com.example.focuslogy.R;
import com.example.focuslogy.data.entity.MateriaEntity;
import com.example.focuslogy.databinding.FragmentNovaSessaoBinding;
import com.example.focuslogy.viewmodel.MateriaViewModel;

import java.util.ArrayList;
import java.util.List;

public class NovaSessaoFragment extends Fragment {

    private FragmentNovaSessaoBinding binding;
    private MateriaViewModel materiaViewModel;
    private List<MateriaEntity> listaMaterias = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNovaSessaoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        materiaViewModel = new ViewModelProvider(activity, activity.getFactory()).get(MateriaViewModel.class);

        String[] tecnicas = new String[]{"Pomodoro", "Estudo Livre", "Recordação Ativa", "Método Feynman"};
        ArrayAdapter<String> adapterTecnicas = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tecnicas);
        adapterTecnicas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spTecnicas.setAdapter(adapterTecnicas);

        materiaViewModel.getMaterias().observe(getViewLifecycleOwner(), materias -> {
            listaMaterias = materias != null ? materias : new ArrayList<>();
            List<String> nomes = new ArrayList<>();
            for (MateriaEntity m : listaMaterias) {
                nomes.add(m.getNome());
            }
            ArrayAdapter<String> adapterMaterias = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nomes);
            adapterMaterias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spMaterias.setAdapter(adapterMaterias);
        });

        binding.btnVoltar.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        binding.btnIniciarSessao.setOnClickListener(v -> {
            if (listaMaterias.isEmpty()) {
                Toast.makeText(requireContext(), "Cadastre ao menos uma matéria antes de estudar", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedPos = binding.spMaterias.getSelectedItemPosition();
            if (selectedPos < 0 || selectedPos >= listaMaterias.size()) return;

            MateriaEntity materia = listaMaterias.get(selectedPos);
            String assunto = binding.etAssunto.getText() != null ? binding.etAssunto.getText().toString().trim() : "";
            String duracaoStr = binding.etDuracao.getText() != null ? binding.etDuracao.getText().toString().trim() : "25";
            String tecnica = binding.spTecnicas.getSelectedItem() != null ? binding.spTecnicas.getSelectedItem().toString() : "Pomodoro";

            if (TextUtils.isEmpty(assunto)) {
                Toast.makeText(requireContext(), "Informe o assunto", Toast.LENGTH_SHORT).show();
                return;
            }

            int minutos = TextUtils.isEmpty(duracaoStr) ? 25 : Integer.parseInt(duracaoStr);

            Bundle args = new Bundle();
            args.putInt("materiaId", materia.getId());
            args.putInt("minutos", minutos);
            args.putString("assunto", assunto);
            args.putString("tecnica", tecnica);

            Navigation.findNavController(view).navigate(R.id.cronometroFragment, args);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
