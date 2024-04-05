package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.domain.entities.ComposicaoFamiliarEntity;
import br.com.apajac.acolhimento.repositories.ComposicaoFamiliarRepository;
import br.com.apajac.acolhimento.services.interfaces.ComposicaoFamiliarService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ComposicaoFamiliarServiceImpl implements ComposicaoFamiliarService {

    private final ComposicaoFamiliarRepository cRepository;

    public ComposicaoFamiliarServiceImpl(ComposicaoFamiliarRepository cRepository) {
        this.cRepository = cRepository;
    }

    @Override
    public void cadastrarComposicaoFamiliar(ComposicaoFamiliarDTO composicaoFamiliar) {
        ComposicaoFamiliarEntity entity = new ComposicaoFamiliarEntity();
        BeanUtils.copyProperties(composicaoFamiliar, entity);
        cRepository.save(entity);
    }
}