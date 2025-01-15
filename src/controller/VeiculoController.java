package controller;

import dao.ClienteDAO;
import dao.VeiculoDAO;
import models.Veiculo;
import models.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    private TableColumn<Veiculo, String> idColumn;   // Coluna para o ID do veículo
    @FXML
    private TableColumn<Veiculo, String> modeloColumn;
    @FXML
    private TableColumn<Veiculo, String> placaColumn;
    @FXML
    private TextArea modeloDetalhes;
    @FXML
    private TextArea placaDetalhes;
    @FXML
    private TextArea donoDetalhes;

    private VeiculoDAO veiculoDAO;
    private ClienteController clienteController;  // Referência ao controlador de clientes

    public VeiculoController() {
        veiculoDAO = new VeiculoDAO();
    }

    // Método para inicializar a tabela com os dados
    @FXML
    public void initialize() {
    	idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        modeloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        placaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlaca()));
        updateTable();
        tableViewVeiculos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                exibirDetalhesDoVeiculo(newValue);
            }
        });
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

 // Editar veículo (preenche os campos)
    @FXML
    private void editarVeiculo() {
        Veiculo veiculoSelecionado = tableViewVeiculos.getSelectionModel().getSelectedItem();

        if (veiculoSelecionado != null) {
            modeloField.setText(veiculoSelecionado.getModelo());
            placaField.setText(veiculoSelecionado.getPlaca());
        } else {
            showAlert("Erro", "Nenhum veículo selecionado para edição.");
        }
    }

    
 // Salvar alterações no veículo
    @FXML
    private void salvarAlteracoes() {
        Veiculo veiculoSelecionado = tableViewVeiculos.getSelectionModel().getSelectedItem();

        if (veiculoSelecionado != null) {
            String modelo = modeloField.getText();
            String placa = placaField.getText();

            // Valida se os campos de modelo e placa estão preenchidos
            if (modelo.isEmpty() || placa.isEmpty()) {
                showAlert("Erro", "Modelo e Placa devem ser preenchidos.");
                return;
            }

            // Atualiza os dados do veículo
            veiculoSelecionado.setModelo(modelo);
            veiculoSelecionado.setPlaca(placa);

            // Atualiza o veículo no banco de dados
            veiculoDAO.updateVeiculo(veiculoSelecionado);

            // Limpa os campos de entrada
            clearFields();

            // Atualiza a tabela para refletir as mudanças
            updateTable();
        } else {
            showAlert("Erro", "Nenhum veículo selecionado para salvar alterações.");
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
    
    private void exibirDetalhesDoVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            modeloDetalhes.setText(veiculo.getModelo());
            placaDetalhes.setText(veiculo.getPlaca());

            // Buscar o nome do dono do veículo
            ClienteDAO clienteDAO = new ClienteDAO();
            String nomeDono = clienteDAO.getClienteNomeById(veiculo.getClienteId());
            donoDetalhes.setText(nomeDono != null ? nomeDono : "Não encontrado");
        } else {
            modeloDetalhes.setText("");
            placaDetalhes.setText("");
            donoDetalhes.setText("");
        }
    }
    
    // Método para atualizar a tabela com a lista de veículos
    private void updateTable() {
        ObservableList<Veiculo> veiculos = FXCollections.observableArrayList(veiculoDAO.getVeiculos());
        tableViewVeiculos.setItems(veiculos);
    }
    
 // Limpa os campos após salvar as alterações
    private void clearFields() {
        modeloField.clear();
        placaField.clear();
    }

    // Exibe uma janela de alerta
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para buscar veículos por modelo ou placa
    @FXML
    private void searchVeiculo() {
        String searchTerm = searchFieldVeiculo.getText();
        ObservableList<Veiculo> veiculos = FXCollections.observableArrayList(veiculoDAO.searchVeiculos(searchTerm));
        tableViewVeiculos.setItems(veiculos);
    }
}


