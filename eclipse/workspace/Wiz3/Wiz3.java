/*     */ import java.applet.Applet;
/*     */ import java.applet.AudioClip;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Locale;
/*     */ import netscape.javascript.JSObject;
/*     */ 
/*     */ public final class Wiz3 extends Applet
/*     */   implements Runnable, FocusListener
/*     */ {
/*  28 */   static int WIDTH = 496; static int HEIGHT = 256;
/*  29 */   static int W = WIDTH; static int H = HEIGHT; static int OFFSET = -16;
/*  30 */   static int ZOOM = 1;
/*     */   static final String info = "Wiz3 v5.0 ©2000,2008 Steve Eaborn (MrE Software)";
/*     */   static Thread runner;
/*     */   static Thread timer;
/*     */   static boolean running;
/*  36 */   private static SimpleDateFormat formatter = new SimpleDateFormat(
/*  37 */     "EEE, dd-MMM-yy HH:mm:ss", Locale.getDefault());
/*     */   static Calendar exp;
/*     */   static JSObject myBrowser;
/*     */   static JSObject myDocument;
/*     */   int fps;
/*     */   int frames;
/*     */   static Image alphaGif;
/*     */   static Image introGif;
/*     */   static Image buffer;
/*     */   static Graphics gBuffer;
/*     */   static Graphics gMain;
/*  48 */   static BobManager bm = null;
/*  49 */   static LandScape ls = null;
/*  50 */   static JoyStick joy = null;
/*  51 */   static int sync = 0;
/*     */   static final long SLEEPTIME = 30L;
/*     */   static Frame f;
/*     */   static String cookie;
/*  55 */   static int startLevel = 1;
/*  56 */   static int FINISHED = -1;
/*  57 */   static int NEWGAME = 0;
/*  58 */   static int RESUME = 1;
/*  59 */   static int LOADING = 2;
/*  60 */   static int GAMEOVER = 3;
/*  61 */   static int PLAYING = 4;
/*  62 */   static int PAUSED = 5;
/*  63 */   static int RESTART = 6;
/*  64 */   static int NOFOCUS = 8;
/*  65 */   static int GOTFOCUS = -9;
/*     */   int gameState;
/*     */ 
/*     */   public Wiz3()
/*     */   {
/*  87 */     this.fps = 0;
/*  88 */     this.frames = 0;
/*  89 */     this.gameState = LOADING;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  93 */     if (args.length > 0) {
/*  94 */       startLevel = Integer.parseInt(args[0]);
/*  95 */       System.out.println("startLevel:" + startLevel);
/*     */     }
/*  97 */     if (args.length > 1) {
/*  98 */       ZOOM = Integer.parseInt(args[1]);
/*  99 */       System.out.println("ZOOM:" + ZOOM);
/* 100 */       W = WIDTH * ZOOM;
/* 101 */       H = HEIGHT * ZOOM;
/* 102 */       OFFSET = -16 * ZOOM;
/*     */     }
/* 104 */     f = new Frame("Wiz3");
/* 105 */     Applet w = new Wiz3();
/* 106 */     f.setIconImage(Loader.getImage("icon.gif", w));
/* 107 */     f.setVisible(true);
/* 108 */     f.setSize(W + OFFSET + f.getInsets().left + f.getInsets().right, H - 
/* 109 */       8 * ZOOM + f.getInsets().top + f.getInsets().bottom);
/* 110 */     f.add(w);
/* 111 */     Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
/* 112 */     f.setLocation((d.width - f.getWidth()) / 2, 
/* 113 */       (d.height - f.getHeight()) / 2);
/* 114 */     f.setResizable(false);
/* 115 */     w.init();
/* 116 */     w.start();
/* 117 */     f.addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent e) {
/* 120 */         Wiz3.this.stop();
/* 121 */         Wiz3.this.destroy();
/* 122 */         System.exit(0);
/*     */       }
/*     */     });
/* 126 */     f.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 131 */     String z = getParameter("ZOOM");
/* 132 */     if (z != null) {
/* 133 */       ZOOM = Integer.parseInt(z);
/*     */     }
/* 135 */     buffer = createImage(WIDTH, HEIGHT);
/* 136 */     W = WIDTH * ZOOM;
/* 137 */     H = HEIGHT * ZOOM;
/* 138 */     OFFSET = -16 * ZOOM;
/*     */ 
/* 140 */     gBuffer = buffer.getGraphics();
/* 141 */     gBuffer.setColor(Color.black);
/* 142 */     gMain = getGraphics().create();
/* 143 */     introGif = Loader.getImage("intro.gif", this);
/* 144 */     alphaGif = Loader.getImage("alphabet.gif", this);
/* 145 */     Score.init(alphaGif);
/* 146 */     joy = new JoyStick(this);
/* 147 */     addKeyListener(joy);
/*     */     try {
/* 149 */       loadCookie();
/*     */     } catch (Exception localException) {
/*     */     }
/* 152 */     addFocusListener(this);
/*     */   }
/*     */ 
/*     */   public void start() {
/* 156 */     running = true;
/* 157 */     if (runner == null) {
/* 158 */       runner = new Thread(this);
/* 159 */       runner.start();
/*     */     }
/* 161 */     if (timer == null) {
/* 162 */       timer = new Wiz3.Timer();
/* 163 */       timer.start();
/*     */     }
/* 165 */     requestFocus();
/* 166 */     showStatus("Wiz3 v4.0 ©2000 Steve Eaborn (MrE Software)");
/*     */   }
/*     */ 
/*     */   public URL getCodeBase() {
/*     */     try {
/* 171 */       return super.getCodeBase();
/*     */     }
/*     */     catch (NullPointerException localNullPointerException) {
/*     */       try {
/* 175 */         return new URL("file:./"); } catch (MalformedURLException ee) {  }
/*     */     }
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   public String getParameter(String s)
/*     */   {
/*     */     try {
/* 183 */       return super.getParameter(s); } catch (NullPointerException e) {
/*     */     }
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */   public void showStatus(String s)
/*     */   {
/*     */     try {
/* 191 */       super.showStatus(s);
/*     */     } catch (NullPointerException e) {
/* 193 */       f.setTitle(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Image getImage(URL u, String s) {
/*     */     try {
/* 199 */       return super.getImage(u, s); } catch (NullPointerException e) {
/*     */     }
/* 201 */     return getToolkit().getImage(s);
/*     */   }
/*     */ 
/*     */   public AudioClip getAudioClip(URL u, String s)
/*     */   {
/*     */     AudioClip a;
/*     */     try {
/* 208 */       a = super.getAudioClip(u, s);
/*     */     }
/*     */     catch (NullPointerException e)
/*     */     {
/*     */       try
/*     */       {
/*     */         AudioClip a;
/* 211 */         u = new URL(u + s);
/*     */       } catch (MalformedURLException ee) {
/* 213 */         ee.printStackTrace();
/*     */       }
/* 215 */       a = Applet.newAudioClip(u);
/*     */     }
/* 217 */     return a;
/*     */   }
/*     */ 
/*     */   public void run() {
/* 221 */     bm = new BobManager(this);
/* 222 */     ls = new LandScape(this);
/* 223 */     bm.au[0].loop();
/* 224 */     this.gameState = GAMEOVER;
/* 225 */     while (running) {
/* 226 */       while (this.gameState == PLAYING) {
/* 227 */         bm.moveAll();
/* 228 */         while (sync == 0)
/* 229 */           Thread.yield();
/* 230 */         sync = 0;
/* 231 */         paintAll();
/* 232 */         Thread.yield();
/*     */       }
/* 234 */       if (this.gameState == RESTART) {
/* 235 */         restart();
/* 236 */       } else if (this.gameState == GAMEOVER) {
/* 237 */         gBuffer.drawImage(introGif, 16, 0, null);
/* 238 */         Score.gameOver(gBuffer, startLevel);
/* 239 */         gMain.drawImage(buffer, OFFSET, 0, W, H, this);
/* 240 */         this.gameState = JoyStick.getGameState(this.gameState);
/* 241 */       } else if (this.gameState == PAUSED) {
/* 242 */         ls.paintAll(gBuffer);
/* 243 */         bm.paintAll(gBuffer);
/* 244 */         JoyStick.showPaused(gBuffer);
/* 245 */         gMain.drawImage(buffer, OFFSET, 0, W, H, this);
/* 246 */       } else if (this.gameState == NEWGAME) {
/* 247 */         newGame();
/* 248 */       } else if (this.gameState == RESUME) {
/* 249 */         resumeGame();
/* 250 */       } else if ((this.gameState & NOFOCUS) != 0) {
/* 251 */         gBuffer.drawImage(introGif, 16, 0, null);
/* 252 */         Score.noFocus(gBuffer);
/* 253 */         gMain.drawImage(buffer, OFFSET, 0, W, H, this);
/*     */       }
/*     */       try {
/* 256 */         Thread.sleep(30L);
/*     */       } catch (InterruptedException localInterruptedException) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void paintAll() {
/* 263 */     ls.paintAll(gBuffer);
/* 264 */     bm.paintAll(gBuffer);
/* 265 */     Score.paint(gBuffer);
/* 266 */     gMain.drawImage(buffer, OFFSET, 0, W, H, this);
/*     */   }
/*     */ 
/*     */   public void showProgress() {
/* 270 */     gBuffer.drawImage(introGif, 16, 0, null);
/* 271 */     Score.showProgress(gBuffer);
/* 272 */     gMain.drawImage(buffer, OFFSET, 0, W, H, this);
/*     */   }
/*     */ 
/*     */   public void stop() {
/* 276 */     this.gameState = FINISHED;
/* 277 */     running = false;
/* 278 */     timer = null;
/* 279 */     runner = null;
/*     */   }
/*     */ 
/*     */   public void restart() {
/* 283 */     for (Score.fade = -128; Score.fade < 0; ) {
/* 284 */       while (sync == 0)
/* 285 */         Thread.yield();
/* 286 */       sync = 0;
/* 287 */       paintAll();
/*     */     }
/*     */ 
/* 290 */     this.gameState = PLAYING;
/* 291 */     if (bm.dead) {
/* 292 */       if (Score.lives == 0) {
/* 293 */         this.gameState = GAMEOVER;
/* 294 */         saveCookie();
/* 295 */         return;
/*     */       }
/* 297 */       Score.invincible = 80;
/*     */     }
/* 299 */     if (bm.nextLevel) {
/* 300 */       this.gameState = LOADING;
/* 301 */       ls.getNextLevel();
/* 302 */       this.gameState = PLAYING;
/*     */     }
/* 304 */     bm.init();
/* 305 */     System.gc();
/*     */   }
/*     */ 
/*     */   public void newGame() {
/* 309 */     startLevel = 1;
/* 310 */     resumeGame();
/*     */   }
/*     */ 
/*     */   public void resumeGame() {
/* 314 */     this.gameState = LOADING;
/* 315 */     Score.init(alphaGif);
/* 316 */     ls.getFirstLevel(startLevel);
/* 317 */     bm.init();
/* 318 */     System.gc();
/* 319 */     this.gameState = PLAYING;
/*     */   }
/*     */ 
/*     */   public String getAppletInfo() {
/* 323 */     return "Wiz3 v4.0 ©2000 Steve Eaborn (MrE Software)";
/*     */   }
/*     */ 
/*     */   public void loadCookie() throws Exception {
/* 327 */     int a = 0;
/* 328 */     int b = 0;
/* 329 */     cookie = "";
/*     */     try {
/* 331 */       Class.forName("netscape.javascript.JSObject");
/*     */     } catch (ClassNotFoundException e) {
/* 333 */       System.out.println("Can't find 'netscape/javascript/JSObject'");
/* 334 */       return;
/*     */     }
/* 336 */     cookie = getCookie();
/* 337 */     if (cookie.equals("")) return;
/*     */ 
/* 339 */     System.out.println("Cookie found:" + cookie);
/* 340 */     a = cookie.indexOf("=") + 1;
/* 341 */     b = cookie.indexOf(",", a);
/* 342 */     Score.score = Integer.parseInt(cookie.substring(a, b));
/* 343 */     a = b + 1;
/* 344 */     b = cookie.indexOf(",", a);
/* 345 */     Score.hiscore = Integer.parseInt(cookie.substring(a, b));
/* 346 */     a = b + 1;
/* 347 */     b = cookie.indexOf(",", a);
/* 348 */     startLevel = Integer.parseInt(cookie.substring(a, b));
/* 349 */     a = b + 1;
/* 350 */     b = cookie.indexOf(",", a);
/* 351 */     JoyStick.kl = Integer.parseInt(cookie.substring(a, b));
/* 352 */     a = b + 1;
/* 353 */     b = cookie.indexOf(",", a);
/* 354 */     JoyStick.kr = Integer.parseInt(cookie.substring(a, b));
/* 355 */     a = b + 1;
/* 356 */     b = cookie.indexOf(",", a);
/* 357 */     JoyStick.ku = Integer.parseInt(cookie.substring(a, b));
/* 358 */     a = b + 1;
/* 359 */     b = cookie.indexOf(",", a);
/* 360 */     JoyStick.kd = Integer.parseInt(cookie.substring(a, b));
/* 361 */     a = b + 1;
/* 362 */     b = cookie.indexOf(",", a);
/* 363 */     JoyStick.kf = Integer.parseInt(cookie.substring(a, b));
/* 364 */     a = b + 1;
/* 365 */     JoyStick.kp = Integer.parseInt(cookie.substring(a));
/*     */   }
/*     */ 
/*     */   public void saveCookie()
/*     */   {
/* 370 */     if (Score.score > Score.hiscore) Score.hiscore = Score.score;
/* 371 */     startLevel = ls.level;
/* 372 */     cookie = "WIZ3=" + Score.score + "," + Score.hiscore + "," + startLevel + 
/* 373 */       "," + JoyStick.kl + "," + JoyStick.kr + "," + JoyStick.ku + 
/* 374 */       "," + JoyStick.kd + "," + JoyStick.kf + "," + JoyStick.kp;
/* 375 */     setCookie(cookie);
/*     */   }
/*     */ 
/*     */   public String getCookie() throws Exception {
/* 379 */     myBrowser = JSObject.getWindow(this);
/* 380 */     myDocument = (JSObject)myBrowser.getMember("document");
/* 381 */     return (String)myDocument.getMember("cookie");
/*     */   }
/*     */ 
/*     */   public void setCookie(String c) {
/*     */     try {
/* 386 */       exp = Calendar.getInstance();
/* 387 */       exp.add(2, 12);
/* 388 */       cookie = c + "; expires=" + formatter.format(exp.getTime()) + 
/* 389 */         " GMT";
/* 390 */       myBrowser.eval("document.cookie=\"" + cookie + "\"");
/*     */     } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(Graphics g1) {
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g1) {
/*     */   }
/*     */ 
/*     */   public void focusGained(FocusEvent p1) {
/* 402 */     this.gameState &= GOTFOCUS;
/*     */   }
/*     */ 
/*     */   public void focusLost(FocusEvent p1) {
/* 406 */     this.gameState |= NOFOCUS;
/*     */   }
/*     */ 
/*     */   final class Timer extends Thread
/*     */     implements Runnable
/*     */   {
/*     */     public void run()
/*     */     {
/*  71 */       while (Wiz3.running) {
/*     */         try {
/*  73 */           Thread.sleep(30L);
/*     */         } catch (InterruptedException localInterruptedException) {
/*     */         }
/*  76 */         Wiz3.sync += 1;
/*  77 */         Thread.yield();
/*     */       }
/*  79 */       Wiz3.sync = 1;
/*     */     }
/*     */ 
/*     */     Timer()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     Wiz3
 * JD-Core Version:    0.6.2
 */