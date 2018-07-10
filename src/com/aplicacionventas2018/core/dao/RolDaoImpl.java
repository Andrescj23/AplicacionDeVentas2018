package com.aplicacionventas2018.core.dao;

import com.aplicacionventas2018.core.db.Conexion;
import com.aplicacionventas2018.core.model.Rol;
import java.util.List;


public class RolDaoImpl implements RolDao{

    @Override
    public List<Rol> findAllRol() {
        return (List<Rol>) Conexion.getInstancia().findAll(Rol.class);
    }

    @Override
    public Rol findRolById(Integer id) {
        return (Rol) (List<Rol>) (Rol)Conexion.getInstancia().findAll(Rol.class);
    }

    @Override
    public Rol findRolbyDescripcion(String descripcion) {
        List<Rol> rol = (List<Rol>) Conexion.getInstancia().getEm()
                .createNamedQuery("select r from Rol r where r.descripcion =: descripcion")
                .setParameter("descripcion", descripcion)
                .getResultList();
        if (!rol.isEmpty()){
            return rol.get(0);
        }
          return null;
    }

    @Override
    public void saveRol(Rol elemento) {
        Conexion.getInstancia().save(elemento);
    }

    @Override
    public void deleteRol(Rol elemento) {
        Conexion.getInstancia().delete(elemento);
    }

    @Override
    public void updateRol(Rol elemento) {
        Conexion.getInstancia().update(elemento);
    }
    
}
