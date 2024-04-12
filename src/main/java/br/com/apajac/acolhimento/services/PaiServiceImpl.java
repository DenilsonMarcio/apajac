package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.PaiDTO;
import br.com.apajac.acolhimento.domain.entities.PaiEntity;
import br.com.apajac.acolhimento.repositories.PaiRepository;
import br.com.apajac.acolhimento.services.interfaces.PaiService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PaiServiceImpl implements PaiService {

    private final PaiRepository pRepository;

    public PaiServiceImpl(PaiRepository pRepository) {
        this.pRepository = pRepository;
    }

    @Override
    public PaiEntity cadastrarPai(PaiDTO pai) {
        PaiEntity entity = new PaiEntity();
        BeanUtils.copyProperties(pai, entity);
        pRepository.save(entity);
        return entity;
    }
}