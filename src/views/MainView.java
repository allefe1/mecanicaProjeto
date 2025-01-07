package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Oficina - Sistema");

        // Layout
        VBox layout = new VBox(10);
        
        // Botões
        Button btnClientes = new Button("Gerenciar Clientes");
        Button btnVeiculos = new Button("Gerenciar Veículos");

        // Ações dos botões
        btnClientes.setOnAction(e -> openClientesView());
        btnVeiculos.setOnAction(e -> openVeiculosView());

        // Adiciona os botões no layout
        layout.getChildren().addAll(btnClientes, btnVeiculos);

        // Cena
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Abre a tela de gerenciar clientes
    private void openClientesView() {
        ClientesView clientesView = new ClientesView();
        clientesView.start(new Stage()); // Cria a nova janela
    }

    // Abre a tela de gerenciar veículos
    private void openVeiculosView() {
        VeiculosView veiculosView = new VeiculosView();
        veiculosView.start(new Stage()); // Cria a nova janela
    }

    public static void main(String[] args) {
        launch(args);
    }
}