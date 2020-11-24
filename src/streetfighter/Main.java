package streetfighter;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import streetfighter.database.ConnexionBD;

public class Main extends Application {
    private final static int WIDTH = 1306, HEIGHT = 560;
    static Music music = new Music();
    static FightManager fightManager;
    private static Stage pStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        pStage = primaryStage;
        music.playMusic();
        // Déclarations JavaFX
        AnchorPane root = new AnchorPane();
        primaryStage.setTitle("Street Fighter");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
        // Affichage du logo
        Image IconeImage = new Image("streetfighter/Background/Icone.png");
        primaryStage.getIcons().add(IconeImage);
        
        // Affichage du splashscreen
        final Image backgroundImageSC = new Image("streetfighter/Background/SplashScreenBackground.gif"); // Créer le background du Splash Screen
        Background background = new Background(0,0, WIDTH, HEIGHT, backgroundImageSC);
        root.getChildren().add(background.renderer);
        final Image logoImageSC = new Image("streetfighter/Background/StreetFighterLogo.png"); // Créer le Logo du Splash Screen
        ImageView logoImageViewSC = new ImageView(logoImageSC);
        AnchorPane.setTopAnchor(logoImageViewSC, 10.0); 
        AnchorPane.setLeftAnchor(logoImageViewSC, 653-(logoImageSC.getWidth())/2); // Positionne l'image au centre
        root.getChildren().add(logoImageViewSC);
        final Image PressAnyKeyImageSC = new Image("streetfighter/Background/PressAnyKey.png", 425, 50, false, false); // Créer le texte du Splash Screen
        ImageView PressAnyKeyImageViewSC = new ImageView(PressAnyKeyImageSC);
        AnchorPane.setTopAnchor(PressAnyKeyImageViewSC, 500.0); 
        AnchorPane.setLeftAnchor(PressAnyKeyImageViewSC, 653-(PressAnyKeyImageSC.getWidth())/2); // Positionne l'image au centre
        root.getChildren().add(PressAnyKeyImageViewSC);
     
        
        // Appuyer pour démarrer
        primaryStage.getScene().setOnKeyPressed(event -> {
            root.getChildren().removeAll(root.getChildren()); // Retire tous les éléments de la collection, peu importe l'allure du splash-screen
            startMenu(root);
        });
        
        
    }
    public static Stage getPrimaryStage() {
        return pStage;
    }
    
    // startMenu : lancer le menu
    public static void startMenu(AnchorPane root) {
        // Fond du Menu
        final Image menuBackgroundImageSC = new Image("streetfighter/Background/SplashScreenBackground.gif");
        Background background = new Background(0,0, WIDTH, HEIGHT, menuBackgroundImageSC);
        root.getChildren().add(background.renderer);
        
        // Image Texte du menu
        final Image menuImage = new Image("streetfighter/Menu/Menu.png"); 
        ImageView menuImageView = new ImageView(menuImage);
        AnchorPane.setTopAnchor(menuImageView, 10.0); 
        AnchorPane.setLeftAnchor(menuImageView, 653-(menuImage.getWidth())/2); 
        root.getChildren().add(menuImageView);
        
        // Image Texte play orange
        final Image playImageO = new Image("streetfighter/Menu/PlayOrange.png"); 
        ImageView playImageViewO = new ImageView(playImageO);
        AnchorPane.setTopAnchor(playImageViewO, 200.0); 
        AnchorPane.setLeftAnchor(playImageViewO, 653-(playImageO.getWidth())/2);
        root.getChildren().add(playImageViewO);
        // Image Texte play jaune
        final Image playImageY = new Image("streetfighter/Menu/PlayYellow.png"); 
    
        // Switch de couleur lorque l'on passe la souris sur l'image
        playImageViewO.setOnMouseEntered(e->    { playImageViewO.setImage(playImageY); }  );
        playImageViewO.setOnMouseExited(e->     { playImageViewO.setImage(playImageO); }  );
        // Lorsque l'on clique sur play, le combat commence
        playImageViewO.setOnMouseClicked(e-> {
                root.getChildren().removeAll(root.getChildren()); 
                startGame(root);
            });
        
        // Image Texte Music OFF orange
        final Image musicOFFOImage = new Image("streetfighter/Menu/MusicOFFO.png", 194.5, 46.5, false, false);
        ImageView musicOFFOImageView = new ImageView(musicOFFOImage);
        AnchorPane.setTopAnchor(musicOFFOImageView, 500.0); 
        AnchorPane.setLeftAnchor(musicOFFOImageView, (653-(musicOFFOImage.getWidth())/2)-150); 
        root.getChildren().add(musicOFFOImageView);
        // Image Texte Music OFF jaune
        final Image musicOFFYImage = new Image("streetfighter/Menu/MusicOFFY.png", 194.5, 46.5, false, false);
        
        // Switch de couleur lorque l'on passe la souris sur l'image
        musicOFFOImageView.setOnMouseEntered(e->    { musicOFFOImageView.setImage(musicOFFYImage);} );
        musicOFFOImageView.setOnMouseExited(e->     { musicOFFOImageView.setImage(musicOFFOImage);} );
        // Lorsque l'on clique sur Music OFF, la musique se met en pause
        musicOFFOImageView.setOnMouseClicked(e-> {
                music.getMediaPlayer().pause();
            });
        // Image Texte Music ON orange
        final Image musicONOImage = new Image("streetfighter/Menu/MusicONO.png", 194.5, 46.5, false, false);
        ImageView musicONOImageView = new ImageView(musicONOImage);
        AnchorPane.setTopAnchor(musicONOImageView, 500.0); 
        AnchorPane.setLeftAnchor(musicONOImageView, (653-(musicONOImage.getWidth())/2)+150); 
        root.getChildren().add(musicONOImageView);
        // Image Texte Music ON jaune
        final Image musicONYImage = new Image("streetfighter/Menu/MusicONY.png", 194.5, 46.5, false, false);
        
        // Switch de couleur lorque l'on passe la souris sur l'image
        musicONOImageView.setOnMouseEntered(e->     { musicONOImageView.setImage(musicONYImage);} );
        musicONOImageView.setOnMouseExited(e->      { musicONOImageView.setImage(musicONOImage);} );
        // Lorsque l'on clique sur Music ON, la musique se met en play
        musicONOImageView.setOnMouseClicked(e-> {
                music.getMediaPlayer().play();
            });
        
        // Image Texte controls orange
        final Image controlsImageO = new Image("streetfighter/Menu/ControlsO.png", 254.1, 70, false, false);  
        ImageView controlsImageViewO = new ImageView(controlsImageO);
        AnchorPane.setTopAnchor(controlsImageViewO, 340.0); 
        AnchorPane.setLeftAnchor(controlsImageViewO, 653-(controlsImageO.getWidth())/2);
        root.getChildren().add(controlsImageViewO);
        // Image Texte controls jaune
        final Image controlsImageY = new Image("streetfighter/Menu/ControlsY.png", 254.1, 70, false, false);
    
        // Switch de couleur lorque l'on passe la souris sur l'image
        controlsImageViewO.setOnMouseEntered(e->    { controlsImageViewO.setImage(controlsImageY); }  );
        controlsImageViewO.setOnMouseExited(e->     { controlsImageViewO.setImage(controlsImageO); }  );
        // Lorsque l'on clique sur controls, la fenetre des controls s'ouvre
        controlsImageViewO.setOnMouseClicked(e-> {
                root.getChildren().removeAll(root.getChildren()); 
                startControls(root);
            });
        
        // Image Texte Quit orange
        final Image quitImageO = new Image("streetfighter/Menu/QuitO.png", 157.6, 80, false, false); 
        ImageView quitImageViewO = new ImageView(quitImageO);
        AnchorPane.setTopAnchor(quitImageViewO, 420.0); 
        AnchorPane.setLeftAnchor(quitImageViewO, 653-(quitImageO.getWidth())/2);
        root.getChildren().add(quitImageViewO);
        // Image Texte Quit jaune
        final Image quitImageY = new Image("streetfighter/Menu/QuitY.png", 157.6, 80, false, false); 
    
        // Switch de couleur lorque l'on passe la souris sur l'image
        quitImageViewO.setOnMouseEntered(e->    { quitImageViewO.setImage(quitImageY); }  );
        quitImageViewO.setOnMouseExited(e->     { quitImageViewO.setImage(quitImageO); }  );
        // Lorsque l'on clique sur quit, la fenetre se ferme
        quitImageViewO.setOnMouseClicked(e-> {
                getPrimaryStage().close();
            });
        
        // GIFS decoration du menu
        final Image ryuMenuImage = new Image("streetfighter/Menu/RyuMenu.gif", 169.1, 573.8, false, false);
        ImageView ryuMenuImageView = new ImageView(ryuMenuImage);
        AnchorPane.setTopAnchor(ryuMenuImageView, -10.0); 
        AnchorPane.setLeftAnchor(ryuMenuImageView, (653-(ryuMenuImage.getWidth())/2)-570); 
        root.getChildren().add(ryuMenuImageView);
        final Image kenMenuImage = new Image("streetfighter/Menu/KenMenu.gif", 218.5, 729.6, false, false);
        ImageView KenMenuImageView = new ImageView(kenMenuImage);
        AnchorPane.setTopAnchor(KenMenuImageView, -165.0); 
        AnchorPane.setLeftAnchor(KenMenuImageView, (653-(kenMenuImage.getWidth())/2)+530); 
        root.getChildren().add(KenMenuImageView);
        
        // Selection de profile
        // TF 1
        TextField player1TF = new TextField();
        player1TF.setPrefWidth(120);
        player1TF.setPrefHeight(40);
        AnchorPane.setTopAnchor(player1TF, 350.0); 
        AnchorPane.setLeftAnchor(player1TF, 250.0); 
        player1TF.setPromptText("P1 Name");
        player1TF.setAlignment(Pos.CENTER);
        root.getChildren().add(player1TF);
        // TF 2
        TextField player2TF = new TextField();
        player2TF.setPrefWidth(120);
        player2TF.setPrefHeight(40);
        AnchorPane.setTopAnchor(player2TF, 350.0); 
        AnchorPane.setRightAnchor(player2TF, 250.0); 
        player2TF.setPromptText("P2 Name");
        player2TF.setAlignment(Pos.CENTER);
        root.getChildren().add(player2TF);
        
        
        Platform.runLater( () -> root.requestFocus());
    }
    
    public static void startControls(AnchorPane root) {
        // Fond des Controls
        final Image menuBackgroundImageSC = new Image("streetfighter/Background/SplashScreenBackground.gif");
        Background background = new Background(0,0, WIDTH, HEIGHT, menuBackgroundImageSC);
        root.getChildren().add(background.renderer);

        // IMAGE des controles
        final Image ctrlImageO = new Image("streetfighter/Menu/ctrl.png", 1231, 254, false, false); 
        ImageView ctrlImageViewO = new ImageView(ctrlImageO);
        AnchorPane.setTopAnchor(ctrlImageViewO, 100.0); 
        AnchorPane.setLeftAnchor(ctrlImageViewO, (653-(ctrlImageO.getWidth())/2-50));  
        root.getChildren().add(ctrlImageViewO);
        
        // Image Texte back orange
        final Image backImageO = new Image("streetfighter/Menu/BackO.png"); 
        ImageView backImageViewO = new ImageView(backImageO);
        AnchorPane.setTopAnchor(backImageViewO, 460.0); 
        AnchorPane.setLeftAnchor(backImageViewO, 30.0); 
        root.getChildren().add(backImageViewO);
        // Image Texte back jaune
        final Image backImageY = new Image("streetfighter/Menu/BackY.png"); 
    
        // Switch de couleur lorque l'on passe la souris sur l'image
        backImageViewO.setOnMouseEntered(e->    { backImageViewO.setImage(backImageY); }  );
        backImageViewO.setOnMouseExited(e->     { backImageViewO.setImage(backImageO); }  );
        // Lorsque l'on clique sur back, la fenetre se ferme
        backImageViewO.setOnMouseClicked(e-> {
                root.getChildren().removeAll(root.getChildren()); 
                startMenu(root);
            });
    }
    
    // startGame : démarrer le Timer de la boucle principale du jeu
    // root : AnchorPane : l'élement parent principal, créé dans start()
    public static void startGame(AnchorPane root) {
        // Initialisations et ajouts des gameObjects

       
        final Image backgroundImage = new Image("streetfighter/Background/Background1.gif"); // Ici est créée l'image (à partir de l'URL) afin de l'utiliser dans streetfighter.streetfighter.Background
        Background background = new Background(0,0, WIDTH, HEIGHT, backgroundImage);
        
        Ryu ryu = new Ryu(20, HEIGHT-222, 156, 222, 7);
        Ken ken = new Ken(1000, HEIGHT-222 , 156, 222, 7);
        
        fightManager = new FightManager(ryu,ken,background,root);
        fightManager.initializeFight();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        fightManager.instance.stop();
    }

    public static void main(String[] args) throws SQLException {
        ConnexionBD con;
        con = new ConnexionBD("jdbc:derby:StreetFighterDB");
        con.ConnexionBDD();
        launch(args);
    }
}