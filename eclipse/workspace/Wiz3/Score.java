/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ 
/*     */ public final class Score
/*     */ {
/* 236 */   static int score = 0;
/* 237 */   static int hiscore = 0;
/* 238 */   static int lives = 5;
/* 239 */   static int potion = 124;
/* 240 */   static boolean key = false;
/*     */   static Graphics g2;
/*     */   static Image alpha;
/* 243 */   static int flash = 0;
/* 244 */   static int progressC = 0;
/* 245 */   static int progressM = 64;
/* 246 */   static String progressT = "";
/* 247 */   static int fade = 0;
/*     */   static final int FADEIN = 128;
/*     */   static final int FADEOUT = -128;
/*     */   static final int CENTER = -1;
/* 251 */   static int powerup = 0;
/* 252 */   static int maxJumps = 1;
/* 253 */   static int highJump = -36;
/* 254 */   static int invincible = 0;
/* 255 */   static boolean invisible = false;
/* 256 */   static boolean asbestos = false;
/* 257 */   static boolean restore = false;
/*     */ 
/*     */   public static void init(Image i)
/*     */   {
/*  17 */     progressM = 64;
/*  18 */     progressC = 0;
/*  19 */     progressT = "";
/*  20 */     lives = 3;
/*  21 */     potion = 124;
/*  22 */     score = 0;
/*  23 */     fade = 0;
/*  24 */     key = false;
/*  25 */     alpha = i;
/*  26 */     flash = 0;
/*  27 */     invincible = 0;
/*  28 */     resetPowerups();
/*  29 */     restore = false;
/*     */   }
/*     */ 
/*     */   public static void nextPowerup(int x, int y, BobManager bm)
/*     */   {
/*  34 */     powerup += 1;
/*  35 */     if (powerup == 1) {
/*  36 */       highJump = -40;
/*     */     }
/*  38 */     else if (powerup == 2) {
/*  39 */       invisible = true;
/*     */     }
/*  41 */     else if (powerup == 3) {
/*  42 */       maxJumps = 2;
/*     */     }
/*  44 */     else if (powerup == 4) {
/*  45 */       asbestos = true;
/*     */     }
/*  47 */     else if (powerup == 5) {
/*  48 */       bm.createBob(0, 0, 0, 0, 16, 0);
/*     */     }
/*  50 */     else if (powerup == 6) {
/*  51 */       restore = true;
/*     */     }
/*  53 */     else if (powerup > 6)
/*     */     {
/*  55 */       extraMan(x, y, bm);
/*  56 */       powerup = 6;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void restorePowerups()
/*     */   {
/*  62 */     powerup = 5;
/*  63 */     maxJumps = 2;
/*  64 */     highJump = -40;
/*  65 */     invisible = true;
/*  66 */     asbestos = true;
/*  67 */     restore = false;
/*     */   }
/*     */ 
/*     */   public static void resetPowerups()
/*     */   {
/*  72 */     powerup = 0;
/*  73 */     maxJumps = 1;
/*  74 */     highJump = -36;
/*  75 */     invisible = false;
/*  76 */     asbestos = false;
/*     */   }
/*     */ 
/*     */   public static void addScore(int s)
/*     */   {
/*  81 */     score += s;
/*     */   }
/*     */ 
/*     */   public static void extraMan(int x, int y, BobManager bm)
/*     */   {
/*  86 */     lives += 1;
/*  87 */     bm.createBob(x, y, 0, 0, 10, 2);
/*  88 */     bm.createBob(0, 0, 0, 0, 12, 0);
/*     */   }
/*     */ 
/*     */   public static void incPotion()
/*     */   {
/*  93 */     potion -= 1;
/*  94 */     addScore(100);
/*     */   }
/*     */ 
/*     */   public static void paint(Graphics g)
/*     */   {
/*  99 */     if (fade > 0)
/*     */     {
/* 101 */       fade -= 8;
/* 102 */       g.fillRect(16, 0, 480, fade);
/* 103 */       g.fillRect(16, 256 - fade, 480, 128);
/* 104 */       g.fillRect(16, 0, fade, 256);
/* 105 */       g.fillRect(496 - fade, 0, 480, 256);
/*     */     }
/* 107 */     else if (fade < 0)
/*     */     {
/* 109 */       fade += 4;
/* 110 */       g.fillRect(16, 0, 480, 128 + fade);
/* 111 */       g.fillRect(16, 128 - fade, 480, 128);
/*     */     }
/* 113 */     int s = score;
/* 114 */     for (int i = 80; i >= 24; i -= 8)
/*     */     {
/* 116 */       g2 = g.create(i, 8, 8, 16);
/* 117 */       g2.drawImage(alpha, -(s % 10) * 8, -16, null);
/* 118 */       g2.dispose();
/* 119 */       s /= 10;
/*     */     }
/*     */ 
/* 122 */     for (int i = lives * 12 + 12; i >= 24; i -= 12)
/*     */     {
/* 124 */       g2 = g.create(i, 24, 16, 16);
/* 125 */       g2.drawImage(alpha, 0, 0, null);
/* 126 */       g2.dispose();
/*     */     }
/*     */ 
/* 129 */     if (key)
/*     */     {
/* 131 */       g2 = g.create(96, 8, 16, 16);
/* 132 */       g2.drawImage(alpha, -16, 0, null);
/* 133 */       g2.dispose();
/*     */     }
/* 135 */     g2 = g.create(460, 8, 32, 32);
/* 136 */     g2.setClip(0, potion / 4, 32, 32);
/* 137 */     g2.drawImage(alpha, -208, 0, null);
/* 138 */     g2.setClip(0, 0, 32, 32);
/* 139 */     g2.drawImage(alpha, -176, 0, null);
/* 140 */     g2.dispose();
/* 141 */     g2 = g.create(444, 24, 8, 16);
/* 142 */     g2.drawImage(alpha, -((124 - potion) / 10) * 8, -16, null);
/* 143 */     g2.dispose();
/* 144 */     g2 = g.create(452, 24, 8, 16);
/* 145 */     g2.drawImage(alpha, -((124 - potion) % 10) * 8, -16, null);
/* 146 */     g2.dispose();
/* 147 */     g2 = g.create(460 - powerup * 16, 8, powerup * 16, 16);
/* 148 */     g2.drawImage(alpha, -32, 0, null);
/* 149 */     g2.dispose();
/*     */   }
/*     */ 
/*     */   public static void initProgress(int m, String s)
/*     */   {
/* 154 */     progressM = m;
/* 155 */     progressC = 0;
/* 156 */     progressT = s;
/* 157 */     flash = 8;
/*     */   }
/*     */ 
/*     */   public static void incProgress(Wiz3 parent)
/*     */   {
/* 162 */     progressC += 1;
/* 163 */     parent.showProgress();
/*     */   }
/*     */ 
/*     */   public static void showProgress(Graphics g)
/*     */   {
/* 168 */     paint(g);
/* 169 */     paintString(-1, 5, progressT, g);
/* 170 */     g.setColor(Color.blue);
/* 171 */     g.drawRect(220, 128, 68, 7);
/* 172 */     g.setColor(Color.green.brighter().brighter());
/* 173 */     g.drawRect(222, 130, progressC * 64 / progressM, 0);
/* 174 */     g.setColor(Color.green);
/* 175 */     g.drawRect(222, 131, progressC * 64 / progressM, 1);
/* 176 */     g.setColor(Color.green.darker().darker());
/* 177 */     g.drawRect(222, 133, progressC * 64 / progressM, 0);
/* 178 */     g.setColor(Color.black);
/*     */   }
/*     */ 
/*     */   public static void gameOver(Graphics g, int lev)
/*     */   {
/* 183 */     paint(g);
/* 184 */     flash += 1;
/* 185 */     if ((flash & 0x18) != 0)
/* 186 */       paintString(-1, 5, "Game Over", g);
/* 187 */     paintString(-1, 7, "Press S to Start", g);
/* 188 */     if (lev > 1)
/* 189 */       paintString(-1, 9, "Press R to Resume at Level " + lev, g);
/* 190 */     if (hiscore > 0)
/* 191 */       paintString(-1, 11, "Your best score is " + hiscore, g);
/* 192 */     paintString(-1, 13, "©2000 Steve Eaborn", g);
/* 193 */     paintString(-1, 14, "email:eaborn@btinternet.com", g);
/*     */   }
/*     */ 
/*     */   public static void showPaused(Graphics g, String p)
/*     */   {
/* 198 */     paint(g);
/* 199 */     flash += 1;
/* 200 */     if ((flash & 0x18) != 0)
/* 201 */       paintString(-1, 5, "Paused", g);
/* 202 */     paintString(-1, 7, "Press " + p + " to continue", g);
/*     */   }
/*     */ 
/*     */   public static void noFocus(Graphics g)
/*     */   {
/* 207 */     paint(g);
/* 208 */     flash += 1;
/* 209 */     if ((flash & 0x18) != 0)
/* 210 */       paintString(-1, 6, "LOST KEYBOARD FOCUS!", g);
/* 211 */     paintString(-1, 8, "Click mouse here to get it back", g);
/*     */   }
/*     */ 
/*     */   public static void paintString(int x, int y, String s, Graphics g)
/*     */   {
/* 216 */     if (x == -1)
/* 217 */       x = 31 - s.length() / 2;
/* 218 */     for (int i = 0; i < s.length(); i++)
/* 219 */       paintChar(x + i, y, s.charAt(i), g);
/*     */   }
/*     */ 
/*     */   private static void paintChar(int x, int y, int a, Graphics g)
/*     */   {
/* 225 */     a = "0123456789©:.!@/()-,      ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".indexOf(a);
/* 226 */     if (a == -1)
/*     */     {
/* 228 */       return;
/*     */     }
/* 230 */     g2 = g.create(x * 8, y * 16, 8, 16);
/* 231 */     g2.drawImage(alpha, -(a % 26) * 8, -(a / 26) * 16 - 16, null);
/* 232 */     g2.dispose();
/*     */   }
/*     */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     Score
 * JD-Core Version:    0.6.2
 */