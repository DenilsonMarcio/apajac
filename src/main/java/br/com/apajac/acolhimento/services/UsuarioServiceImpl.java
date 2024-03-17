package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import br.com.apajac.acolhimento.domain.entities.UsuarioEntity;
import br.com.apajac.acolhimento.repositories.UsuarioRepository;
import br.com.apajac.acolhimento.services.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    @Override
    public void cadastrar(UsuarioDTO usuario) {

        UsuarioEntity entity = new UsuarioEntity();
        entity.setName(usuario.getName());
        entity.setType(usuario.getType());
        entity.setStatus(usuario.getStatus());
        entity.setPassword(usuario.getPassword());
        entity.setLogin(usuario.getLogin());

        repository.save(entity);
    }

    @Override
    public List<UsuarioEntity> listarUsuarios() {
        return repository.findAll();
    }
}
