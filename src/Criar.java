import java.io.*;
public class Criar{
    public static void main(String[] args) throws IOException {
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
    }
}