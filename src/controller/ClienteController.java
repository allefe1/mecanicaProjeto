package controller;

import dao.ClienteDAO;
import models.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClienteController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField telefoneField;
    @FXML
    private TextField enderecoField;
    @FXML
    private TextField searchFieldCliente;
    @FXML
    private Button addButton;
    @FXML
    private TableView<Cliente> table;
    @FXML
    private TableColumn<Cliente, String> nomeColumn;
    @FXML
    private TableColumn<Cliente, String> telefoneColumn;

    private ClienteDAO clienteDAO;
    private VeiculoController veiculoController; // Referência ao VeiculoController

    public ClienteController() {
        clienteDAO = new ClienteDAO();
    }

    // Inicialização do controlador
    @FXML
    public void initialize() {
        if (nomeField == null || telefoneField == null || enderecoField == null || table == null) {
            System.err.println("Erro: Certifique-se de que todos os componentes FXML estão devidamente configurados.");
            return;
        }

        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        telefoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));

        // Listener para seleção de cliente na tabela
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cliente>() {
            @Override
            public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue, Cliente newValue) {
                if (newValue != null) {
                    // Atualizar automaticamente o clienteIdField no VeiculoController
                    if (veiculoController != null) {
                        veiculoController.setClienteId(newValue.getId());
                    }
                }
            }
        });

        updateTable();
    }
    
    public Cliente getClienteSelecionado() {
        return table.getSelectionModel().getSelectedItem();
    }

    // Adicionar cliente
    @FXML
    private void addCliente() {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String endereco = enderecoField.getText();

        if (nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        Cliente cliente = new Cliente(nome, telefone, endereco);
        clienteDAO.addCliente(cliente);
        clearFields();
        updateTable();
    }

    // Remover cliente
    @FXML
    private void removeCliente() {
        Cliente clienteSelecionado = table.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            clienteDAO.removeCliente(clienteSelecionado.getId());
            updateTable();
        } else {
            showAlert("Erro", "Nenhum cliente selecionado para remoção.");
        }
    }

    // Editar cliente (preenche os campos)
    @FXML
    private void editCliente() {
        Cliente clienteSelecionado = table.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            nomeField.setText(clienteSelecionado.getNome());
            telefoneField.setText(clienteSelecionado.getTelefone());
            enderecoField.setText(clienteSelecionado.getEndereco());
        } else {
            showAlert("Erro", "Nenhum cliente selecionado para edição.");
        }
    }

    // Salvar alterações no cliente
    @FXML
    private void salvarAtualizacao() {
        Cliente clienteSelecionado = table.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String endereco = enderecoField.getText();

            if (nome.isEmpty() || telefone.isEmpty() || endereco.isEmpty()) {
                showAlert("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            clienteSelecionado.setNome(nome);
            clienteSelecionado.setTelefone(telefone);
            clienteSelecionado.setEndereco(endereco);

            clienteDAO.updateCliente(clienteSelecionado);
            clearFields();
            updateTable();
        } else {
            showAlert("Erro", "Nenhum cliente selecionado para salvar alterações.");
        }
    }

    // Buscar cliente
    @FXML
    private void searchCliente() {
        String query = searchFieldCliente.getText().trim().toLowerCase();

        if (query.isEmpty()) {
            updateTable();
            return;
        }

        ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();
        for (Cliente cliente : clienteDAO.getClientes()) {
            if (cliente.getNome().toLowerCase().contains(query) ||
                cliente.getTelefone().toLowerCase().contains(query) ||
                cliente.getEndereco().toLowerCase().contains(query)) {
                clientesFiltrados.add(cliente);
            }
        }

        table.setItems(clientesFiltrados);
    }

    // Atualizar tabela
    private void updateTable() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.getClientes());
        table.setItems(clientes);
    }

    // Limpar campos de entrada
    private void clearFields() {
        nomeField.clear();
        telefoneField.clear();
        enderecoField.clear();
    }

    // Definir a referência para o VeiculoController
    public void setVeiculoController(VeiculoController veiculoController) {
        this.veiculoController = veiculoController;
    }

    // Exibir alertas
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
