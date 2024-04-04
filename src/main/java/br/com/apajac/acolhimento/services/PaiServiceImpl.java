package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.MaeDTO;
import br.com.apajac.acolhimento.domain.dtos.PaiDTO;
import br.com.apajac.acolhimento.domain.entities.MaeEntity;
import br.com.apajac.acolhimento.domain.entities.PaiEntity;
import br.com.apajac.acolhimento.repositories.MaeRepository;
import br.com.apajac.acolhimento.repositories.PaiRepository;
import br.com.apajac.acolhimento.services.interfaces.PaiService;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class PaiServiceImpl implements PaiService {

    private final PaiRepository prepository;

    public PaiServiceImpl(PaiRepository repository) {
        this.prepository = repository;
    }
    @Override
    public void cadastrarPai(PaiDTO pai) {

        PaiEntity entity = new PaiEntity();
        BeanUtils.copyProperties(pai, entity);
        prepository.save(entity);

    }

    @Override
    public List<PaiEntity> listarPai() {
        return prepository.findAll();
    }

    @Override
    public PaiDTO buscarMaePorNome(String nome) {
        return null;
    }
}
