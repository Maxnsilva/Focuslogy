package com.example.focuslogy.ui.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.focuslogy.data.entity.UsuarioEntity;
import com.example.focuslogy.databinding.FragmentCronometroBinding;
import com.example.focuslogy.viewmodel.AuthViewModel;
import com.example.focuslogy.viewmodel.SessaoViewModel;

import java.util.Locale;

public class CronometroFragment extends Fragment {

    private FragmentCronometroBinding binding;
    private AuthViewModel authViewModel;
    private SessaoViewModel sessaoViewModel;

    private int materiaId = 0;
    private int minutosIniciais = 25;
    private String assunto = "";
    private String tecnica = "";

    private CountDownTimer countDownTimer;
    private long millisLeft = 0;
    private boolean isRunning = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCronometroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        authViewModel = new ViewModelProvider(activity, activity.getFactory()).get(AuthViewModel.class);
        sessaoViewModel = new ViewModelProvider(activity, activity.getFactory()).get(SessaoViewModel.class);

        if (getArguments() != null) {
            materiaId = getArguments().getInt("materiaId", 0);
            minutosIniciais = getArguments().getInt("minutos", 25);
            assunto = getArguments().getString("assunto", "");
            tecnica = getArguments().getString("tecnica", "Pomodoro");
        }

        binding.tvTecnica.setText(tecnica);
        binding.tvAssunto.setText(assunto);

        millisLeft = minutosIniciais * 60 * 1000L;
        updateTimerText();
        startTimer();

        binding.btnVoltar.setOnClickListener(v -> {
            pauseTimer();
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnPausar.setOnClickListener(v -> {
            if (isRunning) {
                pauseTimer();
                binding.btnPausar.setText("Continuar");
            } else {
                startTimer();
                binding.btnPausar.setText("Pausar");
            }
        });

        binding.btnFinalizar.setOnClickListener(v -> finalizarSessao());
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(millisLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                millisLeft = 0;
                updateTimerText();
                finalizarSessao();
            }
        }.start();
        isRunning = true;
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
    }

    private void updateTimerText() {
        int minutes = (int) (millisLeft / 1000) / 60;
        int seconds = (int) (millisLeft / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        if (binding != null) {
            binding.tvTimer.setText(timeFormatted);
        }
    }

    private void finalizarSessao() {
        pauseTimer();
        int segundosEstudados = (int) ((minutosIniciais * 60 * 1000L - millisLeft) / 1000);
        int minutosEstudados = Math.max(1, segundosEstudados / 60);

        UsuarioEntity usuario = authViewModel.getUsuarioLogado().getValue();
        int usuarioId = usuario != null ? usuario.getId() : 0;

        SessaoEntity sessao = new SessaoEntity(usuarioId, materiaId, assunto, minutosEstudados, tecnica);
        sessaoViewModel.salvarSessao(sessao, () -> {
            if (isAdded() && getView() != null) {
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pauseTimer();
        binding = null;
    }
}
