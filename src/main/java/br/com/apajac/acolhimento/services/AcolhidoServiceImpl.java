package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import br.com.apajac.acolhimento.services.interfaces.AcolhidoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import br.com.apajac.acolhimento.repositories.AcolhidoRepository;

import java.util.List;


@Service
public class AcolhidoServiceImpl implements AcolhidoService {

    private final AcolhidoRepository aRepository;

    public AcolhidoServiceImpl(AcolhidoRepository repository) {
        this.aRepository = repository;
    }


    @Override
    public AcolhidoEntity cadastrarAcolhido(AcolhidoDTO acolhido) {
        AcolhidoEntity entity = new AcolhidoEntity();
        BeanUtils.copyProperties(acolhido, entity);
        return aRepository.save(entity);
    }

    @Override
    public List<AcolhidoEntity> listarAcolhidos() {
        return aRepository.findAll();
    }

    @Override
    public AcolhidoDTO buscarAcolhidoPorId(Integer id) {
        return null;
    }
}