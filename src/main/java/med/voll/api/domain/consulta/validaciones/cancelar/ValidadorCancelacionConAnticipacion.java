package med.voll.api.domain.consulta.validaciones.cancelar;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorCancelacionConAnticipacion implements ValidadorCancelamientoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelamientoConsulta datos) {
        var consulta = consultaRepository.getReferenceById(datos.id());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24) {
            throw new ValidacionException("¡La consulta solo puede ser cancelada " +
                    "con anticipación mínima de 24 horas!");
        }
    }
}
