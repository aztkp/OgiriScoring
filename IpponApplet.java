import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class IpponApplet extends Applet {
  int count=0;
  int playerCount=-1;
  int width = 42;
  int stroke=14;
  String msg = "test";

  Image point[] = new Image[11];
  Image player[] = new Image[4];

  AudioClip audioClip[] = new AudioClip[11];

  Font font = new Font("SansSerif",Font.BOLD,40);

  public void init() {
    // 画像読み込み
    for (int i=0; i<11; i++) {
     point[i] = getImage(getDocumentBase(),"img/"+i+".png");
    }

    // 音声読み込み
    try {
      for (int i=1; i<11; i++) {
        audioClip[i] = Applet.newAudioClip(IpponApplet.class.getResource("snd/"+i+".wav"));
      }
   } catch (Exception e) {

   }



    player[0] =  getImage(getDocumentBase(),"img/1.png");
    player[1] =  getImage(getDocumentBase(),"img/2.png");
    player[2] =  getImage(getDocumentBase(),"img/3.png");
    player[3] =  getImage(getDocumentBase(),"img/4.png");

    setBackground(Color.yellow);
    addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
          switch ( key ) {
            case 32:
              count=0; playerCount = -1; break;
            case 37:
              if (playerCount>-1) playerCount--; break; // left
            case 39:
              if (playerCount<3) playerCount++; break; // right
            case 38:
              if (count<10) {
                count++;
                if (count>=1 && count<=10) {
                  audioClip[count].play();
                }
              }
               break; // up
            case 40:
              if (count>0) count--; break; // down
            case 49:
              playerCount = 0; break;
            case 50:
              playerCount = 1; break;
            case 51:
              playerCount = 2; break;
            case 52:
              playerCount = 3; break;
          }
        repaint();
        }
      }
    );

    // addMouseListener(
    //   new MouseAdapter() {
    //
    //     public void mousePressed(MouseEvent e) {
    //
    //     }
    //
    //     public void mouseReleased(MouseEvent e) {
    //       if (count<10) {
    //         count++;
    //         repaint();
    //       }
    //     }
    //   }
    // );
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
    resize(1366, 768);


    if (playerCount != -1) {
      g.drawImage(player[playerCount], 0, 0, 1366, 768, this);
    }

    g.drawImage(point[0], 0, 0, 1366, 768, this);

    for (int i=1; i<=count; i++) {
      g.drawImage(point[i], 0, 0, 1366, 768, this);
    }

    g.setFont(font);
    g.setColor(Color.white);
    g.drawString("一度やってみたかった贅沢とは？", 20, 50);
  }
}
