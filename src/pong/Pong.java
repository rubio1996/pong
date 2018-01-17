/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
   //Creacion de constantes de la medida de la ventana
   final int SCENE_TAM_X = 600;
   final int SCENE_TAM_Y = 400;
   //Creacion de constantes para la pala
   final int STICK_WIDTH = 7;
   final int STICK_HEIGHT = 50;
   int stickPosY = (SCENE_TAM_Y - STICK_HEIGHT) /2;
   int stickCurrentSpeed = 0;
    @Override
    public void start(Stage primaryStage) {

        //pondremos un panal normal porque si pones STACKPANE acostumbrara a poner todos los elementos en medio y no tomara los valores puesto por xy
        Pane root = new Pane();
        //Aqui crearemos una escena  con las medidas elegidas en las variables
        Scene scene = new Scene (root, SCENE_TAM_X, SCENE_TAM_Y, Color.BLACK);
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
        //Ahora le daremos valores a las teclas para cuando pulses una tecla
        scene.setOnKeyPressed((KeyEvent event) -> {
            //Crearemos un swith que es como una sentencia de if  pero tiene multiples opciones para selecionar la tecla  y el evento
            switch(event.getCode()){
                case UP:
                    //pulsada tecla arriba
                    stickCurrentSpeed = -6;
                    break;
                case DOWN:
                    //pulsada tecla abajo
                    stickCurrentSpeed = 6;
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            stickCurrentSpeed = 0;
        });
         //Creacion de la pala donde chocara la pelota
                Rectangle rectStick = new Rectangle(SCENE_TAM_X*0.9, stickPosY, STICK_WIDTH, STICK_HEIGHT);
                rectStick.setFill(Color.WHITE);
                //añadiremos el rectangulo o pala como hijo del panel principal 
                root.getChildren().add(rectStick);
        AnimationTimer animationBall = new AnimationTimer(){
         
            @Override
            public void handle(long now) {
                circleBall.setCenterX(ballCenterX);        
                ballCenterX += BallCurrentSpeedX;
                if(ballCenterX >= SCENE_TAM_X) {
                    BallCurrentSpeedX = -3;
                }
                if(ballCenterX <= 0){
                    BallCurrentSpeedX = 3;
                }
                circleBall.setCenterY(ballCentery);        
                ballCentery += BallCurrentSpeedy;
                if(ballCentery >= SCENE_TAM_Y) {
                    BallCurrentSpeedy = -3;
                }
                if(ballCentery <= 0){
                    BallCurrentSpeedy = 3;
                }
                //actualizar posicion de la pala
                rectStick.setY(stickPosY);
                stickPosY += stickCurrentSpeed;
                if (stickPosY < 0) {
                    //No sobrepasar el borde superior de la ventana
                    stickPosY = 0;
                }
                else {
                    //No sobrepasar el borde inferior de la ventana
                    if(stickPosY > SCENE_TAM_Y - STICK_HEIGHT) {
                        stickPosY =SCENE_TAM_Y - STICK_HEIGHT; 
                    }
                }
                //MOVEMOS EL RECTANGULO A LA POSICION ACTUAL QUE SE LA HEMOS DADO ANTERIORMENTE
                rectStick.setY(stickPosY);
                
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