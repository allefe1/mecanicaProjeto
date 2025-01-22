package models;

public class Veiculo {
    private int id;  // ID do veículo
    private String modelo;
    private String placa;
    private int clienteId;  // ID do cliente associado ao veículo

 
    public Veiculo(String modelo, String placa, int clienteId) {
        this.modelo = modelo;
        this.placa = placa;
        this.clienteId = clienteId;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}