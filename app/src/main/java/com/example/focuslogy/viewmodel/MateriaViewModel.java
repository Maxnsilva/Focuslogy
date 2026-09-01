package com.example.focuslogy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.focuslogy.data.entity.MateriaEntity;
import com.example.focuslogy.data.mysql.MateriaRepositoryMySQL;
import com.example.focuslogy.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class MateriaViewModel extends ViewModel {

    private final MateriaRepositoryMySQL repository;
    private final MutableLiveData<List<MateriaEntity>> materias = new MutableLiveData<>(new ArrayList<>());
    private int usuarioId = -1;

    public MateriaViewModel(MateriaRepositoryMySQL repository) {
        this.repository = repository;
    }

    public LiveData<List<MateriaEntity>> getMaterias() {
        return materias;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        carregarMaterias();
    }

    public void carregarMaterias() {
        if (usuarioId == -1) return;
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<MateriaEntity> lista = repository.listarPorUsuario(usuarioId);
            AppExecutors.getInstance().mainThread().execute(() -> materias.setValue(lista));
        });
    }

    public void cadastrar(MateriaEntity materia, Runnable onComplete) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.inserir(materia);
            carregarMaterias();
            if (onComplete != null) {
                AppExecutors.getInstance().mainThread().execute(onComplete);
            }
        });
    }

    public void atualizar(MateriaEntity materia, Runnable onComplete) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.atualizar(materia);
            carregarMaterias();
            if (onComplete != null) {
                AppExecutors.getInstance().mainThread().execute(onComplete);
            }
        });
    }

    public void excluir(int materiaId, Runnable onComplete) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            repository.excluir(materiaId);
            carregarMaterias();
            if (onComplete != null) {
                AppExecutors.getInstance().mainThread().execute(onComplete);
            }
        });
    }

    public void buscarPorId(int id, Consumer<MateriaEntity> callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            MateriaEntity materia = repository.buscarPorId(id);
            AppExecutors.getInstance().mainThread().execute(() -> callback.accept(materia));
        });
    }

    public interface Consumer<T> {
        void accept(T result);
    }
}
