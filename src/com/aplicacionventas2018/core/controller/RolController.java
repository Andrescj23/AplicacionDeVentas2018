package com.aplicacionventas2018.core.controller;

import com.aplicacionventas2018.core.dao.RolDaoImpl;
import com.aplicacionventas2018.core.model.Rol;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class RolController implements Initializable{

   
    private enum ACCIONES {NUEVO, EDITAR};
    
    @FXML private TableView<Rol> tblRol;
    @FXML private TableColumn<Rol, Number> colCodigoRol;
    @FXML private TableColumn<Rol, String> colRol;
    @FXML private TextField txtDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    
    private ACCIONES accion;
    private Rol elementoSelecciondo;
    
    private ObservableList<Rol> listaRoles;
    private RolDaoImpl rolDao = new RolDaoImpl();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getRoles();
        enlazarDatos();
        enlzarColumnas();
    }
    
    public void seleccionarElementos(){
        if(tblRol.getSelectionModel().getSelectedItem() != null){
            elementoSelecciondo = tblRol.getSelectionModel().getSelectedItem();
            txtDescripcion.setText(elementoSelecciondo.getDescripcion());
        }
    }
    
     private void getRoles() {
         listaRoles = FXCollections.observableArrayList(rolDao.findAllRol());
    }

    private void enlazarDatos() {
        tblRol.setItems(listaRoles);
    }

    private void enlzarColumnas() {
        colCodigoRol.setCellValueFactory(cellData->cellData.getValue().codigoRol());
        colRol.setCellValueFactory(cellData->cellData.getValue().descripcion());
    }
    public void nuevo(){
        txtDescripcion.setEditable(true);
        btnNuevo.setDisable(true);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        accion = ACCIONES.NUEVO;
    }
    public void guardar(){
        try {
            switch(accion){
                case NUEVO:
                    Rol rol = new Rol();
                    rol.setDescripcion(txtDescripcion.getText());
                    rolDao.saveRol(rol);
                    listaRoles.add(rol);
                    break;
                case EDITAR:
                    elementoSelecciondo.setDescripcion(txtDescripcion.getText());
                    rolDao.saveRol(elementoSelecciondo);
                    listaRoles.set(tblRol.getSelectionModel().getSelectedIndex(), elementoSelecciondo);
            }
            
            txtDescripcion.setText("");
            txtDescripcion.setEditable(false);
            btnNuevo.setDisable(false);
            btnGuardar.setDisable(true);
            btnCancelar.setDisable(true);
            btnEliminar.setDisable(false);
            btnEditar.setDisable(false);
        } catch (Exception e) {
        }
    }
    public void cancelar(){
        txtDescripcion.setText("");
        txtDescripcion.setEditable(false);
        btnNuevo.setDisable(false);
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
    }
    public void eliminar(){
        if(tblRol.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if(respuesta == JOptionPane.YES_OPTION)    {
            rolDao.deleteRol(tblRol.getSelectionModel().getSelectedItem());
            listaRoles.remove(tblRol.getSelectionModel().getSelectedItem());
        }
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
    }
    public void modificar(){
        if(tblRol.getSelectionModel().getSelectedItem() != null){
            txtDescripcion.setEditable(true);
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);
            btnGuardar.setDisable(false);
            btnCancelar.setDisable(false);
            accion = ACCIONES.EDITAR;
        }else{
        JOptionPane.showConfirmDialog(null, "Debe seleccionar un elemento");
    }
    }
}
