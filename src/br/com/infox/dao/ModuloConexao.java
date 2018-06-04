package br.com.infox.dal;

import java.sql.*;

public class ModuloConexao {

    // Método responsável por fazer a conexão com o banco
    public static Connection conector() {

        java.sql.Connection conexao = null;

        // A linha abaixo "chama" o driver importado para a biblioteca
        String driver = "com.mysql.jdbc.Driver";

        // Armazenando informações referentes ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox";
        String user = "root";
        String password = "";

        // Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            // System.out.println(conexao);
			return conexao;
        } catch (Exception e) {
            // A linha abaixo serve para esclarecer o erro
            //System.out.println(e);
            return null;
        }

    }
}
