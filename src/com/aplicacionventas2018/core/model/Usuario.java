package com.aplicacionventas2018.core.model;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
@NamedQueries({@NamedQuery(name = "Usuario.findAll", query = "from Usuario")})
public class Usuario implements Serializable{
    
    private final IntegerProperty codigoUsuario;
    private final StringProperty nombre;
    private final StringProperty login;
    private final StringProperty pass;
    private final StringProperty email;
    private Rol rol;

    public Usuario(){
        
        this.codigoUsuario = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty("");
        this.login = new SimpleStringProperty("");
        this.pass = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        
    }
    
    public Usuario(int codigoUsuario, String nombre, String login, String pass, String email) {
        this.codigoUsuario = new SimpleIntegerProperty(codigoUsuario);
        this.nombre = new SimpleStringProperty(nombre);
        this.login =  new SimpleStringProperty(login);
        this.pass =  new SimpleStringProperty(pass);
        this.email = new SimpleStringProperty(email);
    }
    
     @Override
    public String toString() {
        return "Usuario{" + "codigoUsuario=" + codigoUsuario + ", nombre=" + nombre + ", login=" + login + ", pass=" + pass + ", email=" + email + ", rol=" + rol + '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usuario")
    public int getCodigoUsuario() {
        return codigoUsuario.get();
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario.set(codigoUsuario);
    }
        
    public IntegerProperty codigoUsuario(){        
        return codigoUsuario;
    }

    @Column(name = "nombre") 
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    
    public StringProperty nombre(){
        return nombre;
    }

    @Column(name = "login")
    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }
    
    public StringProperty login(){
        return login;
    }
    
    @Column(name = "pass")
    public String getPass() {
        return pass.get();
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }
    
    public StringProperty pass(){        
        return pass;
    }

    @Column(name = "email")
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public StringProperty email(){        
        return email;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_rol")
    public Rol getRol(){        
        return rol;        
    }
    
    public void setRol(Rol rol){        
        this.rol = rol;
    }
    
}
