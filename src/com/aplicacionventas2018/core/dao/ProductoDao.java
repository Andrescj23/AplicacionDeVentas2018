package com.aplicacionventas2018.core.dao;

import com.aplicacionventas2018.core.model.Producto;
import java.util.List;



public interface ProductoDao {

    public List<Producto> findAllCategoria();
    public Producto findProductoById(Integer id);
    public Producto findProductoByDescripcion(String descripcion);
    public void saveProducto (Producto elemento);
    public void deleteProducto(Producto elemento);
    public void updateProducto(Producto elemento);    
        
    }