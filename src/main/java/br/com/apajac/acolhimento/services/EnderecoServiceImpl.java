package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import br.com.apajac.acolhimento.domain.entities.EnderecoEntity;
import br.com.apajac.acolhimento.services.interfaces.EnderecoService;
import br.com.apajac.acolhimento.repositories.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {
    private final EnderecoRepository erepository;

    public EnderecoServiceImpl(EnderecoRepository erepository) {
        this.erepository = erepository;
    }

    @Override
    public void cadastrarEndereco(EnderecoDTO endereco) {
        EnderecoEntity entity = new EnderecoEntity();
        BeanUtils.copyProperties(endereco, entity);
        erepository.save(entity);
    }

    @Override
    public EnderecoDTO buscarEnderecoCEP(String cep) {
        return null;
    }
}
