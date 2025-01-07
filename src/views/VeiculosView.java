package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VeiculosView extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciar Veículos");

        // Layout
        VBox layout = new VBox(10);

        // Campos para adicionar um veículo
        TextField tfModelo = new TextField();
        tfModelo.setPromptText("Modelo do veículo");
        TextField tfPlaca = new TextField();
        tfPlaca.setPromptText("Placa do veículo");
        TextField tfClienteId = new TextField();
        tfClienteId.setPromptText("ID do cliente");

        // Botões
        Button btnAdicionarVeiculo = new Button("Adicionar Veículo");
        Button btnListarVeiculos = new Button("Listar Veículos");

        // Ação do botão Adicionar
        btnAdicionarVeiculo.setOnAction(e -> {
            // Adiciona veículo (Aqui você pode chamar o DAO para salvar o veículo)
            String modelo = tfModelo.getText();
            String placa = tfPlaca.getText();
            int clienteId = Integer.parseInt(tfClienteId.getText());
            // Lógica de salvar o veículo...
            System.out.println("Veículo adicionado: " + modelo);
        });

        // Ação do botão Listar
        btnListarVeiculos.setOnAction(e -> {
            // Exibe a lista de veículos
            // Você pode usar o DAO para buscar os veículos e exibir em uma lista
            System.out.println("Listando veículos...");
        });

        // Adiciona os componentes no layout
        layout.getChildren().addAll(tfModelo, tfPlaca, tfClienteId, btnAdicionarVeiculo, btnListarVeiculos);

        // Cena
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}