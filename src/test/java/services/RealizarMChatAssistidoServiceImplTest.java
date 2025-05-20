package services;

import com.apajac.acolhimento.domain.dtos.*;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.Endereco;
import com.apajac.acolhimento.domain.entities.MChatEntity;
import com.apajac.acolhimento.domain.enums.TipoParentesco;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.repositories.MChatRepository;
import com.apajac.acolhimento.repositories.RespostaMChatRepository;
import com.apajac.acolhimento.services.RealizarMChatAssistidoServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class RealizarMChatAssistidoServiceImplTest {

    //Mokar e injetar

    @Mock
    private AssistidoRepository assistidoRepository;

    @Mock
    private MChatRepository mChatRepository;

    @Mock
    private RespostaMChatRepository respostaMChatRepository;

    @InjectMocks
    private RealizarMChatAssistidoServiceImpl realizarMChatAssistidoService;

    //Teste de cadastro

    @Test
    void realizarMChatAssistido_DeveSalvarNovoMChat() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        AssistidoEntity assistidoEntity = createAssistidoEntity(assistidoDTO);
        MChatDTO mChatDTO = createMChatDTO();

        when(assistidoRepository.findById(mChatDTO.getId())).thenReturn(Optional.of(assistidoEntity));

        realizarMChatAssistidoService.realizarMChatAssistido(mChatDTO);

        verify(mChatRepository).save(any(MChatEntity.class));
        verify(respostaMChatRepository).saveAll(anyList());
    }

    //Teste de ID nulo

    @Test
    void realizarMChatAssistido_DTOInvalidoBusinessEx() {
        MChatDTO mChatDTO = createMChatDTO();

        mChatDTO.setId(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> realizarMChatAssistidoService.realizarMChatAssistido(mChatDTO));
        assertEquals("M-Chat precisa ser associado a um assistido", exception.getMessage());

    }

    //Teste de 404

    @Test
    void realizarMChatAssistido_AssistidoInvalidoNFoundEx() {
        MChatDTO mChatDTO = createMChatDTO();

        NotFoundException exception = assertThrows(NotFoundException.class, () -> realizarMChatAssistidoService.realizarMChatAssistido(mChatDTO));
        assertEquals("Assistido não encontrado!", exception.getMessage());
    }

    //Gerador dinâmico de DTO do M-Chat
    private MChatDTO createMChatDTO() {
        MChatDTO mChatDTO = new MChatDTO();
        List<RespostaMChatDTO> respostaMChatDTOS = new ArrayList<>();
        mChatDTO.setId(1L);
        int soma = 0;
        for (int i = 1; i <= 20; i++) {
            Random random = new Random();
            RespostaMChatDTO respostaMChatDTO = new RespostaMChatDTO();
            respostaMChatDTO.setPergunta(i);
            respostaMChatDTO.setResposta(random.nextBoolean());
            respostaMChatDTOS.add(respostaMChatDTO);
            if (respostaMChatDTO.getResposta()) {
                soma++;
            }
        }
        if (soma > 10) {
            mChatDTO.setPea(true);
        }
        mChatDTO.setMchat(respostaMChatDTOS);
        return mChatDTO;
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