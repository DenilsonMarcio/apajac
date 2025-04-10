package com.apajac.acolhimento.services;

import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.RelatoriosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatoriosServiceImpl implements RelatoriosService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public int calcularMediaIdade() {
        List<LocalDate> datas = assistidoRepository.findAllBirthDates();
        return (int) datas.stream()
                .mapToInt(data -> Period.between(data, LocalDate.now()).getYears())
                .average()
                .orElse(0);
    }
}
