import java.io.IOException;
import java.util.Scanner;

public class RecuperarEOperarSistema {
    public static void main(String[] args) {
        //inicia o objeto sistema fora do try e um novo scanner(ñ vejo motivo pra salvar scanner)
        Scanner sc = new Scanner(System.in);
        Sistema sistema = null;
        try {
            sistema = Sistema.abrir("Champions.ser");
            System.out.println("Sistema recuperado com sucesso!");
        } catch (IOException e) {
            System.out.println("Excecao de I/O");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Excecao de classe desconhecida");
            e.printStackTrace();
        }

        if (sistema == null) {
            System.out.println("Erro ao recuperar o sistema. Encerrando o programa.");
            sc.close();
            return;
        }
        //main antiga
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