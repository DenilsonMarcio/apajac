package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.CarsDataPontuacaoDTO;
import com.apajac.acolhimento.domain.dtos.DetalhesCarsAssistidoDTO;
import com.apajac.acolhimento.domain.dtos.NomeAssistidoCarsDTO;
import com.apajac.acolhimento.domain.dtos.RespostaCarsDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.CarsEntity;
import com.apajac.acolhimento.domain.entities.RespostaCarsEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.CarsRepository;
import com.apajac.acolhimento.services.interfaces.ListarCarsService;
import com.apajac.acolhimento.utils.AssistidoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListaCarsRealizadoAssistidoServiceImpl implements ListarCarsService {

    private final CarsRepository carsRepository;
    private final AssistidoUtils assistidoUtils;

    @Override
    public NomeAssistidoCarsDTO listarCarsPorAssistido(Long id) {

        NomeAssistidoCarsDTO response = new NomeAssistidoCarsDTO();
        List<CarsDataPontuacaoDTO> datasCars = new ArrayList<>();

        AssistidoEntity assistido = assistidoUtils.verificaAssistido(id);

        List<CarsEntity> listCars = carsRepository.findByAssistidoId(assistido.getId());

        for (CarsEntity cars : listCars){
            CarsDataPontuacaoDTO dto = new CarsDataPontuacaoDTO();
            dto.setId(cars.getId());
            dto.setData(cars.getRealizadoEm());
            dto.setPontuacao(cars.getPontuacao());
            datasCars.add(dto);
        }

        response.setNomeAssistido(assistido.getNome());
        response.setCars(datasCars);

        return response;
    }

    @Override
    public DetalhesCarsAssistidoDTO detalhesCarsPorAssistidoEData(Long id) {

        DetalhesCarsAssistidoDTO response = new DetalhesCarsAssistidoDTO();
        List<RespostaCarsDTO> detalhesCars = new ArrayList<>();

        CarsEntity cars = verificaCars(id);
        AssistidoEntity assistido = assistidoUtils.verificaAssistido(cars.getAssistido().getId());

        List<RespostaCarsEntity> respostas = cars.getRespostas();
        for (RespostaCarsEntity resp : respostas){
            RespostaCarsDTO dto = new RespostaCarsDTO();
            dto.setPergunta(resp.getPergunta());
            dto.setResposta(resp.getResposta());
            dto.setObservacoes(resp.getObservacoes());
            detalhesCars.add(dto);
        }

        response.setNomeAssistido(assistido.getNome());
        response.setData(cars.getRealizadoEm());
        response.setPontuacao(cars.getPontuacao());
        response.setDetalhes(detalhesCars);

        return response;
    }

    private CarsEntity verificaCars(Long id) {
        Optional<CarsEntity> optionalCars = carsRepository.findById(id);
        if (optionalCars.isEmpty()){
            throw new NotFoundException("Cars não encontrado!");
        }
        return optionalCars.get();
    }
}
