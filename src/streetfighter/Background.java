package streetfighter;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import streetfighter.interfaces.Renderable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import streetfighter.other.GameObject;
/**
 *
 * @author leo
 */
public class Background extends GameObject implements Renderable
{
    
    ImageView renderer;
    
     public Background(double x, double y, double width, double height,Image image) {
        super(x, y, width, height);
        renderer = new ImageView(image);
        renderer.setFitHeight(height);
        renderer.setFitWidth(width);
        
    }
     
     @Override
    public Node getRenderer() {
        return renderer;
    }

    @Override
    public void draw() {
        this.renderer.resizeRelocate(x, y, width, height);
    }

    @Override
    public void update() {
        
    }
}
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//    {
//        private Container c;
//        private JPanel imagePanel;
//        private String filePath;
//        public streetfighter.streetfighter.Background(String filePath)
//        {
//            super("background de la zone de combat");
//            this.filePath = filePath;
//            initialize();
//        }
//        private void initialize()
//        {
//            setDefaultCloseOperation(EXIT_ON_CLOSE);
//            c = getContentPane();
//            imagePanel = new JPanel()
//            {
//                @Override
//                public void paint(Graphics g)
//                {
//                    try
//                    {
//                        BufferedImage image = ImageIO.read(new File(filePath));
//                        g.drawImage(image, 0, 0, null);
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            imagePanel.setPreferredSize(new Dimension(640, 480));
//            c.add(imagePanel);
////        }
//    }
