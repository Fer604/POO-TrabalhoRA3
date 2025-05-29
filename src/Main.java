
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Scanner sc = new Scanner(System.in);

        String caminhoMedicos = "src/medicos.csv";
        String caminhoPacientes = "src/pacientes.csv";
        String caminhoConsultas = "src/consultas.csv";

        try {
            sistema.carregarMedicos(caminhoMedicos);
            sistema.carregarPacientes(caminhoPacientes);
            sistema.carregarConsultas(caminhoConsultas);
        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo csv não encontrado.");
            return;
        }


        while (true) {
            System.out.println("\nEscolha a interface:");
            System.out.println("(1) Interface do Médico");
            System.out.println("(2) Interface do Paciente");
            System.out.println("(9) Sair");
            System.out.print("Opção: ");
            int escolha = sc.nextInt();

            if (escolha == 9) break;

            switch (escolha) {
                case 1:
                    new InterfaceMedico(sc, sistema).exibirMenu();
                    break;
                case 2:
                    new InterfacePaciente(sc, sistema).exibirMenu();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
        System.out.println("Programa encerrado.");
    }
}
