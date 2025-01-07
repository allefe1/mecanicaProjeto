package dao;

import models.Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    private Connection connection;

    public ServicoDAO() {
        try {
            connection = DatabaseSQLite.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(); // Registra o erro no console
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    public void addServico(Servico servico) {
        String sql = "INSERT INTO servicos (descricao, preco, data, veiculo_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getPreco());
            stmt.setString(3, servico.getData());
            stmt.setInt(4, servico.getVeiculoId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Servico> getServicosByClienteId(int clienteId) {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos WHERE veiculo_id IN (SELECT id FROM veiculos WHERE cliente_id = ?)";
        try (Connection connection = DatabaseSQLite.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String descricao = rs.getString("descricao");
                    double preco = rs.getDouble("preco");
                    int veiculoId = rs.getInt("veiculo_id");
                    Servico servico = new Servico(id, descricao, preco, veiculoId);
                    servicos.add(servico);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicos;
    }

    public List<Servico> getServicos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Servico servico = new Servico(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getDouble("preco"),
                    rs.getString("data"),
                    rs.getInt("veiculo_id")
                );
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicos;
    }

    public void removeServico(int id) {
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}