package com.apajac.acolhimento.controllers.carsController;

import com.apajac.acolhimento.domain.dtos.CarsDTO;
import com.apajac.acolhimento.domain.dtos.RespostaCarsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Configs para teste do Weblayer
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class RealizarCarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //Teste do cadastro

    @Test
    void realizarCarsAssistidoTest_DeveCadastrarCars() throws Exception {
        CarsDTO carsDTO = createCarsDTO();

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/cars").content((mapper.writeValueAsString(carsDTO))).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    //Teste de ID Nulo

    @Test
    void RealizarCarsAssistido_DeveLancarExceptionQuandoIDNulo() throws Exception {
        CarsDTO carsDTO = createCarsDTO();
        carsDTO.setId(null);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/cars").content((mapper.writeValueAsString(carsDTO))).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable()).andExpect(content().string("{\"message\":\"Cars precisa ser associado a um assistido\"}"));
    }

    //Teste de 404

    @Test
    void RealizarCarsAssistido_DeveLancarExceptionQuandoNaoEncontrarAssistido() throws Exception {
        CarsDTO carsDTO = createCarsDTO();
        carsDTO.setId(0L);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/cars").content((mapper.writeValueAsString(carsDTO))).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andExpect(content().string("{\"message\":\"Assistido nÃ£o encontrado!\"}"));
    }

    //Gerador dinâmico de DTO

    private CarsDTO createCarsDTO() {
        CarsDTO carsDTO = new CarsDTO();
        List<RespostaCarsDTO> respostaCarsDTOS = new ArrayList<>();
        carsDTO.setId(1L);
        carsDTO.setPontuacao(0D);
        for (int i = 1; i <= 15; i++) {
            Random random = new Random();
            RespostaCarsDTO respostaCarsDTO = new RespostaCarsDTO();
            respostaCarsDTO.setPergunta(i);
            respostaCarsDTO.setResposta((Arrays.asList(0, 1.5, 2.5, 3.5).get(random.nextInt(4))).doubleValue());
            respostaCarsDTO.setObservacoes(Arrays.asList("(  0.0)", "( 0.0 )", "(0.0  )").get(random.nextInt(3)));
            carsDTO.setPontuacao(carsDTO.getPontuacao() + respostaCarsDTO.getResposta());
            respostaCarsDTOS.add(respostaCarsDTO);
        }
        carsDTO.setCars(respostaCarsDTOS);
        return carsDTO;
    }
}