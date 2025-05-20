package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RespostaMChatDTO {
    private Integer pergunta;
    private Boolean resposta;
}
