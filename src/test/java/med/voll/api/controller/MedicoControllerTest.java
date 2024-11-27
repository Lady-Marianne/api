package med.voll.api.controller;

import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosRegistroMedico> datosRegistroMedicoJson;

    @Autowired
    private JacksonTester<DatosRespuestaMedico> datosRespuestaMedicoJson;

    @MockitoBean
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Debería devolver código http 400 cuando la información no es válida.")
    @WithMockUser
    void registrar_escenario1() throws Exception {
        var response = mvc
                .perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver código http 200 cuando la información es válida.")
    @WithMockUser
    void registrar_escenario2() throws Exception {
        var datosRegistroMedico = new DatosRegistroMedico(
                "Medico",
                "medico@voll.med",
                "61999999999",
                "123456",
                Especialidad.CARDIOLOGIA,
                direccion());

        when(medicoRepository.save(any())).thenReturn(new Medico(datosRegistroMedico));

        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosRegistroMedicoJson.write(datosRegistroMedico).getJson()))
                .andReturn().getResponse();

//        var datosDetalle= new DatosRespuestaMedico(
//                0L,
//                datosRegistroMedico.nombre(),
//                datosRegistroMedico.especialidad().toString(),
//                datosRegistroMedico.email(),
//                datosRegistroMedico.telefono(),
//                datosRegistroMedico.documento(),
//                datosRegistroMedico.direccion()        );
//        var jsonEsperado = datosRespuestaMedicoJson.write(datosDetalle).getJson();

        var jsonEsperado = datosRespuestaMedicoJson.write(
                        new DatosRespuestaMedico(
                                null,
                                datosRegistroMedico.nombre(),
                                datosRegistroMedico.email(),
                                datosRegistroMedico.telefono(),
                                datosRegistroMedico.especialidad().toString(),
                                datosRegistroMedico.direccion()
                        )
                )
                .getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DatosDireccion direccion() {
        return new DatosDireccion(
                "calle ejemplo",
                "distrito",
                "Buenos Aires",
                "123",
                "complemento"
        );
    }
}