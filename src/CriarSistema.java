import SistemaESeusObjetos.Sistema;

import java.io.*;

public class CriarSistema {
    public static void main(String[] args) throws IOException {
        Sistema sistema = new Sistema();

        String caminhoMedicos = "src/medicos.csv";
        String caminhoPacientes = "src/pacientes.csv";
        String caminhoConsultas = "src/consultas.csv";


        try {
            sistema.carregarMedicos(caminhoMedicos);
            sistema.carregarPacientes(caminhoPacientes);
            sistema.carregarConsultas(caminhoConsultas);
            System.out.println("Sistema criado com sucesso!");

        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo csv não encontrado.");
            return;
        }
        try {
            sistema.salvar("Sistema.ser");
            System.out.println("Sistema salvado com sucesso!");
        } catch (IOException e) {
            System.out.println("Excecao de I/O");
            e.printStackTrace();
        }
    }
}
