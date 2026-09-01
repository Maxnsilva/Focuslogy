package com.example.focuslogy.data.entity;

public class MetaEntity {
    private int id;
    private int usuarioId;
    private Integer materiaId;
    private String titulo;
    private int minutosObjetivo;
    private long dataInicio;
    private long dataFim;
    private String status;

    public MetaEntity(int id, int usuarioId, Integer materiaId, String titulo, int minutosObjetivo, long dataInicio, long dataFim, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.materiaId = materiaId;
        this.titulo = titulo;
        this.minutosObjetivo = minutosObjetivo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public Integer getMateriaId() { return materiaId; }
    public void setMateriaId(Integer materiaId) { this.materiaId = materiaId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getMinutosObjetivo() { return minutosObjetivo; }
    public void setMinutosObjetivo(int minutosObjetivo) { this.minutosObjetivo = minutosObjetivo; }

    public long getDataInicio() { return dataInicio; }
    public void setDataInicio(long dataInicio) { this.dataInicio = dataInicio; }

    public long getDataFim() { return dataFim; }
    public void setDataFim(long dataFim) { this.dataFim = dataFim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
