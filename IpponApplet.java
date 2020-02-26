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
  int default_point = 0; // 下駄はかせるときはここ
  int playerNum = 5; // 人数変更するときはここ
  int match = 1; // 何試合目か

  // プレイヤー設定
  String[] playerName = {
    "",
    "",
    "",
    "",
    "", // ここまで1回戦
    "",
    "",
    "",
    "",
    "", // ここまで2回戦
    "",
    "",
    "",
    "",
    "" // ここまで3回戦
  };

  //お題設定
  String[] odai = {
    "お題Aお題Aお題Aお題Aお題Aお題Aお題Aお題Aお題Aお題A",
    "お題Bお題Bお題Bお題Bお題Bお題Bお題Bお題Bお題Bお題B",
    "お題Cお題Cお題Cお題Cお題Cお題Cお題Cお題Cお題Cお題C",
    "お題Dお題Dお題Dお題Dお題Dお題Dお題Dお題Dお題Dお題D",
    "お題Eお題Eお題Eお題Eお題Eお題Eお題Eお題Eお題Eお題E"
  };

  int count=0;
  int playerCount=-1;
  int width = 42;
  int stroke=14;

  int odaiNum = 0;
  Image point[] = new Image[11];
  Image player[] = new Image[playerNum];
  int playerPoint[] = new int[playerNum];

  AudioClip audioClip[] = new AudioClip[12];

  Dimension size;
  Image back;
  Graphics buffer;
  Font font = new Font("SansSerif",Font.BOLD,40);

  public void init() {
    count = default_point;
    // ダブルバッファリング設定
    size = getSize();
    back = createImage(size.width, size.height);
    buffer = back.getGraphics();

    // 画像読み込み
    for (int i=0; i<11; i++) {
     point[i] = getImage(getDocumentBase(),"img/"+i+".png");
    }

    // 音声読み込み
    try {
      for (int i=1; i<11; i++) {
        audioClip[i] = Applet.newAudioClip(IpponApplet.class.getResource("snd/"+i+".wav"));
      }
      audioClip[11] = Applet.newAudioClip(IpponApplet.class.getResource("snd/answer.wav"));
   } catch (Exception e) {

   }

   // 画像表示
    for (int i=(match-1)*5; i<(match-1)*5+5; i++) {
      player[i] =  getImage(getDocumentBase(),"player/"+playerName[i]+".png");
    }

    resize(1366, 768);

    addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
          switch ( key ) {
            case 32: // space
              count=default_point; playerCount = -1; break;
            case 37: // left
              if (playerCount>-1) playerCount--; break;
            case 39: // right
              if (playerCount<playerNum-1) playerCount++; break;
            case 38: // up
            if (count<10) {
              count++;
              if (count>=1 && count<=10) {
                audioClip[count].play();
                if (count==10 && playerCount != -1) playerPoint[playerCount]++;
              }
            }
             break;
            case 40: // down
              if (count>0) count--; break;
            case 49: // 1
              if (playerCount == -1) {
                playerCount = 0;
                audioClip[11].stop();
                audioClip[11].play();
              }
              break;
            case 50: // 2
              if (playerCount == -1) {
                playerCount = 1;
                audioClip[11].stop();
                audioClip[11].play();
              }
              break;
            case 51: // 3
              if (playerCount == -1) {
                playerCount = 2;
                audioClip[11].stop();
                audioClip[11].play();
              }
              break;
            case 52: // 4
              if (playerCount == -1) {
                playerCount = 3;
                audioClip[11].stop();
                audioClip[11].play();
              }
              break;
            case 53: // 5
              if (playerCount == -1) {
                playerCount = 4;
                audioClip[11].stop();
                audioClip[11].play();
              }
              break;
            case 66: // back space
              if(odaiNum>0) odaiNum--; break;
            case 74: // j
              if (playerCount<3) {
                playerCount++;
              } else if (playerCount==3) {
                playerCount=0;
              }
              break;
            case 78: // n
              if(odaiNum<3) odaiNum++; break; // enter
            case 80: // p
            if (count<10) {
              count++;
              if (count>=1 && count<=10) {
                audioClip[count].play();
                if (count==10 && playerCount != -1) playerPoint[playerCount]++;
              }
            }
             break;
            case 81: // q
              count=default_point; playerCount = -1; break;
            case 85: // u
              playerPoint[playerCount]++; break;
            case 68: // d
              if (playerPoint[playerCount] > 0) playerPoint[playerCount]--; break;
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
// テキストフィールドにフォーカスが移ったときに実行する処理
 //  public void focusGained(FocusEvent event) {
 //    if ( event.getSource() == odai ) {
 //      odai.setEnabled(true);
 //    }
 //  }
 //
 //  public void focusLost(FocusEvent event) {
 //   if ( event.getSource() == odai ) {
 //      odai.setEnabled(false);
 //   }
 // }


  public void update(Graphics g) {
    paint(g);
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    //buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    buffer.setColor(Color.yellow);
    buffer.fillRect(0, 0, size.width, size.height);

    if (playerCount != -1) {
      buffer.drawImage(player[playerCount], 0, 0, 1366, 768, this);
      buffer.setColor(Color.black);

      if (playerPoint[playerCount]>=1) {
        for (int i=0; i<playerPoint[playerCount]; i++) {
          buffer.fillRect(0, 100+default_point*35+70*i, 230, 50);
          if (i >= 6) break;
        }
      }

      if (playerPoint[playerCount]>=8) {
        for (int i=0; i<playerPoint[playerCount]-8; i++) {
          buffer.fillRect(1136, 100+default_point*35+70*i, 230, 50);
          if (i >= 16) break;
        }
      }
    }

    buffer.drawImage(point[0], 0, 0, 1366, 768, this);

    for (int i=1; i<=count; i++) {
      buffer.drawImage(point[i], 0, 0, 1366, 768, this);
    }

    buffer.setFont(font);
    buffer.setColor(Color.white);
    buffer.drawString(odai[odaiNum], 20, 50);
    //if (playerCount != -1) {
    //  buffer.drawString(String.valueOf(playerPoint[playerCount]), 100, 50);
    //}

    g2.drawImage(back, 0, 0, this);
  }
}
