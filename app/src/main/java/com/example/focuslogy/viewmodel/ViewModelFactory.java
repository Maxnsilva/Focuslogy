package com.example.focuslogy.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.focuslogy.data.mysql.MateriaRepositoryMySQL;
import com.example.focuslogy.data.mysql.SessaoRepositoryMySQL;
import com.example.focuslogy.data.mysql.UsuarioRepositoryMySQL;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UsuarioRepositoryMySQL usuarioRepository;
    private final MateriaRepositoryMySQL materiaRepository;
    private final SessaoRepositoryMySQL sessaoRepository;

    public ViewModelFactory(UsuarioRepositoryMySQL usuarioRepository,
                            MateriaRepositoryMySQL materiaRepository,
                            SessaoRepositoryMySQL sessaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.materiaRepository = materiaRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            //noinspection unchecked
            return (T) new AuthViewModel(usuarioRepository);
        }
        if (modelClass.isAssignableFrom(MateriaViewModel.class)) {
            //noinspection unchecked
            return (T) new MateriaViewModel(materiaRepository);
        }
        if (modelClass.isAssignableFrom(SessaoViewModel.class)) {
            //noinspection unchecked
            return (T) new SessaoViewModel(sessaoRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
