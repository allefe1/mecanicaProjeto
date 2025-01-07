package dao;

import models.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    // Método para adicionar um veículo
	public void addVeiculo(Veiculo veiculo) {
	    String sql = "INSERT INTO veiculos (modelo, placa, cliente_id) VALUES (?, ?, ?)";
	    
	    try (Connection conn = DatabaseSQLite.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        // Setando os valores dos parâmetros da consulta
	        stmt.setString(1, veiculo.getModelo());  // Modelo do veículo
	        stmt.setString(2, veiculo.getPlaca());   // Placa do veículo
	        stmt.setInt(3, veiculo.getClienteId());  // ID do cliente associado ao veículo

	        int rowsAffected = stmt.executeUpdate();  // Executando a inserção
	        if (rowsAffected > 0) {
	            try (ResultSet rs = stmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    int generatedId = rs.getInt(1);  // ID gerado pelo banco de dados
	                    veiculo.setId(generatedId);  // Atualize o objeto Veiculo com o ID gerado
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void createVeiculosTable() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos ("  // Corrigido para 'veiculos'
                 + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + "modelo TEXT NOT NULL, "
                 + "placa TEXT NOT NULL, "
                 + "cliente_id INTEGER, "
                 + "FOREIGN KEY(cliente_id) REFERENCES clientes(id)"
                 + ");";

        try (Connection connection = DatabaseSQLite.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela veiculos criada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os veículos de um cliente
    public List<Veiculo> getVeiculosByCliente(int clienteId) {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos WHERE cliente_id = ?";  // Corrigido para 'veiculos'
        try (Connection connection = DatabaseSQLite.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String placa = rs.getString("placa");
                    Veiculo veiculo = new Veiculo(modelo, placa, clienteId);
                    veiculo.setId(id);
                    veiculos.add(veiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    // Método para atualizar um veículo
    public void updateVeiculo(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET modelo = ?, placa = ?, cliente_id = ? WHERE id = ?";  // Corrigido para 'veiculos'
        try (Connection connection = DatabaseSQLite.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getModelo());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setInt(3, veiculo.getClienteId());  // Corrigido para ter apenas um 'setInt' do cliente_id
            stmt.setInt(4, veiculo.getId());  // Correção para setar o id corretamente
            stmt.executeUpdate();
            System.out.println("Veículo atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para remover um veículo
    public void removeVeiculo(int id) {
        String sql = "DELETE FROM veiculos WHERE id = ?";  // Corrigido para 'veiculos'
        try (Connection connection = DatabaseSQLite.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Veículo removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // Método para listar todos os veículos
    public List<Veiculo> getVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";  // Seleciona todos os veículos

        try (Connection connection = DatabaseSQLite.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String modelo = rs.getString("modelo");
                String placa = rs.getString("placa");
                int clienteId = rs.getInt("cliente_id");

                Veiculo veiculo = new Veiculo(modelo, placa, clienteId);
                veiculo.setId(id);  // Definindo o id retornado do banco de dados
                veiculos.add(veiculo);  // Adiciona o veículo à lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veiculos;  // Retorna a lista de veículos
    }

    // Método para buscar veículos por modelo ou placa
    public List<Veiculo> searchVeiculos(String searchTerm) {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos WHERE modelo LIKE ? OR placa LIKE ?";
        try (Connection connection = DatabaseSQLite.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String modelo = rs.getString("modelo");
                    String placa = rs.getString("placa");
                    int clienteId = rs.getInt("cliente_id");
                    Veiculo veiculo = new Veiculo(modelo, placa, clienteId);
                    veiculo.setId(id);
                    veiculos.add(veiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }
}
