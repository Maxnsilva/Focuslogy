package com.example.focuslogy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.focuslogy.data.entity.SessaoEntity;
import com.example.focuslogy.data.mysql.SessaoRepositoryMySQL;
import com.example.focuslogy.util.AppExecutors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SessaoViewModel extends ViewModel {

    private final SessaoRepositoryMySQL repository;
    private final MutableLiveData<List<SessaoEntity>> historico = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> tempoHoje = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> sessoesHoje = new MutableLiveData<>(0);
    private int usuarioId = -1;

    public SessaoViewModel(SessaoRepositoryMySQL repository) {
        this.repository = repository;
    }

    public LiveData<List<SessaoEntity>> getHistorico() { return historico; }
    public LiveData<Integer> getTempoHoje() { return tempoHoje; }
    public LiveData<Integer> getSessoesHoje() { return sessoesHoje; }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        carregarDados();
    }

    public void carregarDados() {
        if (usuarioId == -1) return;
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<SessaoEntity> list = repository.listarPorUsuario(usuarioId);
            long inicioDia = getInicioDia();
            int tempo = repository.somarMinutosHoje(usuarioId, inicioDia);
            int count = repository.contarSessoesHoje(usuarioId, inicioDia);

            AppExecutors.getInstance().mainThread().execute(() -> {
                historico.setValue(list);
                tempoHoje.setValue(tempo);
                sessoesHoje.setValue(count);
            });
        });
    }

    public void salvarSessao(SessaoEntity sessao, Runnable onComplete) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.inserir(sessao);
            carregarDados();
            if (onComplete != null) {
                AppExecutors.getInstance().mainThread().execute(onComplete);
            }
        });
    }

    private long getInicioDia() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
