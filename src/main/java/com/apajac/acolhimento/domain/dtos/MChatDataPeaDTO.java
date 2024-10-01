package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MChatDataPeaDTO {
    private Long id;
    private LocalDate data;
    private Boolean pea;
}
