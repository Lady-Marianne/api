package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente) {
        System.out.println("El request de paciente llega correctamente.");
        System.out.println(datosRegistroPaciente);
        pacienteRepository.save(new Paciente(datosRegistroPaciente));

    }

    @GetMapping
    public PagedModel<EntityModel<DatosListadoPaciente>> listadoPacientes(
            @PageableDefault(size = 2) Pageable paginacion,
            PagedResourcesAssembler<DatosListadoPaciente> assembler) {
//        Page<DatosListadoPaciente> paginaPaciente = pacienteRepository.findAll(paginacion)
        Page<DatosListadoPaciente> paginaPaciente = pacienteRepository.findAByActivoTrue(paginacion)
                .map(DatosListadoPaciente::new);
        return assembler.toModel(paginaPaciente);
    }

    @PutMapping
    @Transactional
    public void actualizarPaciente (@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente) {
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
    }
}
