package com.example.focuslogy.data.entity;

public class MateriaEntity {
    private int id;
    private int usuarioId;
    private String nome;
    private String descricao;
    private String cor;
    private int metaSemanalMinutos;
    private long dataCriacao;

    public MateriaEntity(int id, int usuarioId, String nome, String descricao, String cor, int metaSemanalMinutos, long dataCriacao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.descricao = descricao;
        this.cor = cor;
        this.metaSemanalMinutos = metaSemanalMinutos;
        this.dataCriacao = dataCriacao;
    }

    public MateriaEntity(int usuarioId, String nome, String descricao, String cor, int metaSemanalMinutos) {
        this(0, usuarioId, nome, descricao, cor, metaSemanalMinutos, System.currentTimeMillis());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public int getMetaSemanalMinutos() { return metaSemanalMinutos; }
    public void setMetaSemanalMinutos(int metaSemanalMinutos) { this.metaSemanalMinutos = metaSemanalMinutos; }

    public long getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(long dataCriacao) { this.dataCriacao = dataCriacao; }
}
