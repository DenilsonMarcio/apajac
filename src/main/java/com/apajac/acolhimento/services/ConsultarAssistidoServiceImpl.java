package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.relatorio.AssistidoPorBairroDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.ConsultarAssistidoService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarAssistidoServiceImpl implements ConsultarAssistidoService {

    private final AssistidoRepository assistidoRepository;
    @Override
    public Page<AssistidoEntity> listarAssistidos(Pageable pageable) {
        return assistidoRepository.findAll(pageable);
    }

    @Override
    public AssistidoEntity buscarAssistidoPorId(Long id) {
        Optional<AssistidoEntity> assistidoOpt = assistidoRepository.findById(id);
        if (assistidoOpt.isEmpty()){
            throw new NotFoundException("Assistido não encontrado.");
        }
        return assistidoOpt.get();
    }

    @Override
    public Page<AssistidoEntity> buscarAssistidosPorNome(String nome, Pageable pageable) {
        return assistidoRepository.findAllByNomeContainingIgnoreCase(nome, pageable);
    }

    @Override
    public List<Integer> getAnosCadastros() {
        return assistidoRepository.getAnosCadastros();
    }

    @Override
    public List<AssistidoPorBairroDTO> totalAssistidoPorBairro(String bairro) {

        AssistidoPorBairroDTO dto = new AssistidoPorBairroDTO();
        List<AssistidoPorBairroDTO> assistidoPorBairro = new ArrayList<>();

        List<Tuple> tuples = assistidoRepository.totalAssistidosPorBairro(bairro);

        for (Tuple tuple : tuples) {
            dto.setBairro(tuple.get("bairro").toString());
            dto.setTotal_assistidos((Long) tuple.get("total_assistidos"));
            assistidoPorBairro.add(dto);
        }
        return assistidoPorBairro;
    }
}
