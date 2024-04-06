package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import br.com.apajac.acolhimento.domain.dtos.FamiliarDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import br.com.apajac.acolhimento.domain.entities.ComposicaoFamiliarEntity;
import br.com.apajac.acolhimento.domain.entities.Endereco;
import br.com.apajac.acolhimento.domain.entities.FamiliarEntity;
import br.com.apajac.acolhimento.domain.enums.TipoParentesco;
import br.com.apajac.acolhimento.exceptions.BusinessException;
import br.com.apajac.acolhimento.repositories.AcolhidoRepository;
import br.com.apajac.acolhimento.repositories.ComposicaoFamiliarRepository;
import br.com.apajac.acolhimento.repositories.FamiliarRepository;
import br.com.apajac.acolhimento.services.interfaces.PersistirAcolhidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PersistirPersistirAcolhidoServiceImpl implements PersistirAcolhidoService {

    private final AcolhidoRepository acolhidoRepository;
    private final FamiliarRepository familiarRepository;
    private final ComposicaoFamiliarRepository composicaoFamiliarRepository;

    @Override
    public void persistirAcolhido(AcolhidoDTO acolhidoDTO) {
        try {
            List<FamiliarDTO> familiares = acolhidoDTO.getFamiliares();
            List<ComposicaoFamiliarDTO> composicaoFamiliar = acolhidoDTO.getComposicaoFamiliar();

            AcolhidoEntity acolhido = cadastrarAcolhido(acolhidoDTO);
            cadastrarFamiliares(familiares, acolhido);
            cadastrarComposicaoFamiliar(composicaoFamiliar, acolhido);
        } catch (Exception ex){
            throw new BusinessException(format("Não foi possivel inserir o Acolhido: %s", ex.getMessage()));
        }
    }
    private AcolhidoEntity cadastrarAcolhido(AcolhidoDTO acolhidoDTO) {
        AcolhidoEntity acolhidoEntity = new AcolhidoEntity();
        acolhidoEntity.setNome(acolhidoDTO.getNome());
        acolhidoEntity.setDataNascimento(acolhidoDTO.getDataNascimento());
        acolhidoEntity.setEscolaridade(acolhidoDTO.getEscolaridade());
        acolhidoEntity.setEscola(acolhidoDTO.getEscola());
        acolhidoEntity.setTelEscola(acolhidoDTO.getTelEscola());
        acolhidoEntity.setCadastroInstituicao(acolhidoDTO.isCadastroInstituicao());
        acolhidoEntity.setInstituicao(acolhidoDTO.getInstituicao());
        acolhidoEntity.setEncaminhadoPara(acolhidoDTO.getEncaminhadoPara());
        acolhidoEntity.setQuemIndicouApajac(acolhidoDTO.getQuemIndicouApajac());
        acolhidoEntity.setInformacoesFornecidasPor(acolhidoDTO.getInformacoesFornecidasPor());
        acolhidoEntity.setEndereco(getEndereco(acolhidoDTO.getEndereco()));
        acolhidoEntity.setObservacoes(acolhidoDTO.getObservacoes());

        return acolhidoRepository.save(acolhidoEntity);
    }

    private Endereco getEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoDTO.getCep());
        endereco.setEndereco(enderecoDTO.getEndereco());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setUF(enderecoDTO.getUF());
        return endereco;
    }

    private void cadastrarFamiliares(List<FamiliarDTO> familiarDTOs, AcolhidoEntity acolhido) {
        List<FamiliarEntity> familiares = new ArrayList<>();
        for (FamiliarDTO familiarDTO : familiarDTOs) {
            FamiliarEntity familiarEntity = new FamiliarEntity();
            familiarEntity.setNome(familiarDTO.getNome());
            familiarEntity.setTel(familiarDTO.getTel());
            familiarEntity.setCel(familiarDTO.getCel());
            familiarEntity.setOcupacao(familiarDTO.getOcupacao());
            familiarEntity.setLocalTrabalho(familiarDTO.getLocalTrabalho());
            familiarEntity.setSalario(familiarDTO.getSalario());
            familiarEntity.setVinculoEmpregaticio(familiarDTO.getVinculoEmpregaticio());
            familiarEntity.setTipoParentesco(TipoParentesco.valueOf(familiarDTO.getTipoParentesco()));
            familiarEntity.setAcolhido(acolhido);
            familiares.add(familiarEntity);
        }
        familiarRepository.saveAll(familiares);
    }

    private void cadastrarComposicaoFamiliar(List<ComposicaoFamiliarDTO> composicaoFamiliarDTOs, AcolhidoEntity acolhido) {
        List<ComposicaoFamiliarEntity> composicaoFamiliar = new ArrayList<>();
        for (ComposicaoFamiliarDTO composicaoFamiliarDTO : composicaoFamiliarDTOs) {
            ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
            composicaoFamiliarEntity.setNome(composicaoFamiliarDTO.getNome());
            composicaoFamiliarEntity.setAnoNascimento(composicaoFamiliarDTO.getAnoNascimento());
            composicaoFamiliarEntity.setParentesco(composicaoFamiliarDTO.getParentesco());
            composicaoFamiliarEntity.setOcupacao(composicaoFamiliarDTO.getOcupacao());
            composicaoFamiliarEntity.setAcolhido(acolhido);
            composicaoFamiliar.add(composicaoFamiliarEntity);
        }
        composicaoFamiliarRepository.saveAll(composicaoFamiliar);
    }


}
