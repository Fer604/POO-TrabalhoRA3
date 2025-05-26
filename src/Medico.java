import java.util.ArrayList;
import java.util.List;

public class Medico {
    private String nome;
    private int codigo;
    private List<Paciente> pacientes;

    public Medico(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.pacientes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void adicionarPaciente(Paciente paciente) {
        pacientes.add(paciente);

    }
}
