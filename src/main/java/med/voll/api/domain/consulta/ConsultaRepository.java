package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndFechaBetween(
            Long id,
            LocalDateTime primerHorario,
            LocalDateTime ultimoHorario
    );

    boolean existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(Long idMedico, LocalDateTime fecha);
}
