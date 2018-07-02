package com.aplicacionventas2018.core.dao;

import java.util.List;
import com.aplicacionventas2018.core.model.Categoria;


public interface CategoriaDao {

    public List<Categoria> findAllCategoria();
    public Categoria findCategoriaById(Integer id);
    public Categoria findCategoriaByDescripcion(String descripcion);
    public void saveCategoria (Categoria elemento);
    public void deleteCategoria(Categoria elemento);
    public void updateCategoria(Categoria elemento);
    
}