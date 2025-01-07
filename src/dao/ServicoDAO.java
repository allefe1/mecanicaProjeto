package dao;

import models.Servico;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    private List<Servico> servicos;

    public ServicoDAO() {
        this.servicos = new ArrayList<>();
    }

    // Criar (Adicionar Serviço)
    public void addServico(Servico servico) {
        servicos.add(servico);
    }

    // Ler (Obter todos os Serviços)
    public List<Servico> getServicos() {
        return servicos;
    }

    // Atualizar (Alterar os dados de um Serviço)
    public void atualizarServico(int indice, Servico servicoAtualizado) {
        if (indice >= 0 && indice < servicos.size()) {
            servicos.set(indice, servicoAtualizado);
        }
    }

    // Deletar (Remover um Serviço)
    public void removerServico(int indice) {
        if (indice >= 0 && indice < servicos.size()) {
            servicos.remove(indice);
        }
    }
}