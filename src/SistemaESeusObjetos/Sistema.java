package SistemaESeusObjetos;

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class Sistema implements Serializable {
    private List<Medico> medicos = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();

    public void carregarMedicos(String caminho) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(caminho));
        scanner.useDelimiter(";");

         if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            String[] campos = linha.split(";");
            String nome = campos[0];
            int codigo = Integer.parseInt(campos[1]);
            Medico medico = new Medico(nome, codigo);
            medicos.add(medico);
        }
        scanner.close();
    }


    public void carregarPacientes(String caminho) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(caminho));
        scanner.useDelimiter(";");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            String[] campos = linha.split(";");
            String nome = campos[0];
            String cpf = campos[1];
            Paciente paciente = new Paciente(nome, cpf);
            pacientes.add(paciente);
        }
        scanner.close();
    }

    public void carregarConsultas(String caminho) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(caminho));
        scanner.useDelimiter(";");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            String[] campos = linha.split(";");
            LocalDate data = LocalDate.parse(campos[0], dateFormatter);
            LocalTime horario = LocalTime.parse(campos[1]);
            int codigoMedico = Integer.parseInt(campos[2]);
            String cpfPaciente = campos[3];

            Medico medico = medicos.stream()
                    .filter(m -> m.getCodigo() == codigoMedico)
                    .findFirst()
                    .orElse(null);

            Paciente paciente = pacientes.stream()
                    .filter(p -> p.getCpf().equals(cpfPaciente))
                    .findFirst()
                    .orElse(null);

            if (medico != null && paciente != null) {
                Consulta consulta = new Consulta(data, horario, medico, paciente);
                medico.adicionarPaciente(paciente);
                paciente.adicionarConsulta(consulta);
                consultas.add(consulta);
            }
        }
        scanner.close();
    }


    public List<Paciente> getPacientesDoMedico(int codigoMedico) {
        Medico medico = medicos.stream()
                .filter(m -> m.getCodigo() == codigoMedico)
                .findFirst()
                .orElse(null);

        if (medico != null) {
            return medico.getPacientes();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Consulta> getConsultasDoMedicoEmPeriodo(int codigoMedico, LocalDate dataInicial, LocalDate dataFinal) {
        List<Consulta> resultado = new ArrayList<>();

        for (Consulta c : consultas) {
            if (c.getMedico().getCodigo() == codigoMedico
                    && !c.getData().isBefore(dataInicial)
                    && !c.getData().isAfter(dataFinal)) {
                resultado.add(c);
            }
        }

        resultado.sort((c1, c2) -> c1.getHorario().compareTo(c2.getHorario()));
        return resultado;
    }

    public List<Medico> getMedicosDoPaciente(String cpfPaciente) {
        List<Medico> resultado = new ArrayList<>();

        for (Consulta c : consultas) {
            if (c.getPaciente().getCpf().equals(cpfPaciente)) {
                Medico medico = c.getMedico();
                if (!resultado.contains(medico)) {
                    resultado.add(medico);
                }
            }
        }

        return resultado;
    }

    public List<Consulta> getConsultasPacienteMedico(String cpfPaciente, int codigoMedico) {
        List<Consulta> resultado = new ArrayList<>();

        for (Consulta c : consultas) {
            if (c.getPaciente().getCpf().equals(cpfPaciente)
                    && c.getMedico().getCodigo() == codigoMedico) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    public List<Consulta> getConsultasAgendadas(String cpfPaciente) {
        LocalDate hoje = LocalDate.now();
        List<Consulta> resultado = new ArrayList<>();

        for (Consulta c : consultas) {
            if (c.getPaciente().getCpf().equals(cpfPaciente)
                    && c.getData().isAfter(hoje)) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    public List<Paciente> getPacientesInativos(int codigoMedico, int meses) {
        LocalDate limite = LocalDate.now().minusMonths(meses);

        List<Paciente> resultado = new ArrayList<>();

        for (Paciente paciente : pacientes) {
            boolean consultou = false;
            for (Consulta consulta : paciente.getConsultas()) {
                if (consulta.getMedico().getCodigo() == codigoMedico
                        && consulta.getData().isAfter(limite)) {
                    consultou = true;
                    break;
                }
            }
            if (!consultou) {
                resultado.add(paciente);
            }
        }

        return resultado;
    }
    public void salvar(String nome_arquivo) throws IOException {
        FileOutputStream arquivo = new FileOutputStream(nome_arquivo);
        ObjectOutputStream gravador = new ObjectOutputStream(arquivo);

        gravador.writeObject(this);

        gravador.close();
        arquivo.close();
    }
    public static Sistema abrir(String nome_arquivo) throws IOException, ClassNotFoundException {
        Sistema sistema = null;

        FileInputStream arquivo = new FileInputStream(nome_arquivo);
        ObjectInputStream restaurador = new ObjectInputStream(arquivo);

        sistema = (Sistema) restaurador.readObject();

        restaurador.close();
        arquivo.close();

        return sistema;
    }
}
