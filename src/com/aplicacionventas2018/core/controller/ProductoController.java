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
    @FXML private TextField txtPrecio;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtExistencias;

    @FXML private ObservableList<Producto> listaProductos;
    @FXML private ObservableList<Categoria> listaCategorias;
    @FXML private ObservableList<String> listaDescripciones;
    
    private ProductoDaoImpl productoDao = new ProductoDaoImpl();
    private CategoriaDaoImpl categoriaDao = new CategoriaDaoImpl();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getProductos();
        enlazarDatos();
        enlazarColumnas();
        
        
    }

    private void getProductos() {
        
        listaProductos = FXCollections.observableArrayList(productoDao.findAllProducto());
        listaCategorias = FXCollections.observableArrayList(categoriaDao.findAllCategoria());
        listaDescripciones = FXCollections.observableArrayList(listaCategorias.stream().map(categoria-> categoria.getDescripcion()).collect(Collectors.toList()));
        choiceCategorias.setItems(listaDescripciones);
        
        System.err.println(listaDescripciones);

        
    }
    
 

    private void enlazarDatos() {
        tblProducto.setItems(listaProductos);
        choiceCategorias.setItems(listaDescripciones);
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
        
        txtDescripcion.setDisable(false);
        txtExistencias.setDisable(false);
        txtPrecio.setDisable(false);
        txtPrecioUnitario.setDisable(false);
        choiceCategorias.setDisable(false);
        btnNuevo.setDisable(true);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnEliminar.setDisable(true);
        
    }
    
    public void guardar(){
        
        try {
            Producto producto = new Producto();
            Categoria categoria = new Categoria();
            
            producto.setDescripcion(txtDescripcion.getText());
            
            producto.setPrecio(Integer.parseInt(txtPrecio.getText()));
            producto.setExistencias(Integer.parseInt(txtExistencias.getText()));
            producto.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            //categoria.setChoiceCategorias(choiceCategorias.)
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void cancelar(){
        
        txtDescripcion.setText("");
        txtDescripcion.setDisable(true);
        txtExistencias.setText("");
        txtExistencias.setDisable(true);
        txtPrecio.setText("");
        txtPrecioUnitario.setText("");
        txtPrecioUnitario.setDisable(true);
        txtPrecio.setText("");
        txtPrecio.setDisable(true);
        choiceCategorias.setDisable(true);
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
    
        
    }
    


