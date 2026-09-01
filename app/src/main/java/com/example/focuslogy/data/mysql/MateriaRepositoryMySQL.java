package com.example.focuslogy.data.mysql;

import com.example.focuslogy.ConectionMySQL;
import com.example.focuslogy.data.entity.MateriaEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MateriaRepositoryMySQL {

    public boolean inserir(MateriaEntity materia) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return false;
        try {
            String query = "INSERT INTO materias (usuarioId, nome, descricao, cor, metaSemanalMinutos, dataCriacao) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, materia.getUsuarioId());
            pstmt.setString(2, materia.getNome());
            pstmt.setString(3, materia.getDescricao());
            pstmt.setString(4, materia.getCor());
            pstmt.setInt(5, materia.getMetaSemanalMinutos());
            pstmt.setLong(6, materia.getDataCriacao());
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(MateriaEntity materia) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return false;
        try {
            String query = "UPDATE materias SET nome = ?, descricao = ?, cor = ?, metaSemanalMinutos = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, materia.getNome());
            pstmt.setString(2, materia.getDescricao());
            pstmt.setString(3, materia.getCor());
            pstmt.setInt(4, materia.getMetaSemanalMinutos());
            pstmt.setInt(5, materia.getId());
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return false;
        try {
            String query = "DELETE FROM materias WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MateriaEntity> listarPorUsuario(int usuarioId) {
        Connection conn = ConectionMySQL.conectar();
        List<MateriaEntity> lista = new ArrayList<>();
        if (conn == null) return lista;
        try {
            String query = "SELECT * FROM materias WHERE usuarioId = ? ORDER BY nome ASC";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new MateriaEntity(
                        rs.getInt("id"),
                        rs.getInt("usuarioId"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("cor"),
                        rs.getInt("metaSemanalMinutos"),
                        rs.getLong("dataCriacao")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public MateriaEntity buscarPorId(int id) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return null;
        try {
            String query = "SELECT * FROM materias WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            MateriaEntity result = null;
            if (rs.next()) {
                result = new MateriaEntity(
                        rs.getInt("id"),
                        rs.getInt("usuarioId"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("cor"),
                        rs.getInt("metaSemanalMinutos"),
                        rs.getLong("dataCriacao")
                );
            }
            rs.close();
            pstmt.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
