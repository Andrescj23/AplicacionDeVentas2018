package com.aplicacionventas2018.core.sistema;

import com.aplicacionventas2018.core.controller.CategoriaController;
import com.aplicacionventas2018.core.controller.ProductoController;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Principal extends Application {

    private final String PAQUETE_VISTA = "/com/aplicacionventas2018/core/view/";
    private Stage escenarioPrincipal;
    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;

       //mostrarHelloWorld();
        mostrarCategoria();
        //mostrarProducto();
        this.escenarioPrincipal.setTitle("Sistema Ventas");
        this.escenarioPrincipal.show();
    }

   public void mostrarCategoria() {
        try {
            CategoriaController categoriaController = (CategoriaController) 
                    cambiarEscena("CategoriaView.fxml", 600, 400);

        } catch (Exception e) {
        }
    }
    
    private void mostrarProducto() {
        try {
            ProductoController productoContoller = (ProductoController)
                cambiarEscena("ProductosView.fxml", 600,400);
        } catch (Exception e) {
        }
        
    }

    
     public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA + fxml));
        Scene escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        this.escenarioPrincipal.setScene(escena);
        this.escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;
    }

    
   

}
