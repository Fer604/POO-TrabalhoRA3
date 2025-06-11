package persistence;

import model.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class P1 {
    public static void main(String[] args) throws IOException, ConsultaInvalidaException{
        List<Medico> medicos = new ArrayList<>();
        List<Paciente> pacientes = new ArrayList<>();
        List<Consulta> consultas = new ArrayList<>();

        List<String> linhasMedicos = Files.readAllLines(Paths.get("src/medicos.csv"));
        for (String linha : linhasMedicos) {
            String[] partes = linha.split(",");
            medicos.add(new Medico(partes[0], partes[1], partes[2]));
        }

        List<String> linhasPacientes = Files.readAllLines(Paths.get("src/pacientes.csv"));
        for (String linha : linhasPacientes) {
            String[] partes = linha.split(",");
            pacientes.add(new Paciente(partes[0], partes[1], Integer.parseInt(partes[2])));
        }

        List<String> linhasConsultas = Files.readAllLines(Paths.get("src/consultas.csv"));
        for (String linha : linhasConsultas) {
            String[] partes = linha.split(",");
            Medico m = buscarMedicoPorCpf(medicos, partes[0]);
            Paciente p = buscarPacientePorCpf(pacientes, partes[1]);
            if (m == null || p == null) {
                throw new ConsultaInvalidaException("Consulta inválida: médico ou paciente não encontrado.");
            }
            consultas.add(new Consulta(m, p, LocalDate.parse(partes[2])));
        }

        salvar("medicos.dat", medicos);
        salvar("pacientes.dat", pacientes);
        salvar("consultas.dat", consultas);

        System.out.println("Objetos salvos com sucesso.");
    }

    private static void salvar(String nome, Object obj) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nome))) {
            oos.writeObject(obj);
        }
    }

    private static Medico buscarMedicoPorCpf(List<Medico> lista, String cpf) {
        return lista.stream().filter(m -> m.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    private static Paciente buscarPacientePorCpf(List<Paciente> lista, String cpf) {
        return lista.stream().filter(p -> p.getCpf().equals(cpf)).findFirst().orElse(null);
    }
}