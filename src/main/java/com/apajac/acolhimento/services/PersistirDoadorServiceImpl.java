package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import com.apajac.acolhimento.services.interfaces.PersistirDoadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
        try{
            DoadorEntity doadorEntity = createDoador(doadorDTO);
        } catch (Exception e) {
            throw new BusinessException(format("Não foi possivel cadastrar doador. %s", e.getMessage()));
        }
    }

    //metodo para cadastrar o doador
    private DoadorEntity createDoador(DoadorDTO doadorDTO) {
        if(isNull(doadorDTO.getId())) {
            DoadorEntity doadorEntity = new DoadorEntity();
            mapearDtoparaEntidade(doadorEntity, doadorDTO);
            auditar(doadorEntity.toString(), doadorDTO.getIdResponsavelPeloCadastro(), AuditoriaEnum.CREATED.getValues());

            return doadorRepository.save(doadorEntity);
        }
        else{
            throw new BusinessException("O ID não deve ser fornecido para um novo cadastro.");
        }
    }

    // metodo que verifica o usuario que esta cadastrando
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

}

