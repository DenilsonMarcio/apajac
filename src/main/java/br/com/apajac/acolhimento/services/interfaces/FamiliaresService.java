package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import br.com.apajac.acolhimento.domain.entities.FamiliaresEntity;

public interface FamiliaresService {

    FamiliaresEntity cadastrarFamiliar(FamiliaresDTO familiares);


}
