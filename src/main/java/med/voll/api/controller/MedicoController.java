package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    public MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        System.out.println("El request de médico llega correctamente.");
        System.out.println(datosRegistroMedico);
        medicoRepository.save(new Medico(datosRegistroMedico));
    }

//    @GetMapping
//    public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
    ////        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
//        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
//    }

    @GetMapping
    public PagedModel<EntityModel<DatosListadoMedico>> listadoMedicos(
            @PageableDefault(size = 2) Pageable paginacion,
            PagedResourcesAssembler<DatosListadoMedico> assembler) {
//        Page<DatosListadoMedico> paginaMedico = medicoRepository.findAll(paginacion)
        Page<DatosListadoMedico> paginaMedico = medicoRepository.findByActivoTrue(paginacion)
                .map(DatosListadoMedico::new);
        return assembler.toModel(paginaMedico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getDocumento(),
//                medico.getEmail(),
//                medico.getTelefono(),
                medico.getDireccion()));
//                medico.getDireccion().getDistrito(),
//                medico.getDireccion().getCiudad(),
//                medico.getDireccion().getNumero(),
//                medico.getDireccion().getComplemento()));
    }

    // DELETE EN BASE DE DATOS:
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }

    //DELETE LÓGICO:
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable @Valid Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

}
