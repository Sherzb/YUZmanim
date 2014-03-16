/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ 
/*     */ public final class JoyStick extends KeyAdapter
/*     */ {
/*     */   static Wiz3 parent;
/*     */   static int keyD;
/*     */   static int keyU;
/* 114 */   static boolean up = false;
/* 115 */   static boolean down = false;
/* 116 */   static boolean left = false;
/* 117 */   static boolean right = false;
/* 118 */   static boolean fire = false;
/* 119 */   static boolean change = false;
/* 120 */   static boolean fireflag = true;
/* 121 */   static boolean upflag = true;
/* 122 */   static boolean downflag = false;
/* 123 */   static boolean pauseflag = true;
/* 124 */   static int kr = 39;
/* 125 */   static int kl = 37;
/* 126 */   static int ku = 38;
/* 127 */   static int kd = 40;
/* 128 */   static int kf = 32;
/* 129 */   static int kf2 = 17;
/* 130 */   static int kk = 113;
/* 131 */   static int kp = 80;
/* 132 */   static int kc = 67;
/*     */ 
/*     */   public JoyStick(Wiz3 parent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void keyPressed(KeyEvent e)
/*     */   {
/*  16 */     keyD = e.getKeyCode();
/*  17 */     if (keyD == kr)
/*  18 */       right = true;
/*  19 */     else if (keyD == kl) left = true;
/*  20 */     if ((keyD == ku) && (upflag)) {
/*  21 */       up = true;
/*  22 */       upflag = false;
/*  23 */     } else if (keyD == kd) { down = true; }
/*  24 */     if ((keyD == kf) && (fireflag)) {
/*  25 */       fire = true;
/*  26 */       fireflag = false;
/*  27 */     } else if ((keyD == kf2) && (fireflag)) {
/*  28 */       fire = true;
/*  29 */       fireflag = false;
/*     */     }
/*  31 */     if ((keyD == kp) && (pauseflag)) {
/*  32 */       pauseflag = false;
/*  33 */       if ((parent.gameState == Wiz3.PLAYING) && (Score.fade == 0))
/*  34 */         parent.gameState = Wiz3.PAUSED;
/*  35 */       else if (parent.gameState == Wiz3.PAUSED)
/*  36 */         parent.gameState = Wiz3.PLAYING;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void keyReleased(KeyEvent e) {
/*  41 */     keyU = e.getKeyCode();
/*  42 */     if (keyU == kr)
/*  43 */       right = false;
/*  44 */     else if (keyU == kl) left = false;
/*  45 */     if ((keyU == ku) && (!upflag)) {
/*  46 */       up = false;
/*  47 */       upflag = true;
/*  48 */     } else if (keyU == kd) {
/*  49 */       down = false;
/*  50 */       downflag = false;
/*     */     }
/*  52 */     if ((keyU == kf) && (!fireflag)) {
/*  53 */       fire = false;
/*  54 */       fireflag = true;
/*  55 */     } else if ((keyU == kf2) && (!fireflag)) {
/*  56 */       fire = false;
/*  57 */       fireflag = true;
/*     */     }
/*  59 */     if ((keyU == kk) && (!change)) change = true;
/*  60 */     if ((keyU == kp) && (!pauseflag)) pauseflag = true; 
/*     */   }
/*     */ 
/*     */   public static void keyChange(Wiz3 parent, JoyStick j)
/*     */   {
/*  64 */     kl = getKey(parent, j, "Press key for Left:");
/*  65 */     kr = getKey(parent, j, "Press key for Right:");
/*  66 */     ku = getKey(parent, j, "Press key for Up:");
/*  67 */     kd = getKey(parent, j, "Press key for Down:");
/*  68 */     kf = getKey(parent, j, "Press key for Jump:");
/*  69 */     kp = getKey(parent, j, "Press key for Pause:");
/*  70 */     parent.saveCookie();
/*  71 */     change = false;
/*     */   }
/*     */ 
/*     */   private static int getKey(Wiz3 parent, JoyStick j, String s) {
/*  75 */     Graphics g = parent.getGraphics();
/*  76 */     g.clearRect(16, 120, 448, 16);
/*  77 */     g.drawString(s, 16, 132);
/*  78 */     keyD = 0;
/*  79 */     keyU = 0;
/*  80 */     while (keyD == 0)
/*     */       try {
/*  82 */         Thread.sleep(10L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException)
/*     */       {
/*  86 */         break;
/*     */       }
/*  88 */     g.drawString(KeyEvent.getKeyText(keyD), 160, 132);
/*  89 */     while (keyU == 0)
/*     */       try {
/*  91 */         Thread.sleep(1000L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException1)
/*     */       {
/*  95 */         break;
/*     */       }
/*  97 */     g.dispose();
/*  98 */     return keyD;
/*     */   }
/*     */ 
/*     */   static int getGameState(int gameState) {
/* 102 */     if (keyU == 83) gameState = Wiz3.NEWGAME;
/* 103 */     if (keyU == 82) gameState = Wiz3.RESUME;
/* 104 */     return gameState;
/*     */   }
/*     */ 
/*     */   static void showPaused(Graphics g) {
/* 108 */     Score.showPaused(g, KeyEvent.getKeyText(kp));
/*     */   }
/*     */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     JoyStick
 * JD-Core Version:    0.6.2
 */