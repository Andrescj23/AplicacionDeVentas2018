package com.aplicacionventas2018.core.dao;

import com.aplicacionventas2018.core.model.Usuario;
import java.util.List;

public interface UsuarioDao {
    
    public List<Usuario> findAllUsuario();
    public Usuario findUsuarioByid(Integer id);
    public Usuario findUsuariobyDescripcion(String descripcion);
    public void saveUsuario (Usuario elemento);
    public void deleteUsuario (Usuario elemento);
    public void updateUsuario (Usuario elemento);
    
}
