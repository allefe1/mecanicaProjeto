package application;

import controller.ClienteController;
import controller.VeiculoController;
import dao.DatabaseSQLite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar a view de Clientes
        FXMLLoader loaderCliente = new FXMLLoader(getClass().getResource("/views/ClientesView.fxml"));
        AnchorPane rootCliente = loaderCliente.load();  // Alterado para AnchorPane
        ClienteController clienteController = loaderCliente.getController();

        // Carregar a view de Veículos
        FXMLLoader loaderVeiculo = new FXMLLoader(getClass().getResource("/views/VeiculosView.fxml"));
        AnchorPane rootVeiculo = loaderVeiculo.load();  // Alterado para AnchorPane
        VeiculoController veiculoController = loaderVeiculo.getController();

        // Passar o ClienteController para o VeiculoController
        veiculoController.setClienteController(clienteController);

        // Layout principal (Clientes e Veículos)
        HBox root = new HBox(20, rootCliente, rootVeiculo);  // Divide a tela entre Cliente e Veículo
        Scene scene = new Scene(root, 800, 600);

        // Botão para abrir a tela de Serviços em uma nova janela
        Button openServicoButton = new Button("Abrir Serviços");
        openServicoButton.setOnAction(this::openServicoWindow);

        // Adicionando o botão de abrir serviços no layout principal
        root.getChildren().add(openServicoButton);

        primaryStage.setTitle("Sistema de Oficina");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para abrir a tela de serviços em uma nova janela
    private void openServicoWindow(ActionEvent event) {
        try {
            FXMLLoader loaderServico = new FXMLLoader(getClass().getResource("/views/ServicosView.fxml"));
            AnchorPane rootServico = loaderServico.load();  // Alterado para AnchorPane
            Stage servicoStage = new Stage();
            servicoStage.setTitle("Gerenciamento de Serviços");

            Scene servicoScene = new Scene(rootServico, 600, 400);
            servicoStage.setScene(servicoScene);
            servicoStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseSQLite.createTables();
        launch(args);
    }
}
