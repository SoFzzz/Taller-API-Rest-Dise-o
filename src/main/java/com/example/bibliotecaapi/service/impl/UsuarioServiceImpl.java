package com.example.bibliotecaapi.service.impl;

import com.example.bibliotecaapi.dto.UsuarioRequest;
import com.example.bibliotecaapi.dto.UsuarioResponse;
import com.example.bibliotecaapi.model.Usuario;
import com.example.bibliotecaapi.repository.UsuarioRepository;
import com.example.bibliotecaapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponse save(UsuarioRequest usuarioRequest){
        // Mapear dto a mongodb
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setCorreo(usuario.getCorreo());
        usuario.setTipoUsuario(usuarioRequest.getTipoUsuario());

        //Guardar en la base de datos

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Mapear la entidad guardada
        return mapToResponse(usuarioGuardado);
    }

    @Override
    public List<UsuarioResponse> findAll(){
        // 1. Buscar todos los usuarios de la base de datos
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id){
        // Verificar primero si el usuario existe antes de intentar eliminarlo
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }

        // Eliminar el documento de MongoDB
        usuarioRepository.deleteById(id);
    }

    // Metodo auxiliar
    private UsuarioResponse mapToResponse(Usuario usuario){
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTipoUsuario()
        );
    }
}
