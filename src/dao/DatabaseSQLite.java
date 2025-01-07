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

        // Criar a tabela "veiculos"
        String sqlVeiculos = "CREATE TABLE IF NOT EXISTS veiculos ("
                           + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                           + "modelo TEXT NOT NULL, "
                           + "placa TEXT NOT NULL, "
                           + "cliente_id INTEGER NOT NULL, "
                           + "FOREIGN KEY(cliente_id) REFERENCES clientes(id)"
                           + ");";

        // Criar a tabela "servicos" com a coluna "preco" caso não exista
        String sqlServicos = "CREATE TABLE IF NOT EXISTS servicos ("
                           + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                           + "descricao TEXT NOT NULL, "
                           + "preco REAL NOT NULL, "
                           + "data TEXT NOT NULL, "
                           + "veiculo_id INTEGER NOT NULL, "
                           + "FOREIGN KEY(veiculo_id) REFERENCES veiculos(id)"
                           + ");";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sqlClientes); // Cria a tabela de clientes
            stmt.execute(sqlVeiculos); // Cria a tabela de veículos
            stmt.execute(sqlServicos); // Cria a tabela de serviços
            System.out.println("Tabelas clientes, veículos e serviços criadas com sucesso!");

            // Verificar se a tabela servicos já existe, se a coluna 'preco' existe
            ResultSet rs = stmt.executeQuery("PRAGMA table_info(servicos);");
            boolean colunaPrecoExiste = false;
            while (rs.next()) {
                if (rs.getString("name").equals("preco")) {
                    colunaPrecoExiste = true;
                    break;
                }
            }

            // Caso a coluna 'preco' não exista, adicioná-la
            if (!colunaPrecoExiste) {
                String alterTableSQL = "ALTER TABLE servicos ADD COLUMN preco REAL NOT NULL;";
                stmt.execute(alterTableSQL);
                System.out.println("Coluna 'preco' adicionada à tabela 'servicos'.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
