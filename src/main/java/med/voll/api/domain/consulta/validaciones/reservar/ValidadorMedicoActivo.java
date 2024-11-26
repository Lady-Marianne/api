package med.voll.api.domain.consulta.validaciones.reservar;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas {

    @Autowired
    public MedicoRepository medicoRepository;

    public void validar(DatosReservaConsulta datos) {
        if(datos.idMedico() == null) {
            return;
        }
        var medicoEstaActivo = medicoRepository.findActivoById(datos.idMedico());
        if (!medicoEstaActivo) {
            throw new ValidacionException("El médico no trabaja más en nuestra clínica. " +
                    "Se le asignará otro especialista.");
        }

    }
}
