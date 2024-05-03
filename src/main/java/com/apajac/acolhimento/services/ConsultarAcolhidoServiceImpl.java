package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AcolhidoRepository;
import com.apajac.acolhimento.services.interfaces.ConsultarAcolhidoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultarAcolhidoServiceImpl implements ConsultarAcolhidoService {

    private final AcolhidoRepository acolhidoRepository;
    @Override
    public Page<AcolhidoEntity> listarAcolhidos(Pageable pageable) {
        return acolhidoRepository.findAll(pageable);
    }

    @Override
    public AcolhidoEntity buscarAcolhidoPorId(Long id) {
        Optional<AcolhidoEntity> acolhidoOpt = acolhidoRepository.findById(id);
        if (acolhidoOpt.isEmpty()){
            throw new NotFoundException("Acolhido não encontrado.");
        }
        return acolhidoOpt.get();
    }

    @Override
    public Page<AcolhidoEntity> buscarAcolhidosPorNome(String nome, Pageable pageable) {
        return acolhidoRepository.findAllByNomeContainingIgnoreCase(nome, pageable);
    }
}
