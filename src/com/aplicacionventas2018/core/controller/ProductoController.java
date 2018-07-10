package com.aplicacionventas2018.core.controller;

import com.aplicacionventas2018.core.dao.CategoriaDaoImpl;
import com.aplicacionventas2018.core.dao.ProductoDaoImpl;
import com.aplicacionventas2018.core.model.Categoria;
import com.aplicacionventas2018.core.model.Producto;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;



public class ProductoController implements Initializable{
    
    private enum ACCIONES {NUEVO, EDITAR};
    
    @FXML private TableView<Producto> tblProducto;
    @FXML private TableColumn<Producto, Number> colCodigoProducto;
    @FXML private TableColumn<Producto, Number> colPrecio;
    @FXML private TableColumn<Producto, String> colDescripcion;
    @FXML private TableColumn<Producto, String> colCategoria;
    @FXML private TableColumn<Producto, Number> colPrecioUnidad;
    @FXML private TableColumn<Producto, Number> colExistencias;
    @FXML private ChoiceBox choiceCategorias;
    @FXML private Button btnNuevo;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;

    @FXML private TextField txtPrecio;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtExistencias;

    @FXML private ObservableList<Producto> listaProductos;
    @FXML private ObservableList<Categoria> listaCategorias;
    @FXML private ObservableList<String> listaDescripciones;
    
    private ACCIONES accion;
    private Producto elementoSeleccionado;
    
    private ProductoDaoImpl productoDao = new ProductoDaoImpl();
    private CategoriaDaoImpl categoriaDao = new CategoriaDaoImpl();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getProductos();
        enlazarDatos();
        enlazarColumnas();
        
        
    }
    
    public void seleccionarElemento(){
        
        if(tblProducto.getSelectionModel().getSelectedItem()!= null){
            
            elementoSeleccionado = tblProducto.getSelectionModel().getSelectedItem();
            txtDescripcion.setText(elementoSeleccionado.getDescripcion());
            txtExistencias.setText(String.valueOf(elementoSeleccionado.getExistencias()));
            txtPrecio.setText(String.valueOf(elementoSeleccionado.getPrecio()));
            txtPrecioUnitario.setText((String.valueOf(elementoSeleccionado.getPrecioUnitario())));
            choiceCategorias.setValue(elementoSeleccionado.getCategoria());
            
        }
        
    }

    private void getProductos() {
        
        listaProductos = FXCollections.observableArrayList(productoDao.findAllProducto());
        listaCategorias = FXCollections.observableArrayList(categoriaDao.findAllCategoria());
       
        choiceCategorias.setItems(listaCategorias);        
        
    }
    
 

    private void enlazarDatos() {
        tblProducto.setItems(listaProductos);
        choiceCategorias.setItems(listaCategorias);
    }

    private void enlazarColumnas() {
       colCodigoProducto.setCellValueFactory(cellData->cellData.getValue().codigoProducto());
       colPrecio.setCellValueFactory(cellData->cellData.getValue().precio());
       colDescripcion.setCellValueFactory(cellData->cellData.getValue().descripcion());        
       colCategoria.setCellValueFactory(cellData->cellData.getValue().getCategoria().descripcion());
       colPrecioUnidad.setCellValueFactory(cellData->cellData.getValue().precioUnitario());
       colExistencias.setCellValueFactory(cellData->cellData.getValue().existencias());
    }
    
    public void nuevo(){
        
        txtDescripcion.setEditable(true);
        txtDescripcion.setText("");
        txtExistencias.setEditable(true);
        txtExistencias.setText("");
        txtPrecio.setEditable(true);
        txtPrecio.setText("");
        txtPrecioUnitario.setEditable(true);
        txtPrecioUnitario.setText("");
        choiceCategorias.getSelectionModel().clearSelection();
        choiceCategorias.setDisable(false);
        btnNuevo.setDisable(true);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnEliminar.setDisable(true);
        accion = ACCIONES.NUEVO;
        
    }
    
    public void guardar(){
        
                if(choiceCategorias.getSelectionModel().getSelectedItem()== null){                
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una categoria");
            }else if (txtDescripcion.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Debe ingresar una descripcion");
            }else{
        
        try { 
            
            switch(accion){
                
                case NUEVO:
                    Producto producto = new Producto();  
                    producto.setCategoria((Categoria)choiceCategorias.getSelectionModel().getSelectedItem());
                    producto.setDescripcion(txtDescripcion.getText());
                    producto.setPrecio(Integer.parseInt(txtPrecio.getText()));
                    producto.setExistencias(Integer.parseInt(txtExistencias.getText()));
                    producto.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));   
                    productoDao.saveProducto(producto);
                    listaProductos.add(producto);
                    break;
                case EDITAR: 
                    
                    elementoSeleccionado.setDescripcion(txtDescripcion.getText());
                    elementoSeleccionado.setCategoria((Categoria)choiceCategorias.getSelectionModel().getSelectedItem());
                    elementoSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
                    elementoSeleccionado.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
                    elementoSeleccionado.setExistencias(Integer.parseInt(txtExistencias.getText()));
                    productoDao.saveProducto(elementoSeleccionado);
                    listaProductos.set(tblProducto.getSelectionModel().getSelectedIndex(),elementoSeleccionado);
            
            }
            
            btnEliminar.setDisable(false);
            btnNuevo.setDisable(false);
            txtDescripcion.setText("");
            txtDescripcion.setEditable(false);
            txtExistencias.setText("");
            txtExistencias.setEditable(false);
            txtPrecio.setText("");
            txtPrecioUnitario.setText("");
            txtPrecioUnitario.setEditable(false);
            txtPrecio.setText("");
            txtPrecio.setEditable(false); 
            choiceCategorias.getSelectionModel().clearSelection();
            choiceCategorias.setDisable(true);                                                                                             
            
        } catch (Exception e) {
            e.printStackTrace();
        }
                
    }
        
}
    
    public void cancelar(){
        
        txtDescripcion.setText("");
        txtDescripcion.setEditable(false);
        txtExistencias.setText("");
        txtExistencias.setEditable(false);
        txtPrecio.setText("");
        txtPrecioUnitario.setText("");
        txtPrecioUnitario.setEditable(false);
        txtPrecio.setText("");
        txtPrecio.setEditable(false);
        choiceCategorias.setDisable(false);
        btnNuevo.setDisable(false);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(false);
        
    }
    
    public void eliminar(){
        
        if(tblProducto.getSelectionModel().getSelectedItem() != null){
            int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION);            
            if(respuesta == JOptionPane.YES_OPTION){
                productoDao.deleteProducto(tblProducto.getSelectionModel().getSelectedItem());
                listaProductos.remove(tblProducto.getSelectionModel().getSelectedItem());
            }
        }else{
            JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento");
        }        
    }
    
    public void modificar(){
        
        if(tblProducto.getSelectionModel().getSelectedItem()!= null){
            
            txtDescripcion.setEditable(true);
            txtExistencias.setEditable(true);
            txtPrecio.setEditable(true);
            txtPrecioUnitario.setEditable(true);
            choiceCategorias.setDisable(false);
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);
            btnGuardar.setDisable(false);
            btnCancelar.setDisable(false);
            accion = ACCIONES.EDITAR;                        
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
        
    }
        
}
    


