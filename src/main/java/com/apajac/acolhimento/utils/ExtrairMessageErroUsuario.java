package com.apajac.acolhimento.utils;

import org.springframework.stereotype.Component;
@Component
public class ExtrairMessageErroUsuario {
    public static String extrairMensagemDeErro(String mensagemErro) {
        int inicio = mensagemErro.indexOf("Duplicate entry '");
        int fim = mensagemErro.indexOf("' for key '");

        if (inicio != -1 && fim != -1) {
            return mensagemErro.substring(inicio, fim);
        } else {
            return null;
        }
    }
}
