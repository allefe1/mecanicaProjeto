package application;

import dao.DatabaseSQLite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import views.ClientesView;
import views.VeiculosView;
import controller.VeiculoController;
import controller.ClienteController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar a view de Clientes
        FXMLLoader loaderCliente = new FXMLLoader(getClass().getResource("/views/ClientesView.fxml"));
        AnchorPane rootCliente = loaderCliente.load();
        ClienteController clienteController = loaderCliente.getController();

        // Carregar a view de Veículos
        FXMLLoader loaderVeiculo = new FXMLLoader(getClass().getResource("/views/VeiculosView.fxml"));
        AnchorPane rootVeiculo = loaderVeiculo.load();
        VeiculoController veiculoController = loaderVeiculo.getController();

        // Passar a referência do clienteController para o veiculoController
        veiculoController.setClienteController(clienteController);

        // Layout principal
        HBox root = new HBox(20, rootCliente, rootVeiculo);  // Divide a tela entre Cliente e Veículo
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Sistema de Oficina");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        DatabaseSQLite.createTables();
        launch(args);
    }
}