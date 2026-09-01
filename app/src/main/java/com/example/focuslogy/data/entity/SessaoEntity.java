package com.example.focuslogy.data.entity;

public class SessaoEntity {
    private int id;
    private int usuarioId;
    private int materiaId;
    private String assunto;
    private int duracaoMinutos;
    private String tecnica;
    private long data;
    private String status;

    public SessaoEntity(int id, int usuarioId, int materiaId, String assunto, int duracaoMinutos, String tecnica, long data, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.materiaId = materiaId;
        this.assunto = assunto;
        this.duracaoMinutos = duracaoMinutos;
        this.tecnica = tecnica;
        this.data = data;
        this.status = status;
    }

    public SessaoEntity(int usuarioId, int materiaId, String assunto, int duracaoMinutos, String tecnica) {
        this(0, usuarioId, materiaId, assunto, duracaoMinutos, tecnica, System.currentTimeMillis(), "CONCLUIDA");
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }

    public int getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(int duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public String getTecnica() { return tecnica; }
    public void setTecnica(String tecnica) { this.tecnica = tecnica; }

    public long getData() { return data; }
    public void setData(long data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
