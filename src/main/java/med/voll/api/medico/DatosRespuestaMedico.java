package med.voll.api.medico;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
//        String email,
//        String telefono,
        String documento,
        med.voll.api.direccion.Direccion direccion
) {
    }

