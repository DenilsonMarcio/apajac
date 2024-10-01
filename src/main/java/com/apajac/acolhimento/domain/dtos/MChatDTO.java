package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MChatDTO {
    private Long id;
    private Boolean pea;
    private List<RespostaMChatDTO> mchat;
}
