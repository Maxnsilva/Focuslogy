package com.example.focuslogy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.focuslogy.data.entity.UsuarioEntity;
import com.example.focuslogy.data.mysql.UsuarioRepositoryMySQL;
import com.example.focuslogy.util.AppExecutors;

public class AuthViewModel extends ViewModel {

    private final UsuarioRepositoryMySQL repository;
    private final MutableLiveData<UsuarioEntity> usuarioLogado = new MutableLiveData<>(null);
    private final MutableLiveData<String> erro = new MutableLiveData<>(null);

    public AuthViewModel(UsuarioRepositoryMySQL repository) {
        this.repository = repository;
    }

    public LiveData<UsuarioEntity> getUsuarioLogado() {
        return usuarioLogado;
    }

    public LiveData<String> getErro() {
        return erro;
    }

    public void cadastrar(String nome, String email, String senha, Runnable onSuccess) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            UsuarioEntity novoUsuario = new UsuarioEntity(nome, email, senha);
            int id = repository.cadastrar(novoUsuario);
            if (id != -1) {
                UsuarioEntity usuario = repository.buscarPorId(id);
                AppExecutors.getInstance().mainThread().execute(() -> {
                    usuarioLogado.setValue(usuario);
                    if (onSuccess != null) onSuccess.run();
                });
            } else {
                AppExecutors.getInstance().mainThread().execute(() -> {
                    erro.setValue("Erro ao cadastrar usuário");
                });
            }
        });
    }

    public void login(String email, String senha, Runnable onSuccess) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            UsuarioEntity usuario = repository.login(email, senha);
            AppExecutors.getInstance().mainThread().execute(() -> {
                if (usuario != null) {
                    usuarioLogado.setValue(usuario);
                    if (onSuccess != null) onSuccess.run();
                } else {
                    erro.setValue("E-mail ou senha incorretos ou erro de conexão");
                }
            });
        });
    }

    public void logout() {
        usuarioLogado.setValue(null);
    }

    public void atualizarPerfil(int id, String nome, int metaDiaria) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean sucesso = repository.atualizarPerfil(id, nome, metaDiaria);
            if (sucesso) {
                UsuarioEntity usuario = repository.buscarPorId(id);
                AppExecutors.getInstance().mainThread().execute(() -> {
                    usuarioLogado.setValue(usuario);
                });
            } else {
                AppExecutors.getInstance().mainThread().execute(() -> {
                    erro.setValue("Erro ao atualizar perfil");
                });
            }
        });
    }

    public void redefinirSenha(String email, String novaSenha, Consumer<Boolean> callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean sucesso = repository.redefinirSenha(email, novaSenha);
            AppExecutors.getInstance().mainThread().execute(() -> {
                if (callback != null) callback.accept(sucesso);
            });
        });
    }

    public interface Consumer<T> {
        void accept(T result);
    }

    public void limparErro() {
        erro.setValue(null);
    }
}
