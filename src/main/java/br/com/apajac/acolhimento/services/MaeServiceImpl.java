package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.MaeDTO;
import br.com.apajac.acolhimento.domain.entities.MaeEntity;
import br.com.apajac.acolhimento.repositories.AcolhidoRepository;
import br.com.apajac.acolhimento.repositories.MaeRepository;
import br.com.apajac.acolhimento.services.interfaces.MaeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaeServiceImpl implements MaeService {

    private final MaeRepository mrepository;

    public MaeServiceImpl(MaeRepository repository) {
        this.mrepository = repository;
    }


    @Override
    public void cadastrarMae(MaeDTO mae) {
        MaeEntity entity = new MaeEntity();
        BeanUtils.copyProperties(mae, entity);
        mrepository.save(entity);
    }

    @Override
    public List<MaeEntity> listarMae() {
        return mrepository.findAll();
    }

    @Override
    public MaeDTO buscarMaePorNome(String nome) {
        return null;
    }
}
