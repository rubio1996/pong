/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author 1daw
 */
public class Pong extends Application {
   int ballCenterX = 10;
   int ballCentery = 10;
   double valory = 30;
   double valorx = 10;
   double radioCirculo = 7;
   int BallCurrentSpeedX = 3;
   int BallCurrentSpeedy = 3;
    @Override
    public void start(Stage primaryStage) {
        //DECLARACION DE VARIABLES
        
          
        
        //pondremos un panal normal porque si pones STACKPANE acostumbrara a poner todos los elementos en medio y no tomara los valores puesto por xy
        Pane root = new Pane();
        Scene scene = new Scene (root, 600, 400, Color.BLACK);
        primaryStage.setTitle("PONG");
        primaryStage.setScene(scene);
        primaryStage.show();
        //Creacion del circulo 
        Circle circleBall = new Circle();
        //Posicionar respecto xy el centro del circulo
        circleBall.setCenterX(valorx);
        circleBall.setCenterY(valory);
        //Tamaño del radio del circulo
        circleBall.setRadius(radioCirculo);
        //AHORA PARA MOSTRAR EL CIRCULO EN LA VENTANA  HABRAQUE AÑADIR EL CIRCULO A LA VENTANA
        root.getChildren().add(circleBall);
        //CAMBIAR COLOR A LA BOLA POR EL CODIGO RGB
        circleBall.setFill(Color.web("#51FE00"));
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now) {
                circleBall.setCenterX(ballCenterX);        
                ballCenterX += BallCurrentSpeedX;
                if(ballCenterX >= 600) {
                    BallCurrentSpeedX = -3;
                }
                if(ballCenterX <= 0){
                    BallCurrentSpeedX = 3;
                }
                circleBall.setCenterY(ballCentery);        
                ballCentery += BallCurrentSpeedy;
                if(ballCentery >= 400) {
                    BallCurrentSpeedy = -3;
                }
                if(ballCentery <= 0){
                    BallCurrentSpeedy = 3;
                }
            };
        };
        animationBall.start();
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        launch(args);
    }
}