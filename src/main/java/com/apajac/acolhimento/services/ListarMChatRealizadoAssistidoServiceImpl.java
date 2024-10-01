package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.*;
import com.apajac.acolhimento.domain.entities.*;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.MChatRepository;
import com.apajac.acolhimento.services.interfaces.ListarMChatService;
import com.apajac.acolhimento.utils.AssistidoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListarMChatRealizadoAssistidoServiceImpl implements ListarMChatService {

    private final MChatRepository mChatRepository;
    private final AssistidoUtils assistidoUtils;

    @Override
    public NomeAssistidoMChatDTO listarMChatPorAssistido(Long id) {
        NomeAssistidoMChatDTO response = new NomeAssistidoMChatDTO();
        List<MChatDataPeaDTO> datasMChat = new ArrayList<>();

        AssistidoEntity assistido = assistidoUtils.verificaAssistido(id);

        List<MChatEntity> listMChat = mChatRepository.findByAssistidoId(assistido.getId());

        for (MChatEntity mchat : listMChat){
            MChatDataPeaDTO dto = new MChatDataPeaDTO();
            dto.setId(mchat.getId());
            dto.setData(mchat.getRealizadoEm());
            dto.setPea(mchat.getPea());
            datasMChat.add(dto);
        }

        response.setNomeAssistido(assistido.getNome());
        response.setMchat(datasMChat);

        return response;
    }

    @Override
    public DetalhesMChatAssistidoDTO detalhesMChatPorAssistidoEData(Long id) {
        DetalhesMChatAssistidoDTO response = new DetalhesMChatAssistidoDTO();
        List<RespostaMChatDTO> detalhesMChat = new ArrayList<>();

        MChatEntity mchat = verificaMChat(id);
        AssistidoEntity assistido = assistidoUtils.verificaAssistido(mchat.getAssistido().getId());

        List<RespostaMChatEntity> respostas = mchat.getRespostas();
        for (RespostaMChatEntity resp : respostas){
            RespostaMChatDTO dto = new RespostaMChatDTO();
            dto.setPergunta(resp.getPergunta());
            dto.setResposta(resp.getResposta());
            detalhesMChat.add(dto);
        }

        response.setNomeAssistido(assistido.getNome());
        response.setData(mchat.getRealizadoEm());
        response.setPea(mchat.getPea());
        response.setDetalhes(detalhesMChat);

        return response;
    }

    private MChatEntity verificaMChat(Long id) {
        Optional<MChatEntity> optionalMChat = mChatRepository.findById(id);
        if (optionalMChat.isEmpty()){
            throw new NotFoundException("M-Chat não encontrado!");
        }
        return optionalMChat.get();
    }
}
