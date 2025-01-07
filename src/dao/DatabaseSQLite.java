package dao;

import java.sql.*;

public class DatabaseSQLite {

    private static final String URL = "jdbc:sqlite:oficina.db"; // Caminho do arquivo de banco de dados

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Criar as tabelas do banco de dados
    public static void createTables() {
        // Criar a tabela "clientes"
        String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes ("
                           + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                           + "nome TEXT NOT NULL, "
                           + "telefone TEXT, "
                           + "endereco TEXT"
                           + ");";
        // Criar a tabela "carros"
        String sqlCarros = "CREATE TABLE IF NOT EXISTS carros ("
                          + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                          + "modelo TEXT NOT NULL, "
                          + "placa TEXT NOT NULL, "
                          + "cliente_id INTEGER, "
                          + "FOREIGN KEY(cliente_id) REFERENCES clientes(id)"
                          + ");";
        // Criar a tabela "veiculos"
        String sqlVeiculos = "CREATE TABLE IF NOT EXISTS veiculos ("
                             + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                             + "modelo TEXT NOT NULL, "
                             + "placa TEXT NOT NULL, "
                             + "cliente_id INTEGER, "
                             + "FOREIGN KEY(cliente_id) REFERENCES clientes(id)"
                             + ");";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sqlClientes); // Cria a tabela de clientes
            stmt.execute(sqlCarros);   // Cria a tabela de carros
            stmt.execute(sqlVeiculos); // Cria a tabela de veiculos
            System.out.println("Tabelas clientes, carros e veiculos criadas com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
