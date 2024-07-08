package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import com.apajac.acolhimento.services.interfaces.PersistirDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.isNull;


@Service
@RequiredArgsConstructor
@Transactional
public class PersistirDoadorServiceImpl implements PersistirDoadorService {

    private final DoadorRepository doadorRepository;

    private final AuditoriaService auditoria;

    @Override
    public void persistirDoador(DoadorDTO doadorDTO) {
        try {
            DoadorEntity doadorEntity = inserirDoador(doadorDTO);
        } catch (Exception e) {
            throw new BusinessException(format("Não foi possivel cadastrar doador. %s", e.getMessage()));
        }
    }

    // metodo para insetir o dto que chega, seja criando um novo ou atualizando um existente
    private DoadorEntity inserirDoador(DoadorDTO doadorDTO) {
        if (isNull(doadorDTO.getId())){
            validador(doadorDTO);
            return createDoador(doadorDTO);
        }
        validador(doadorDTO);
        return updateDoador(doadorDTO);

    }

    //metodo para cadastrar o doador
    private DoadorEntity createDoador(DoadorDTO doadorDTO) {
        DoadorEntity doadorEntity = new DoadorEntity();
        mapearDtoparaEntidade(doadorEntity, doadorDTO);
        auditar(doadorEntity.toString(), doadorDTO.getIdResponsavelPeloCadastro(), AuditoriaEnum.CREATED.getValues());

        return doadorRepository.save(doadorEntity);

    }

    // se o doador ja existe, ele atualiza apenas
    private DoadorEntity updateDoador(DoadorDTO doadorDTO) {
        Optional<DoadorEntity> optionalDoador = doadorRepository.findById(doadorDTO.getId());
        if (optionalDoador.isEmpty()) {
            throw new NotFoundException("Doador não encontrado.");
        }
        DoadorEntity doadorEntity = optionalDoador.get();
        mapearDtoparaEntidade(doadorEntity, doadorDTO);

        auditar(doadorEntity.toString(), doadorDTO.getIdResponsavelPeloCadastro(), AuditoriaEnum.UPDATED.getValues());
        return doadorRepository.save(doadorEntity);
    }

    // metodo que verifica o usuario que esta cadastrando / atualizando o doador
    private void auditar(String body, Long idResponsavel, String tipo) {
        auditoria.inserirDadosDeAuditoria(
                idResponsavel,
                tipo,
                PersistirDoadorService.class.getSimpleName(),
                body);
    }

    // mapeia o DTO para transformar na entidade
    private DoadorEntity mapearDtoparaEntidade(DoadorEntity doadorEntity,DoadorDTO doadorDTO) {
        doadorEntity.setNome(doadorDTO.getNome());
        doadorEntity.setDocumento(doadorDTO.getDocumento());
        doadorEntity.setValor(doadorDTO.getValor());
        doadorEntity.setTipoDoador(doadorDTO.getTipoDoador());
        doadorEntity.setComoConheceu(doadorDTO.getComoConheceu());
        doadorEntity.setIdResponsavelPeloCadastro(doadorDTO.getIdResponsavelPeloCadastro());
        return doadorEntity;
    }

    // valida se os campos são null ou vazios
    private void validador(DoadorDTO doadorDTO){
        if(doadorDTO.getNome() == null || doadorDTO.getNome().isEmpty()){
            throw new BusinessException("O nome não pode ser nulo");
        }
        if(doadorDTO.getDocumento() == null || doadorDTO.getDocumento().isEmpty()){
            throw new BusinessException("O documento não pode ser nulo");
        }
        if(doadorDTO.getValor() == null){
            throw new BusinessException("O valor não pode ser nulo");
        }
        if(doadorDTO.getTipoDoador() == null){
            throw new BusinessException("O tipo de doador não pode ser nulo");
        }
    }
}

