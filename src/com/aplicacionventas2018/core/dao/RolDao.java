package com.aplicacionventas2018.core.dao;

import com.aplicacionventas2018.core.model.Rol;
import java.util.List;

public interface RolDao {
    
    public List<Rol> findAllRol();
    public Rol findRolById(Integer id);
    public Rol findRolbyDescripcion(String descripcion);
    public void saveRol (Rol elemento);
    public void deleteRol (Rol elemento);
    public void updateRol (Rol elemento);
    
}
