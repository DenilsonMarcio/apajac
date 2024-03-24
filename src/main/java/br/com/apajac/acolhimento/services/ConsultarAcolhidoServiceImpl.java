package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import br.com.apajac.acolhimento.exceptions.NotFoundException;
import br.com.apajac.acolhimento.repositories.AcolhidoRepository;
import br.com.apajac.acolhimento.services.interfaces.ConsultarAcolhidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultarAcolhidoServiceImpl implements ConsultarAcolhidoService {

    private final AcolhidoRepository acolhidoRepository;
    @Override
    public List<AcolhidoEntity> listarAcolhidos() {
        return acolhidoRepository.findAll();
    }

    @Override
    public AcolhidoEntity buscarAcolhidoPorId(Long id) {
        Optional<AcolhidoEntity> acolhidoOpt = acolhidoRepository.findById(id);
        if (acolhidoOpt.isEmpty()){
            throw new NotFoundException("Acolhido não encontrado.");
        }
        return acolhidoOpt.get();
    }
}
