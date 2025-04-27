package com.apajac.acolhimento.services.interfaces;

import java.util.List;
import java.util.Map;

public interface FamiliarMediaRendaService {

    Map<String, List<String>> calcularMediaGeralRenda();

    Map<String, List<String>> calcularMediaRendaPorAssistido();

    Map<String, List<String>> calcularFaixasDeRenda();
}
