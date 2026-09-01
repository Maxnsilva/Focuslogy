package com.example.focuslogy.data.mysql;

import com.example.focuslogy.ConectionMySQL;
import com.example.focuslogy.data.entity.UsuarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsuarioRepositoryMySQL {

    public int cadastrar(UsuarioEntity usuario) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return -1;
        try {
            String query = "INSERT INTO usuarios (nome, email, senha, metaDiariaMinutos, dataCadastro) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setInt(4, usuario.getMetaDiariaMinutos());
            pstmt.setLong(5, usuario.getDataCadastro());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public UsuarioEntity login(String email, String senha) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return null;
        try {
            String query = "SELECT * FROM usuarios WHERE email = ? AND senha = ? LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            UsuarioEntity user = null;
            if (rs.next()) {
                user = new UsuarioEntity(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("metaDiariaMinutos"),
                        rs.getLong("dataCadastro")
                );
            }
            rs.close();
            pstmt.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UsuarioEntity buscarPorId(int id) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return null;
        try {
            String query = "SELECT * FROM usuarios WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            UsuarioEntity user = null;
            if (rs.next()) {
                user = new UsuarioEntity(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("metaDiariaMinutos"),
                        rs.getLong("dataCadastro")
                );
            }
            rs.close();
            pstmt.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean atualizarPerfil(int id, String nome, int metaDiaria) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return false;
        try {
            String query = "UPDATE usuarios SET nome = ?, metaDiariaMinutos = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nome);
            pstmt.setInt(2, metaDiaria);
            pstmt.setInt(3, id);
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean redefinirSenha(String email, String novaSenha) {
        Connection conn = ConectionMySQL.conectar();
        if (conn == null) return false;
        try {
            String query = "UPDATE usuarios SET senha = ? WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, novaSenha);
            pstmt.setString(2, email);
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
