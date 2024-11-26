package med.voll.api.domain.consulta.validaciones.reservar;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos) {
        var medicoTieneOtraConsultaEnElMismoHorario =
                consultaRepository.existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(
             datos.idMedico(), datos.fecha()
        );
        if(medicoTieneOtraConsultaEnElMismoHorario) {
            throw new ValidacionException("El médico ya tiene reservada una consulta en ese horario.");
        }
    }
}
