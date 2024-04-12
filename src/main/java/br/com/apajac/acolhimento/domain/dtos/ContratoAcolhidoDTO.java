package br.com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ContratoAcolhidoDTO {

    private AcolhidoDTO acolhido;
    private MaeDTO mae;
    private PaiDTO pai;
    private ResponsavelDTO responsavel;

}
