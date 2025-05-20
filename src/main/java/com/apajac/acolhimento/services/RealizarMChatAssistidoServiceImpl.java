package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.MChatDTO;
import com.apajac.acolhimento.domain.dtos.RespostaMChatDTO;
import com.apajac.acolhimento.domain.entities.*;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.*;
import com.apajac.acolhimento.services.interfaces.RealizarMChatAssistidoService;
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
public class RealizarMChatAssistidoServiceImpl implements RealizarMChatAssistidoService {

    private final MChatRepository mChatRepository;
    private final RespostaMChatRepository respostaMChatRepository;
    private final AssistidoRepository assistidoRepository;

    @Override
    public void realizarMChatAssistido(MChatDTO mChatDTO) {
        validarMChatDTO(mChatDTO);
        AssistidoEntity assistido = verificarAssistido(mChatDTO);
        persistirMChat(assistido, mChatDTO);
    }

    private void persistirMChat(AssistidoEntity assistido, MChatDTO mChatDTO) {
        MChatEntity mChatEntity = new MChatEntity();
        mChatEntity.setAssistido(assistido);
        mChatEntity.setPea(mChatDTO.getPea());
        mChatEntity.setRealizadoEm(LocalDate.now());
        MChatEntity mchat = mChatRepository.save(mChatEntity);

        List<RespostaMChatEntity> respostaMChatEntities = new ArrayList<>();

        for (RespostaMChatDTO respostaMChat: mChatDTO.getMchat()){
            RespostaMChatEntity respostaMChatEntity = new RespostaMChatEntity();
            respostaMChatEntity.setPergunta(respostaMChat.getPergunta());
            respostaMChatEntity.setResposta(respostaMChat.getResposta());
            respostaMChatEntity.setMchat(mchat);

            respostaMChatEntities.add(respostaMChatEntity);
        }
        respostaMChatRepository.saveAll(respostaMChatEntities);
    }

    private AssistidoEntity verificarAssistido(MChatDTO mChatDTO) {
        Optional<AssistidoEntity> optionalAssistido = assistidoRepository.findById(mChatDTO.getId());
        if (optionalAssistido.isEmpty()) {
            throw new NotFoundException("Assistido não encontrado!");
        }
        return optionalAssistido.get();
    }

    private void validarMChatDTO(MChatDTO mChatDTO) {
        if (Objects.isNull(mChatDTO.getId())) {
            throw new BusinessException("M-Chat precisa ser associado a um assistido");
        }
    }
}
