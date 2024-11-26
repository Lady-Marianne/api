package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoConsulta(
        @NotNull
        Long id,
        @NotNull
        MotivoCancelamiento motivo
) {
}
