package com.example.focuslogy;

import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionMySQL {

    private static final String TAG = "MySQLConnection";
    private static final String URL = "jdbc:mysql://10.0.2.2:3307/focuslogy?useSSL=false&allowPublicKeyRetrieval=true&connectTimeout=3000&socketTimeout=3000";
    private static final String USER = "root";
    private static final String PASSWORD = "senac";

    private static Connection cacheConn = null;

    public static synchronized Connection conectar() {
        try {
            // Verifica se a conexão já existe e está aberta
            if (cacheConn != null && !cacheConn.isClosed()) {
                return cacheConn;
            }

            // Registro do driver
            Class.forName("com.mysql.jdbc.Driver");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            
            Log.d(TAG, "Iniciando nova conexão JDBC...");
            cacheConn = DriverManager.getConnection(URL, USER, PASSWORD);
            Log.d(TAG, "Conectado ao MySQL com sucesso!");
            return cacheConn;

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Driver MySQL não encontrado!", e);
            return null;
        } catch (SQLException e) {
            Log.e(TAG, "Erro de SQL na conexão: " + e.getMessage());
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Erro inesperado na conexão: " + e.getMessage());
            return null;
        }
    }

    // Agora o fecharConexao não será chamado em toda query, apenas se necessário
    public static synchronized void fecharConexao() {
        try {
            if (cacheConn != null && !cacheConn.isClosed()) {
                cacheConn.close();
                cacheConn = null;
                Log.d(TAG, "Conexão fechada manualmente.");
            }
        } catch (SQLException e) {
            Log.e(TAG, "Erro ao fechar conexão", e);
        }
    }
}
