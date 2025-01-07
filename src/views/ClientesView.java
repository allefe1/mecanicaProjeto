package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientesView extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciar Clientes");

        // Layout
        VBox layout = new VBox(10);

        // Campos para adicionar um cliente
        TextField tfNome = new TextField();
        tfNome.setPromptText("Nome do cliente");
        TextField tfTelefone = new TextField();
        tfTelefone.setPromptText("Telefone");
        TextField tfEndereco = new TextField();
        tfEndereco.setPromptText("Endereço");

        // Botões
        Button btnAdicionar = new Button("Adicionar Cliente");
        Button btnListar = new Button("Listar Clientes");

        // Ação do botão Adicionar
        btnAdicionar.setOnAction(e -> {
            // Adiciona cliente (Aqui você pode fazer a chamada ao DAO para salvar no banco)
            String nome = tfNome.getText();
            String telefone = tfTelefone.getText();
            String endereco = tfEndereco.getText();
            // Lógica de salvar o cliente...
            System.out.println("Cliente adicionado: " + nome);
        });

        // Ação do botão Listar
        btnListar.setOnAction(e -> {
            // Exibe a lista de clientes
            // Você pode usar o DAO para buscar os clientes e exibir em uma lista
            System.out.println("Listando clientes...");
        });

        // Adiciona os componentes no layout
        layout.getChildren().addAll(tfNome, tfTelefone, tfEndereco, btnAdicionar, btnListar);

        // Cena
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}