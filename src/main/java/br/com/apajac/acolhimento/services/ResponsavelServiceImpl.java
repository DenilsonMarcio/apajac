package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.ResponsavelDTO;
import br.com.apajac.acolhimento.domain.entities.ResponsavelEntity;
import br.com.apajac.acolhimento.repositories.ResponsavelRepository;
import br.com.apajac.acolhimento.services.interfaces.ResponsavelService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ResponsavelServiceImpl implements ResponsavelService {

    private final ResponsavelRepository rRepository;

    public ResponsavelServiceImpl(ResponsavelRepository rRepository) {
        this.rRepository = rRepository;
    }

    @Override
    public void cadastrarResponsavel(ResponsavelDTO responsavel) {
        ResponsavelEntity entity = new ResponsavelEntity();
        BeanUtils.copyProperties(responsavel, entity);
        rRepository.save(entity);
    }
}
