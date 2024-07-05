package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.DoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional
public class DoadorServiceImpl implements DoadorService
{

    private final DoadorRepository doadorRepository;

    @Override
    public void persistirDoador(DoadorDTO doadorDTO) {
        try {
            DoadorEntity doador = inserirDoador(doadorDTO);
        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    private DoadorEntity inserirDoador(DoadorDTO doadorDTO) {
        if (isNull(doadorDTO.getId())){
            return createDoador(doadorDTO);
        }
        return updateDoador(doadorDTO);
    }

    private DoadorEntity createDoador(DoadorDTO doadorDTO)
    {
        DoadorEntity doadorEntity = new DoadorEntity();
        doadorEntity.setNome(doadorDTO.getNome());
        doadorEntity.setDocumento(doadorDTO.getDocumento());
        doadorEntity.setValor(doadorDTO.getValor());
        doadorEntity.setTipo_doador(doadorDTO.getTipo_doador());
        doadorEntity.setComo_conheceu(doadorDTO.getComo_conheceu());
        return doadorRepository.save(doadorEntity);
    }

    private DoadorEntity updateDoador(DoadorDTO doadorDTO)
    {
        Optional<DoadorEntity> optionalDoador = doadorRepository.findById(doadorDTO.getId());
        if (optionalDoador.isEmpty())
        {
            throw new NotFoundException("Não foi possível alterar o Doador.");
        }
        DoadorEntity entity = optionalDoador.get();
        entity.setId(doadorDTO.getId());
        entity.setNome(doadorDTO.getNome());
        entity.setDocumento(doadorDTO.getDocumento());
        entity.setValor(doadorDTO.getValor());
        entity.setTipo_doador(doadorDTO.getTipo_doador());
        entity.setComo_conheceu(doadorDTO.getComo_conheceu());
        return doadorRepository.save(entity);
    }

    @Override
    public void removerDoador(Long id)
    {
        Optional<DoadorEntity> doador = doadorRepository.findById(id);
        if (doador.isEmpty())
        {
            throw new NotFoundException("Doador não encontrado.");
        }
        doadorRepository.delete(doador.get());
    }
    @Override
    public Page<DoadorEntity> listarDoadores(Pageable pageable)
    {
        return doadorRepository.findAll(pageable);
    }
    @Override
    public DoadorEntity buscarDoadorPorId(Long id)
    {
        Optional<DoadorEntity> doadorOpt = doadorRepository.findById(id);
        if (doadorOpt.isEmpty())
        {
            throw new NotFoundException("Doador não encontrado.");
        }
        return doadorOpt.get();
    }
}
