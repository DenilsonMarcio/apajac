package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.ParenteDTO;
import br.com.apajac.acolhimento.domain.entities.ParenteEntity;
import br.com.apajac.acolhimento.repositories.ParenteRepository;
import br.com.apajac.acolhimento.services.interfaces.ParenteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ParenteServiceImpl implements ParenteService {

    private final ParenteRepository pRepository;

    public ParenteServiceImpl(ParenteRepository pRepository) {
        this.pRepository = pRepository;
    }

    @Override
    public void cadastrarParente(ParenteDTO parente) {
        ParenteEntity entity = new ParenteEntity();
        BeanUtils.copyProperties(parente, entity);
        pRepository.save(entity);
    }
}