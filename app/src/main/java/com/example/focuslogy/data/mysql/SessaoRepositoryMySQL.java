package com.example.focuslogy.data.mysql;

import com.example.focuslogy.ConectionMySQL;
import com.example.focuslogy.data.entity.SessaoEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessaoRepositoryMySQL {

    public boolean inserir(SessaoEntity sessao) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return false;
        try {
            String query = "INSERT INTO sessoes (usuarioId, materiaId, assunto, duracaoMinutos, tecnica, data, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, sessao.getUsuarioId());
            pstmt.setInt(2, sessao.getMateriaId());
            pstmt.setString(3, sessao.getAssunto());
            pstmt.setInt(4, sessao.getDuracaoMinutos());
            pstmt.setString(5, sessao.getTecnica());
            pstmt.setLong(6, sessao.getData());
            pstmt.setString(7, sessao.getStatus());
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SessaoEntity> listarPorUsuario(int usuarioId) {
        Connection conn = ConectionMySQL.conectar();
        List<SessaoEntity> lista = new ArrayList<>();
        if (conn == null) return lista;
        try {
            String query = "SELECT * FROM sessoes WHERE usuarioId = ? ORDER BY data DESC";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new SessaoEntity(
                        rs.getInt("id"),
                        rs.getInt("usuarioId"),
                        rs.getInt("materiaId"),
                        rs.getString("assunto"),
                        rs.getInt("duracaoMinutos"),
                        rs.getString("tecnica"),
                        rs.getLong("data"),
                        rs.getString("status")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public int somarMinutosHoje(int usuarioId, long inicioDia) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return 0;
        try {
            String query = "SELECT SUM(duracaoMinutos) FROM sessoes WHERE usuarioId = ? AND data >= ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, usuarioId);
            pstmt.setLong(2, inicioDia);
            ResultSet rs = pstmt.executeQuery();
            int total = 0;
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int contarSessoesHoje(int usuarioId, long inicioDia) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return 0;
        try {
            String query = "SELECT COUNT(*) FROM sessoes WHERE usuarioId = ? AND data >= ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, usuarioId);
            pstmt.setLong(2, inicioDia);
            ResultSet rs = pstmt.executeQuery();
            int total = 0;
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
