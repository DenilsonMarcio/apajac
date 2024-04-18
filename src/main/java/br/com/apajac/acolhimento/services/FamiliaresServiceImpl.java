package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import br.com.apajac.acolhimento.domain.entities.FamiliaresEntity;
import br.com.apajac.acolhimento.repositories.FamiliaresRepository;
import br.com.apajac.acolhimento.services.interfaces.FamiliaresService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FamiliaresServiceImpl implements FamiliaresService {

    private final FamiliaresRepository fRepository;

    public FamiliaresServiceImpl(FamiliaresRepository fRepository) {
        this.fRepository = fRepository;
    }

    @Override
    public FamiliaresEntity cadastrarFamiliar(FamiliaresDTO familiares) {

        FamiliaresEntity entity = new FamiliaresEntity();
        BeanUtils.copyProperties(familiares, entity);
        fRepository.save(entity);
        return entity;
    }
}
