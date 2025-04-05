package com.apajac.acolhimento.services;

import com.apajac.acolhimento.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackupService {

    private final AssistidoRepository assistidoRepository;
    private final AuditoriaRepository auditoriaRepository;
    private final CarsRepository carsRepository;
    private final ComposicaoFamiliarRepository composicaoFamiliarRepository;
    private final ContatoRepository contatoRepository;
    private final FamiliarRepository familiarRepository;
    private final MChatRepository mChatRepository;
    private final ResponsavelRepository responsavelRepository;
    private final RespostaCarsRepository respostaCarsRepository;
    private final RespostaMChatRepository respostaMChatRepository;
    private final UsuarioRepository usuarioRepository;

}
