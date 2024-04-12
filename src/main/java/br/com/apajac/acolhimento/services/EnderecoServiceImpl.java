package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import br.com.apajac.acolhimento.domain.entities.EnderecoEntity;
import br.com.apajac.acolhimento.services.interfaces.EnderecoService;
import br.com.apajac.acolhimento.repositories.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {
    private final EnderecoRepository eRepository;

    public EnderecoServiceImpl(EnderecoRepository erepository) {
        this.eRepository = erepository;
    }

    @Override
    public EnderecoEntity cadastrarEndereco(EnderecoDTO endereco) {
        EnderecoEntity entity = new EnderecoEntity();
        BeanUtils.copyProperties(endereco, entity);
        eRepository.save(entity);
        return entity;
    }

}
