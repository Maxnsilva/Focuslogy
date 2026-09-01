package com.example.focuslogy.data.entity;

public class UsuarioEntity {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private int metaDiariaMinutos;
    private long dataCadastro;

    public UsuarioEntity(int id, String nome, String email, String senha, int metaDiariaMinutos, long dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.metaDiariaMinutos = metaDiariaMinutos;
        this.dataCadastro = dataCadastro;
    }

    public UsuarioEntity(String nome, String email, String senha) {
        this(0, nome, email, senha, 60, System.currentTimeMillis());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public int getMetaDiariaMinutos() { return metaDiariaMinutos; }
    public void setMetaDiariaMinutos(int metaDiariaMinutos) { this.metaDiariaMinutos = metaDiariaMinutos; }

    public long getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(long dataCadastro) { this.dataCadastro = dataCadastro; }
}
