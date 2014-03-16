import java.awt.*;

import javax.swing.*;

/**
 * @version 1.32 2007-04-14
 * @author Cay Horstmann
 */
public class SizedFrameTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               SizedFrame frame = new SizedFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

class SizedFrame extends JFrame
{
   public SizedFrame()
   {
      // get screen dimensions

      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screenSize = kit.getScreenSize();
      int screenHeight = screenSize.height;
      int screenWidth = screenSize.width;

      // set frame width, height and let platform pick screen location

      //setSize(screenWidth / 2, screenHeight / 2);
      //setLocationByPlatform(true);
      
      //More simply
      setSize(500, 300);
      
      //Add a panel
      NotHelloWorldPanel panel1 = new NotHelloWorldPanel();
      add(panel1);
   }
   
   class NotHelloWorldPanel extends JPanel
   {
	   public void paintComponent(Graphics g) {
		   g.drawString("Not Hello World", 75, 100);
	   }
   }
}
