package med.voll.api.controller;

import med.voll.api.paciente.DatosRegistroPaciente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @PostMapping
    public void registrarPaciente(@RequestBody DatosRegistroPaciente datosRegistroPaciente) {
        System.out.println("El request de paciente llega correctamente.");
        System.out.println(datosRegistroPaciente);
    }
}
