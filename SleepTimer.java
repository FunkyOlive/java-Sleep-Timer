import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
  *
  * Beschreibung
  *
  * @version 0.0.2 vom 29.11.2013
  * @author WrstFngr
  */
                                
public class SleepTimer extends JFrame {
  // Anfang Attribute
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JSlider jSlider1 = new JSlider();
  private JButton jButton1 = new JButton();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  // Ende Attribute
  
  int timeSet;
  int timeCurr;
  Thread Monitoring = null;
  boolean countdown = false;
  
  public SleepTimer(String title) {                             
    // Frame-Initialisierung
    super(title);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 252; 
    int frameHeight = 192;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // Anfang Komponenten
    jLabel1.setBounds(24, 8, 210, 49);
    jLabel1.setText("Sleep Timer");
    jLabel1.setFont(new Font("Consolas", Font.BOLD, 32));
    jLabel1.setOpaque(false);                                   
    cp.add(jLabel1);
    jLabel2.setBounds(188, 144, 62, 20);
    jLabel2.setText("by WrstFngr");
    jLabel2.setFont(new Font("Consolas", Font.PLAIN, 9));
    cp.add(jLabel2);
    jLabel3.setBounds(8, 96, 121, 20);
    jLabel3.setText("Minutes To Shutdown");
    jLabel3.setFont(new Font("Consolas", Font.PLAIN, 10));
    cp.add(jLabel3);
    jSlider1.setBounds(2, 48, 238, 54);
    jSlider1.setMinorTickSpacing(15);
    jSlider1.setMajorTickSpacing(15);
    jSlider1.setPaintTicks(true);
    jSlider1.setPaintLabels(true);
    jSlider1.setMaximum(120);
    jSlider1.setMinimum(0);
    jSlider1.setFont(new Font("Consolas", Font.PLAIN, 11));
    jSlider1.setValue(45);
    cp.add(jSlider1);
    jButton1.setBounds(44, 116, 147, 25);
    jButton1.setText("Start  Countdown");
    jButton1.setMargin(new Insets(2, 2, 2, 2));
    jButton1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton1_ActionPerformed(evt);
      }
    });
    jButton1.setFont(new Font("Consolas", Font.PLAIN, 12));
    cp.add(jButton1);
    jLabel4.setBounds(16, 64, 220, 36);
    jLabel4.setText("Shutting down in 60 seconds");
    jLabel4.setFont(new Font("Consolas", Font.BOLD, 14));
    cp.add(jLabel4);
    addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent evt) { 
        sleepTimer_WindowClosing(evt);
      }
    });
    jLabel5.setBounds(2, 144, 62, 20);
    jLabel5.setText("Ver 0.0.2");
    jLabel5.setFont(new Font("Consolas", Font.PLAIN, 9));
    cp.add(jLabel5);
    // Ende Komponenten
    
    setVisible(true);
  } // end of public SleepTimer
  
  // Anfang Methoden
  public void jButton1_ActionPerformed(ActionEvent evt) {
    countdown = false;
    timeSet = jSlider1.getValue();
    timeCurr = timeSet;     
    if (Monitoring==null) {
      jButton1.setText("Stop Countdown");
      Monitoring = new Thread() {
        public void run() { 
          while (timeCurr>1) {                                     
            try { 
              Thread.sleep(6000);
              timeCurr--;
              jSlider1.setValue(timeCurr);
            } catch(Exception e) {}  // end of try
          } // end of while
          
          countdown = true;
          jSlider1.setVisible(false);
          jLabel3.setVisible(false);
          jLabel4.setVisible(true);
          jButton1.setText("Interrupt Countdown");
          timeCurr = 60;
          jLabel4.setText("Shutting down in "+timeCurr+" seconds");
          while (timeCurr>0) { 
            try {
              Thread.sleep(1000);
              timeCurr--;
              jLabel4.setText("Shutting down in "+timeCurr+" seconds" );
            } catch(Exception e) {} // end of try
          } // end of while          
          try {                                                 
            Runtime.getRuntime().exec("shutdown /p /f /t 30 ");
          } catch (Exception ex) {
            System.out.println("Shutdown failed");
          } //end of try
        }  //end of public void run                   
      };
      Monitoring.start();
    } // end of if 
    else {
      jButton1.setText("Resume Countdown");
      Monitoring.stop();
      Monitoring=null;
      if (countdown = true) {
        jSlider1.setVisible(true);
        jLabel3.setVisible(true);
        jLabel4.setVisible(false);
      } // end of if
    } // end of if-else
  } // end of jButton1_ActionPerformed
  
  public void sleepTimer_WindowClosing(WindowEvent evt) {
    if (Monitoring !=null) {
      Monitoring.stop();
    } // end of if 
  } // end of sleepTimer_WindowClosing
  
  // Ende Methoden
  
  public static void main(String[] args) {
    new SleepTimer("SleepTimer");
  } // end of main
  
} // end of class SleepTimer
