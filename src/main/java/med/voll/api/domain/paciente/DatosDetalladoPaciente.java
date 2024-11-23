package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosDetalladoPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documentoIdentidad,
        DatosDireccion direccion) {
}
