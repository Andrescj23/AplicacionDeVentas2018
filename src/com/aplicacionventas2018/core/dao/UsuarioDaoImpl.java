package com.aplicacionventas2018.core.dao;

import com.aplicacionventas2018.core.db.Conexion;
import com.aplicacionventas2018.core.model.Usuario;
import java.util.List;


public class UsuarioDaoImpl implements UsuarioDao{

    @Override
    public List<Usuario> findAllUsuario() {
        return (List<Usuario>)Conexion.getInstancia().findAll(Usuario.class);
    }

    @Override
    public Usuario findUsuarioByid(Integer id) {
        return (Usuario) (List<Usuario>) (Usuario) Conexion.getInstancia().findAll(Usuario.class);
    }

    @Override
    public Usuario findUsuariobyDescripcion(String descripcion) {
        List<Usuario> usuario = (List<Usuario>) Conexion.getInstancia().getEm()
                .createNamedQuery("select u from Usuario u where u.descripcion =: descripcion")
                .setParameter("descripcion", descripcion)
                .getResultList();
        if(!usuario.isEmpty()){
            return usuario.get(0);
        }
        return null;
    }

    @Override
    public void saveUsuario(Usuario elemento) {
        Conexion.getInstancia().save(elemento);
    }

    @Override
    public void deleteUsuario(Usuario elemento) {
        Conexion.getInstancia().delete(elemento);
    }

    @Override
    public void updateUsuario(Usuario elemento) {
        Conexion.getInstancia().update(elemento);
    }
    
}
