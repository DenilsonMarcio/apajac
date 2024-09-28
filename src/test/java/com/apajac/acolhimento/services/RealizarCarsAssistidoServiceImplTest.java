package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.*;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.CarsEntity;
import com.apajac.acolhimento.domain.entities.Endereco;
import com.apajac.acolhimento.domain.enums.TipoParentesco;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.repositories.CarsRepository;
import com.apajac.acolhimento.repositories.RespostaCarsRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class RealizarCarsAssistidoServiceImplTest {

    //Mokar e injetar

    @Mock
    private AssistidoRepository assistidoRepository;

    @Mock
    private CarsRepository carsRepository;

    @Mock
    private RespostaCarsRepository respostaCarsRepository;

    @InjectMocks
    private RealizarCarsAssistidoServiceImpl realizarCarsAssistidoService;

    //Teste de cadastro

    @Test
    void realizarCarsAssistido_DeveSalvarNovoCars() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        AssistidoEntity assistidoEntity = createAssistidoEntity(assistidoDTO);
        CarsDTO carsDTO = createCarsDTO();

        when(assistidoRepository.findById(carsDTO.getId())).thenReturn(Optional.of(assistidoEntity));

        realizarCarsAssistidoService.realizarCarsAssistido(carsDTO);

        verify(carsRepository).save(any(CarsEntity.class));
        verify(respostaCarsRepository).saveAll(anyList());
    }

    //Teste de ID nulo

    @Test
    void realizarCarsAssistido_DTOInvalidoBusinessEx() {
        CarsDTO carsDTO = createCarsDTO();

        carsDTO.setId(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> realizarCarsAssistidoService.realizarCarsAssistido(carsDTO));
        assertEquals("Cars precisa ser associado a um assistido", exception.getMessage());

    }

    //Teste de 404

    @Test
    void realizarCarsAssistido_AssistidoInvalidoNFoundEx() {
        CarsDTO carsDTO = createCarsDTO();

        NotFoundException exception = assertThrows(NotFoundException.class, () -> realizarCarsAssistidoService.realizarCarsAssistido(carsDTO));
        assertEquals("Assistido não encontrado!", exception.getMessage());
    }

    //Gerador dinâmico de DTO do CARS
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

    //Geradores de DTO roubados do Denilson
    private AssistidoDTO createAssistidoDTO() {
        AssistidoDTO assistidoDTO = new AssistidoDTO();
        assistidoDTO.setNome("Assistido Teste");
        assistidoDTO.setDataNascimento(LocalDate.now().minusYears(18));
        assistidoDTO.setEscolaridade("Ensino Médio");
        assistidoDTO.setEscola("Escola Teste");
        assistidoDTO.setTelEscola("123456789");
        assistidoDTO.setCadastroInstituicao(true);
        assistidoDTO.setInstituicao("Instituição Teste");
        assistidoDTO.setEncaminhadoPara("Encaminhamento Teste");
        assistidoDTO.setQuemIndicouApajac("Indicação Teste");
        assistidoDTO.setInformacoesFornecidasPor("Informante Teste");
        assistidoDTO.setEndereco(new EnderecoDTO());
        assistidoDTO.setObservacoes("Observações Teste");
        assistidoDTO.setIdResponsavelPeloCadastro(1L);
        assistidoDTO.setCadastradoEm(LocalDate.now());
        assistidoDTO.setFamiliares(Collections.singletonList(createFamiliarDTO()));
        assistidoDTO.setComposicaoFamiliar(Collections.singletonList(createComposicaoFamiliarDTO()));
        assistidoDTO.setResponsavel(createResponsavelDTO());
        return assistidoDTO;
    }

    private AssistidoEntity createAssistidoEntity(AssistidoDTO dto) {
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(1L);
        assistidoEntity.setNome(dto.getNome());
        assistidoEntity.setDataNascimento(dto.getDataNascimento());
        assistidoEntity.setEscolaridade(dto.getEscolaridade());
        assistidoEntity.setEscola(dto.getEscola());
        assistidoEntity.setTelEscola(dto.getTelEscola());
        assistidoEntity.setCadastroInstituicao(dto.isCadastroInstituicao());
        assistidoEntity.setInstituicao(dto.getInstituicao());
        assistidoEntity.setEncaminhadoPara(dto.getEncaminhadoPara());
        assistidoEntity.setQuemIndicouApajac(dto.getQuemIndicouApajac());
        assistidoEntity.setInformacoesFornecidasPor(dto.getInformacoesFornecidasPor());
        assistidoEntity.setEndereco(new Endereco());
        assistidoEntity.setObservacoes(dto.getObservacoes());
        assistidoEntity.setIdResponsavelPeloCadastro(dto.getIdResponsavelPeloCadastro());
        assistidoEntity.setCadastradoEm(dto.getCadastradoEm());
        return assistidoEntity;
    }

    private FamiliarDTO createFamiliarDTO() {
        FamiliarDTO familiarDTO = new FamiliarDTO();
        familiarDTO.setNome("Familiar Teste");
        familiarDTO.setOcupacao("Ocupação Teste");
        familiarDTO.setLocalTrabalho("Local Trabalho Teste");
        familiarDTO.setSalario(BigDecimal.valueOf(1000));
        familiarDTO.setVinculoEmpregaticio("Autonomo");
        familiarDTO.setTipoParentesco(TipoParentesco.OUTROS.name());
        familiarDTO.setContatos(Collections.singletonList(createContatoDTO()));
        return familiarDTO;
    }

    private ComposicaoFamiliarDTO createComposicaoFamiliarDTO() {
        ComposicaoFamiliarDTO composicaoFamiliarDTO = new ComposicaoFamiliarDTO();
        composicaoFamiliarDTO.setNome("Composição Teste");
        composicaoFamiliarDTO.setAnoNascimento(2005);
        composicaoFamiliarDTO.setParentesco("Primo");
        composicaoFamiliarDTO.setOcupacao("Estudante");
        composicaoFamiliarDTO.setObservacoes("Observações Teste");
        return composicaoFamiliarDTO;
    }

    private ResponsavelDTO createResponsavelDTO() {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO();
        responsavelDTO.setNome("Responsável Teste");
        responsavelDTO.setTipoParentesco(TipoParentesco.MAE.name());
        responsavelDTO.setContatos(Collections.singletonList(createContatoDTO()));
        return responsavelDTO;
    }

    private ContatoDTO createContatoDTO() {
        ContatoDTO contatoDTO = new ContatoDTO();
        contatoDTO.setContato("Contato Teste");
        return contatoDTO;
    }
}