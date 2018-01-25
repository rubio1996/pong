/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author 1daw
 */
public class Pong extends Application {
   int ballCenterX = 10;
   int ballCentery = 10;
   double valorY = 30;
   double valorX = 10;
   double radioCirculo = 7;
   int ballCurrentSpeedX = 3;
   int ballCurrentSpeedy = 3;
   //Creacion de constantes de la medida de la ventana
   final int SCENE_TAM_X = 600;
   final int SCENE_TAM_Y = 400;
   //Creacion de constantes para la pala
   final int STICK_WIDTH = 7;
   final int STICK_HEIGHT = 50;
   int stickPosY = (SCENE_TAM_Y - STICK_HEIGHT) /2;
   int stickCurrentSpeed = 0;
   final int TEXT_SIZE = 15;
   //creacion de variable para la puntuacion
   int score;
   //Creacion de variable para la puntuacion maxima
   int highScore;
   Text textScore;
   int collisionZone = getStickCollisionZone(circleBall, rectStick);
   Pane root = new Pane();
    @Override
    public void start(Stage primaryStage) {
        //Aqui crearemos una escena  con las medidas elegidas en las variables
        Scene scene = new Scene (root, SCENE_TAM_X, SCENE_TAM_Y, Color.BLACK);
        primaryStage.setTitle("PONG");
        primaryStage.setScene(scene);
        primaryStage.show();
        //Creacion del circulo 
        Circle circleBall = new Circle();
        //Posicionar respecto xy el centro del circulo
        circleBall.setCenterX(valorX);
        circleBall.setCenterY(valorY);
        //Tamaño del radio del circulo
        circleBall.setRadius(radioCirculo);
        //AHORA PARA MOSTRAR EL CIRCULO EN LA VENTANA  HABRAQUE AÑADIR EL CIRCULO A LA VENTANA
        root.getChildren().add(circleBall);
        //CAMBIAR COLOR A LA BOLA POR EL CODIGO RGB
        circleBall.setFill(Color.web("#51FE00"));
        drawNet(10,4,30);
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
        rectStick.setFill(Color.GREEN);
        //añadiremos el rectangulo o pala como hijo del panel principal 
        root.getChildren().add(rectStick);
        //LAYOUTS PARA MOSTRAR PUNTUACIONES
        //Layout principal
        HBox paneScore = new HBox();
        paneScore.setTranslateY(20);
        paneScore.setMinWidth(SCENE_TAM_X);
        paneScore.setAlignment(Pos.CENTER);
        paneScore.setSpacing(100);
        root.getChildren().add(paneScore);
        //Layout para puntuacion actual
        HBox paneCurrentScore = new HBox();
        paneCurrentScore.setSpacing(10);
        paneScore.getChildren().add(paneCurrentScore);
        //Layout para puntuacion maxima
        HBox paneHightScore = new HBox();
        paneHightScore.setSpacing(10);
        paneScore.getChildren().add(paneHightScore);
        //Texto de etiqueta para la puntuacion
        Text textTitleScore = new Text("Score:");
        textTitleScore.setFont(Font.font(TEXT_SIZE));
        textTitleScore.setFill(Color.WHITE);
        //Texto para la puntuacion
        textScore = new Text("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.WHITE);
        //Texto de etiqueta para la puntuacion maxima
        Text textTitleHighScore = new Text("Max.Score");
        textTitleHighScore.setFont(Font.font(TEXT_SIZE));
        textTitleHighScore.setFill(Color.WHITE);
        //Texto para la puntuacion maxima
        Text textHighScore = new Text("0");
        textHighScore.setFont(Font.font(TEXT_SIZE));
        textHighScore.setFill(Color.WHITE);
        //Añadir los textos a los layouts reservados para ellos
        paneCurrentScore.getChildren().add(textTitleScore);
        paneCurrentScore.getChildren().add(textScore);
        paneHightScore.getChildren().add(textTitleHighScore);
        paneHightScore.getChildren().add(textHighScore);
        this.ResetGame();
        ///////////////////////////////////////////////////ANIMATION TIMER///////////////////////////////////////
        AnimationTimer animationBall = new AnimationTimer(){
         
            @Override
            public void handle(long now) {
                circleBall.setCenterX(ballCenterX);        
                ballCenterX += ballCurrentSpeedX;
                //comprobar si la bola ha tocado el lado derecho
                if(ballCenterX >= SCENE_TAM_X) {
                    //comprobar si hay una nueva puntuacion mas alta
                    if (score > highScore) {
                        //cambiar nueva puntuacion mas alta
                        highScore = score;
                        textHighScore.setText(String.valueOf(highScore));
                    }
                    ResetGame();
                    //Reiniar partida
                    score = 0;
                    textScore.setText(String.valueOf(score));
                    ballCenterX = 10;
                    ballCurrentSpeedy = 3;
                }
                if(ballCenterX <= 0){
                    ballCurrentSpeedX = 3;
                }
                circleBall.setCenterY(ballCentery);        
                ballCentery += ballCurrentSpeedy;
                if(ballCentery >= SCENE_TAM_Y) {
                    ballCurrentSpeedy = -3;
                }
                if(ballCentery <= 0){
                    ballCurrentSpeedy = 3;
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
                //añadiremos a la clase de circulo y rectangulo que sea un colider y te diga si da o no
                Shape.intersect(circleBall, rectStick);
                Shape shapeColision = Shape.intersect(circleBall, rectStick);
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                if (colisionVacia == false && ballCurrentSpeedX > 0) {
                    //colision detectada, mover bola hacia hizquierda
                    ballCurrentSpeedX = -3;
                    //incrementa la puntuacion
                    score++;
                    textScore.setText(String.valueOf(score));
                }
            };  
      
        };
        animationBall.start();
    }

    /**
     * @param args the command line arguments
     */

    private void ResetGame() {
        score = 0;
        textScore.setText(String.valueOf(score));
        ballCenterX = 10;
        ballCurrentSpeedy = 3;
        //posicion inicial aleatoria para la bola en el eje Y
        Random random = new Random();
        ballCentery = random.nextInt(SCENE_TAM_Y);
    }
    private void drawNet(int portionHeight, int portionWidth, int portionSpacing) {
        for(int i=0; i<SCENE_TAM_Y; i+=portionSpacing){
            Line line = new Line(SCENE_TAM_X/2, i, SCENE_TAM_X/2, i+portionHeight);
            line.setStroke(Color.GREEN);
            line.setStrokeWidth(portionWidth);
            root.getChildren().add(line);
            
                    
        }
    }
    private int getStickCollisionZone(Circle ball, Rectangle stick){
        if (Shape.intersect(ball, stick).getBoundsInLocal().isEmpty()){
            return 0;
        }
        else {
            double offsetBallStick = ball.getCenterY()-stick.getY();
            if(offsetBallStick < stick.getHeight() * 0.1) {
                return 1;   
            }
            else if(offsetBallStick < stick.getHeight()/2) {
                return 2;
            } 
            else if (offsetBallStick < stick.getHeight()/2 && offsetBallStick < stick.getHeight() * 0.9){    
                return 3;
            }
            else {
                return 4;
            }    
            
        }
    }
    private void calculateBallSpeed(int collisionZone) {
        switch(collisionZone) {
            case 0:
                //no hay colision
                break;
            case 1:
                //Hay colision esquina superior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedy = -6;
                break;
            case 2:
                 //hay colision lado superior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedy = -3;
                break;
            case 3:
                //Hay colision esquina superior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedy = 3;
                break;
            case 4:
                 //hay colision lado superior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedy = 6;
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}