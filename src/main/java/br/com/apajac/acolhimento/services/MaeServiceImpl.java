package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.MaeDTO;
import br.com.apajac.acolhimento.domain.entities.MaeEntity;
import br.com.apajac.acolhimento.repositories.MaeRepository;
import br.com.apajac.acolhimento.services.interfaces.MaeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MaeServiceImpl implements MaeService {

    private final MaeRepository mRepository;

    public MaeServiceImpl(MaeRepository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public MaeEntity cadastrarMae(MaeDTO mae) {
        MaeEntity entity = new MaeEntity();
        BeanUtils.copyProperties(mae, entity);
        mRepository.save(entity);
        return entity;
    }
}