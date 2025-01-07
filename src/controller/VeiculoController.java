package controller;

import dao.VeiculoDAO;
import models.Veiculo;
import models.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class VeiculoController {

    @FXML
    private TextField modeloField;
    @FXML
    private TextField placaField;
    @FXML
    private TextField searchFieldVeiculo;
    @FXML
    private TableView<Veiculo> tableViewVeiculos;
    @FXML
    private TableColumn<Veiculo, String> modeloColumn;
    @FXML
    private TableColumn<Veiculo, String> placaColumn;

    private VeiculoDAO veiculoDAO;
    private ClienteController clienteController;  // Referência ao controlador de clientes

    public VeiculoController() {
        veiculoDAO = new VeiculoDAO();
    }

    // Método para inicializar a tabela com os dados
    @FXML
    public void initialize() {
        modeloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        placaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlaca()));
        updateTable();
    }

    // Configurar a referência ao ClienteController
    public void setClienteController(ClienteController clienteController) {
        this.clienteController = clienteController;
    }

 // Método para adicionar um veículo
    @FXML
    private void addVeiculo() {
        try {
            String modelo = modeloField.getText();
            String placa = placaField.getText();

            // Pegar o cliente selecionado automaticamente
            Cliente clienteSelecionado = clienteController.getClienteSelecionado();
            
            // Verificar se um cliente foi selecionado
            if (clienteSelecionado == null) {
                // Exibe um Alert caso o cliente não tenha sido selecionado
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, selecione um cliente antes de adicionar o veículo.");
                alert.showAndWait();
                return;
            }

            // Verificar se todos os campos foram preenchidos
            if (modelo.isEmpty() || placa.isEmpty()) {
                System.out.println("Todos os campos devem ser preenchidos.");
                return;
            }

            // Criar o veículo com o ID do cliente selecionado
            int clienteId = clienteSelecionado.getId();
            Veiculo veiculo = new Veiculo(modelo, placa, clienteId);
            veiculoDAO.addVeiculo(veiculo);
            updateTable();
        } catch (Exception e) {
            System.out.println("Erro ao adicionar veículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para remover um veículo
    @FXML
    private void removeVeiculo() {
        Veiculo veiculoSelecionado = tableViewVeiculos.getSelectionModel().getSelectedItem();
        if (veiculoSelecionado != null) {
            // Remove o veículo do banco de dados
            veiculoDAO.removeVeiculo(veiculoSelecionado.getId());
            updateTable();
        }
    }

    // Método para editar um veículo
    @FXML
    private void editarVeiculo() {
        Veiculo veiculoSelecionado = tableViewVeiculos.getSelectionModel().getSelectedItem();
        if (veiculoSelecionado != null) {
            // Atualiza os dados do veículo
            veiculoSelecionado.setModelo(modeloField.getText());
            veiculoSelecionado.setPlaca(placaField.getText());
            Cliente clienteSelecionado = clienteController.getClienteSelecionado();  // Obtendo cliente selecionado
            if (clienteSelecionado != null) {
                veiculoSelecionado.setClienteId(clienteSelecionado.getId());  // Atualizando clienteId
            }
            veiculoDAO.updateVeiculo(veiculoSelecionado);
            updateTable();
        }
    }
    
    @FXML
    private TextField clienteIdField; // Campo que irá exibir o ID do cliente

    // Método para definir o ID do cliente automaticamente
    public void setClienteId(int clienteId) {
        if (clienteIdField != null) {
            clienteIdField.setText(String.valueOf(clienteId));  // Atualiza o campo com o ID do cliente
        } else {
            System.out.println("Erro: campo clienteIdField não está configurado.");
        }
    }

    // Método para atualizar a tabela com a lista de veículos
    private void updateTable() {
        ObservableList<Veiculo> veiculos = FXCollections.observableArrayList(veiculoDAO.getVeiculos());
        tableViewVeiculos.setItems(veiculos);
    }

    // Método para buscar veículos por modelo ou placa
    @FXML
    private void searchVeiculo() {
        String searchTerm = searchFieldVeiculo.getText();
        ObservableList<Veiculo> veiculos = FXCollections.observableArrayList(veiculoDAO.searchVeiculos(searchTerm));
        tableViewVeiculos.setItems(veiculos);
    }
}
