package com.aplicacionventas2018.core.controller;

import com.aplicacionventas2018.core.dao.RolDaoImpl;
import com.aplicacionventas2018.core.dao.UsuarioDaoImpl;
import com.aplicacionventas2018.core.model.Rol;
import com.aplicacionventas2018.core.model.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class UsuarioController implements Initializable {

    

    private enum ACCIONES {
        NUEVO, EDITAR
    };

    @FXML private TableView<Usuario> tblUsuario;
    @FXML private TableColumn<Usuario, Number> colCodigoUsuario;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colEmail;
    @FXML private TableColumn<Usuario, String> colLog;
    @FXML private TableColumn<Usuario, String> colPass;
    @FXML private ChoiceBox choiceRol;
    @FXML private Button btnNuevo;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private TextField txtLog;
    @FXML private PasswordField passPassword;
    
    @FXML private ObservableList<Usuario> listaUsuario;
    @FXML private ObservableList<Rol> listaRol;

    private ACCIONES accion;
    private Usuario elementoSeleccionado;

    private UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();
    private RolDaoImpl rolDao = new RolDaoImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getUsuarios();
        enlazarDatos();
        enlazarColumnas();

    }
    
    public void seleccionarElemento(){
        
        if(tblUsuario.getSelectionModel().getSelectedItem()!=null){
            
            elementoSeleccionado = tblUsuario.getSelectionModel().getSelectedItem();
            txtNombre.setText(elementoSeleccionado.getNombre());
            txtEmail.setText(elementoSeleccionado.getEmail());
            txtLog.setText(elementoSeleccionado.getLogin());
            passPassword.setText(elementoSeleccionado.getPass());
            choiceRol.setValue(elementoSeleccionado.getRol());
        }
        
    }
    
    private void getUsuarios() {        
        listaUsuario = FXCollections.observableArrayList(usuarioDao.findAllUsuario());        
        listaRol = FXCollections.observableArrayList(rolDao.findAllRol());
        
        choiceRol.setItems(listaRol);
    }

    private void enlazarDatos() {        
        tblUsuario.setItems(listaUsuario);   
        choiceRol.setItems(listaRol);
    }

    private void enlazarColumnas() {                
        colCodigoUsuario.setCellValueFactory(cellData->cellData.getValue().codigoUsuario());
        colNombre.setCellValueFactory(cellData->cellData.getValue().nombre());
        colEmail.setCellValueFactory(cellData->cellData.getValue().email());
        colLog.setCellValueFactory(cellData->cellData.getValue().login());
        colPass.setCellValueFactory(cellData->cellData.getValue().pass());
    }
    
    public void nuevo(){
        
        txtNombre.setEditable(true);
        txtNombre.setText("");
        txtEmail.setEditable(true);
        txtEmail.setText("");
        txtLog.setEditable(true);
        txtLog.setText("");
        choiceRol.getSelectionModel().clearSelection();
        choiceRol.setDisable(false);
        passPassword.setText("");
        passPassword.setEditable(true);
        btnNuevo.setDisable(true);
        btnGuardar.setDisable(false);
        btnCancelar.setDisable(false);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        accion = ACCIONES.NUEVO;                
    }
    
    public void guardar(){
        
                if(choiceRol.getSelectionModel().getSelectedItem()== null){
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Rol");
                }else if (txtNombre.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
                }else{                    
                
        try {
            switch(accion){
                
                case NUEVO:
                    Usuario usuario = new Usuario();
                    usuario.setRol((Rol)choiceRol.getSelectionModel().getSelectedItem());
                    usuario.setNombre(txtNombre.getText());
                    usuario.setEmail(txtEmail.getText());
                    usuario.setLogin(txtLog.getText());
                    usuario.setPass(passPassword.getText());                    
                    usuarioDao.saveUsuario(usuario);
                    listaUsuario.add(usuario);
                    break;
                    
                case EDITAR:
                    elementoSeleccionado.setNombre(txtNombre.getText());
                    elementoSeleccionado.setRol((Rol)choiceRol.getSelectionModel().getSelectedItem());
                    elementoSeleccionado.setEmail(txtEmail.getText());
                    elementoSeleccionado.setLogin(txtLog.getText());
                    elementoSeleccionado.setPass(passPassword.getText());
                    usuarioDao.saveUsuario(elementoSeleccionado);
                    listaUsuario.set(tblUsuario.getSelectionModel().getSelectedIndex(),elementoSeleccionado);
            }
            
            btnEliminar.setDisable(false);
            btnNuevo.setDisable(false);
            btnEditar.setDisable(false);
            txtNombre.setText("");
            txtNombre.setEditable(false);
            txtEmail.setText("");
            txtEmail.setEditable(false);
            txtLog.setText("");
            txtLog.setEditable(false);
            passPassword.setText("");
            passPassword.setEditable(false);
            choiceRol.getSelectionModel().clearSelection();
            choiceRol.setDisable(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         
    }
    public void cancelar(){        
            txtNombre.setText("");
            txtNombre.setEditable(false);
            txtEmail.setText("");
            txtEmail.setEditable(false);
            txtLog.setText("");
            txtLog.setEditable(false);
            passPassword.setText("");
            passPassword.setEditable(false);
            choiceRol.getSelectionModel().clearSelection();
            choiceRol.setDisable(true);
            btnEliminar.setDisable(false);
            btnNuevo.setDisable(false);
            btnEditar.setDisable(false);
            btnCancelar.setDisable(true);
            btnGuardar.setDisable(true);
    }
    
    public void eliminar(){
        
        if(tblUsuario.getSelectionModel().getSelectedItem() != null){
            int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar",JOptionPane.YES_NO_OPTION);
            if(respuesta == JOptionPane.YES_OPTION){
                usuarioDao.deleteUsuario(tblUsuario.getSelectionModel().getSelectedItem());
                listaUsuario.remove((tblUsuario.getSelectionModel().getSelectedItem()));
            }
        }else{
            JOptionPane.showMessageDialog((null), "Debe seleccionar un elemento");
        }
    }
    
    public void modificar(){
        if(tblUsuario.getSelectionModel().getSelectedItem() != null){
            
            txtNombre.setEditable(true);
            txtEmail.setEditable(true);
            txtLog.setEditable(true);
            passPassword.setEditable(true);
            choiceRol.setDisable(false);
            btnEliminar.setDisable(true);
            btnNuevo.setDisable(true);
            btnEditar.setDisable(true);
            btnCancelar.setDisable(false);
            btnGuardar.setDisable(false);
            accion = ACCIONES.EDITAR;
    }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
            
        }
    }
}
