package models;

public class Servico {
    private String descricao;
    private double preco;
    private String status; // Pode ser "pendente", "em andamento", "concluído"

    // Construtor
    public Servico(String descricao, double preco, String status) {
        this.descricao = descricao;
        this.preco = preco;
        this.status = status;
    }

    // Getters e Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Serviço{" +
                "descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", status='" + status + '\'' +
                '}';
    }
}