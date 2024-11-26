package med.voll.api.domain.consulta.validaciones.reservar;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements ValidadorDeConsultas {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DatosReservaConsulta datos) {
        var pacienteEstaActivo = pacienteRepository.findActivoById(datos.idPaciente());
        if (!pacienteEstaActivo) {
            throw new ValidacionException("Un paciente excluído no puede reservar una consulta.");
        }

    }
}
