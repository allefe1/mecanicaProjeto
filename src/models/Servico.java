package models;

public class Servico {
    private int id;
    private String descricao;
    private double preco;
    private String data;
    private int veiculoId;

    public Servico(int id, String descricao, double preco, String data, int veiculoId) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.data = data;
        this.veiculoId = veiculoId;
    }
    
    public Servico(int id, String descricao, double preco, int veiculoId) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.veiculoId = veiculoId;
    }

    public Servico(String descricao, double preco, String data, int veiculoId) {
        this.descricao = descricao;
        this.preco = preco;
        this.data = data;
        this.veiculoId = veiculoId;
    }

    public int getId() {
        return id;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(int veiculoId) {
        this.veiculoId = veiculoId;
    }
}