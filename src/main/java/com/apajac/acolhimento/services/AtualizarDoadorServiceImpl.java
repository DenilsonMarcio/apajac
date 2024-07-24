package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AtualizarDoadorServiceImpl implements AtualizarDoadorService {

    private final DoadorRepository doadorRepository;

    @Override
    public void updateDoador(Long id, DoadorDTO doadorDTO) {
        Optional<DoadorEntity> optionalDoador = doadorRepository.findById(id);
        if (optionalDoador.isEmpty()) {
            throw new NotFoundException("Doador não encontrado.");
        }
        DoadorEntity entity = optionalDoador.get();
        entity.setNome(doadorDTO.getNome());
        entity.setDocumento(doadorDTO.getDocumento());
        entity.setValor(doadorDTO.getValor());
        entity.setTipo_doador(doadorDTO.getTipo_doador());
        entity.setComo_conheceu(doadorDTO.getComo_conheceu());
        doadorRepository.save(entity);
    }
}
