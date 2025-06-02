import SistemaESeusObjetos.Consulta;
import SistemaESeusObjetos.Medico;
import SistemaESeusObjetos.Sistema;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class InterfacePaciente {
    private Scanner scanner;
    private Sistema sistema;
    private FileWriter writer;
    private boolean salvarEmArquivo;

    public InterfacePaciente(Scanner scanner, Sistema sistema) {
        this.scanner = scanner;
        this.sistema = sistema;
        configurarSaida();
    }

    private void configurarSaida() {
        System.out.println("(1) Exibir resultados na tela");
        System.out.println("(2) Salvar resultados em arquivo .txt");
        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 2) {
            salvarEmArquivo = true;
            System.out.print("Digite o nome do arquivo (com .txt): ");
            String nomeArquivo = scanner.nextLine();
            try {
                writer = new FileWriter("src/" + nomeArquivo);
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo: " + e.getMessage());
                salvarEmArquivo = false;
            }
        }
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("\nInterface do SistemaESeusObjetos.Paciente:");
            System.out.println("(1) Listar médicos que o paciente já consultou");
            System.out.println("(2) Consultas passadas com um médico específico");
            System.out.println("(3) Consultas agendadas no futuro");
            System.out.println("(9) Voltar");
            System.out.print("Opção: ");
            int op = scanner.nextInt();

            if (op == 9) break;

            switch (op) {
                case 1:
                    listarMedicosDoPaciente();
                    break;
                case 2:
                    listarConsultasPassadas();
                    break;
                case 3:
                    listarConsultasFuturas();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar o arquivo.");
            }
        }
    }

    private void listarMedicosDoPaciente() {
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.next();
        List<Medico> medicos = sistema.getMedicosDoPaciente(cpf);
        StringBuilder resultado = new StringBuilder("Médicos consultados:\n");
        for (Medico m : medicos) {
            resultado.append("- ").append(m.getNome()).append("\n");
        }
        imprimirResultado(resultado.toString());
    }

    private void listarConsultasPassadas() {
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.next();
        System.out.print("Digite o código do médico: ");
        int codigo = scanner.nextInt();
        List<Consulta> consultas = sistema.getConsultasPacienteMedico(cpf, codigo);
        StringBuilder resultado = new StringBuilder("Consultas realizadas:\n");
        consultas.stream()
                .filter(c -> c.getData().isBefore(LocalDate.now()))
                .forEach(c -> resultado.append("- ").append(c.getData()).append(" às ").append(c.getHorario()).append("\n"));
        imprimirResultado(resultado.toString());
    }

    private void listarConsultasFuturas() {
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.next();
        List<Consulta> futuras = sistema.getConsultasAgendadas(cpf);
        StringBuilder resultado = new StringBuilder("Consultas futuras:\n");
        for (Consulta c : futuras) {
            resultado.append("- ").append(c.getData()).append(" às ").append(c.getHorario()).append("\n");
        }
        imprimirResultado(resultado.toString());
    }

    private void imprimirResultado(String texto) {
        if (salvarEmArquivo && writer != null) {
            try {
                writer.write(texto + "\n");
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo.");
            }
        } else {
            System.out.print(texto);
        }
    }
}