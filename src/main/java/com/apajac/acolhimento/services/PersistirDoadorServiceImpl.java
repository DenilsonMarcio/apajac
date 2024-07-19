package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.PersistirDoadorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PersistirDoadorServiceImpl implements PersistirDoadorService {

    private final DoadorRepository doadorRepository;

    public void EditarDoador(DoadorDTO doadorDTO){
        Optional<DoadorEntity> doadorEntityOptional = doadorRepository.findById(doadorDTO.getId());

        if (doadorEntityOptional.isPresent()) {
            DoadorEntity doadorEntity = doadorEntityOptional.get();

            doadorEntity.setNome(doadorDTO.getNome());
            doadorEntity.setDocumento(doadorDTO.getDocumento());
            doadorEntity.setValor(doadorDTO.getValor());
            doadorEntity.setTipoDoador(doadorDTO.getTipoDoador());
            doadorEntity.setComoConheceu(doadorDTO.getComoConheceu());

            doadorRepository.save(doadorEntity);
        } else {
            throw new NotFoundException("Não foi possivel alterar doador.");
        }

    }

}
