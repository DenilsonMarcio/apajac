package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.CarsDTO;
import com.apajac.acolhimento.domain.dtos.RespostaCarsDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.CarsEntity;
import com.apajac.acolhimento.domain.entities.RespostaCarsEntity;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.repositories.CarsRepository;
import com.apajac.acolhimento.repositories.RespostaCarsRepository;
import com.apajac.acolhimento.services.interfaces.RealizarCarsAssistidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RealizarCarsAssistidoServiceImpl implements RealizarCarsAssistidoService {

    private final CarsRepository carsRepository;
    private final RespostaCarsRepository respostaCarsRepository;
    private final AssistidoRepository assistidoRepository;

    @Override
    public void realizarCarsAssistido(CarsDTO carsDTO) {

        validarCarsDTO(carsDTO);
        AssistidoEntity assistido = verificarAssistido(carsDTO);
        persistirCars(assistido, carsDTO);

    }

    private void persistirCars(AssistidoEntity assistido, CarsDTO carsDTO) {
        CarsEntity carsEntity = new CarsEntity();
        carsEntity.setAssistido(assistido);
        carsEntity.setPontuacao(carsDTO.getPontuacao());
        carsEntity.setRealizadoEm(LocalDate.now());
        CarsEntity cars = carsRepository.save(carsEntity);

        List<RespostaCarsEntity> respostaCarsEntities = new ArrayList<>();

        for (RespostaCarsDTO respostaCars: carsDTO.getCars()){
            RespostaCarsEntity respostaCarsEntity = new RespostaCarsEntity();
            respostaCarsEntity.setPergunta(respostaCars.getPergunta());
            respostaCarsEntity.setResposta(respostaCars.getResposta());
            respostaCarsEntity.setObservacoes(respostaCars.getObservacoes());
            respostaCarsEntity.setCars(cars);

            respostaCarsEntities.add(respostaCarsEntity);
        }
        respostaCarsRepository.saveAll(respostaCarsEntities);
    }

    private AssistidoEntity verificarAssistido(CarsDTO carsDTO) {
        Optional<AssistidoEntity> optionalAssistido = assistidoRepository.findById(carsDTO.getId());
        if (optionalAssistido.isEmpty()) {
            throw new NotFoundException("Assistido não encontrado!");
        }
        return optionalAssistido.get();
    }

    private void validarCarsDTO(CarsDTO carsDTO) {
        if (Objects.isNull(carsDTO.getId())) {
            throw new BusinessException("Cars precisa ser associado a um assistido");
        }
    }
}
