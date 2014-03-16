/*     */ import java.applet.AudioClip;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ 
/*     */ final class BobManager
/*     */ {
/*     */   Wiz3 wiz3;
/*     */   JoyStick joy;
/*     */   static final int NUMBOBS = 17;
/*     */   static final int BOB_WIZ = 0;
/*     */   static final int BOB_ARROW = 4;
/*     */   static final int BOB_STAR = 5;
/*     */   static final int BOB_BOTTLE = 8;
/*     */   static final int BOB_MISC = 10;
/*     */   static final int BOB_GLITTER = 12;
/*     */   static final int BOB_FIREBALL = 13;
/*     */   static final int BOB_SHOT = 16;
/*     */   static final int BOB_BOSS = 20;
/*     */   static final int BOB_BOSS2 = 22;
/*     */   boolean dead;
/*     */   boolean nextLevel;
/*     */   int checkPoint;
/*     */   int startX;
/*     */   Vector bob;
/*     */   Vector gif;
/*     */   Bob bb;
/*     */   Class[] bobType;
/* 145 */   String[] sound = { 
/* 146 */     "silence.au", "bottle.au", "star.au", "hit.au", "die.au", "powerup.au", "door.au", "lever.au", "bounce.au", "extra.au", 
/* 147 */     "nokey.au", "key.au", "jump.au" };
/*     */   AudioClip[] au;
/*     */ 
/*     */   public BobManager(Wiz3 wiz3)
/*     */   {
/*  16 */     this.dead = true;
/*  17 */     this.nextLevel = false;
/*  18 */     this.checkPoint = 1;
/*  19 */     this.startX = 0;
/*  20 */     this.bob = new Vector(16);
/*  21 */     this.gif = new Vector();
/*  22 */     this.bobType = new Class[] { 
/*  23 */       BobWiz.class, BobGuard.class, BobKnight.class, BobDragon.class, BobArrow.class, BobStar.class, BobVPlatform.class, BobHPlatform.class, BobBottle.class, BobFire.class, 
/*  24 */       BobMisc.class, BobSentry.class, BobGlitter.class, BobFireball.class, BobFlame.class, BobBoulder.class, BobShot.class, BobFPlatform.class, BobFish.class, BobArcher.class, 
/*  25 */       BobBoss.class, BobGhost.class, BobBoss2.class };
/*     */ 
/*  27 */     this.au = new AudioClip[this.sound.length];
/*  28 */     this.wiz3 = wiz3;
/*  29 */     Score.initProgress(this.bobType.length + this.au.length, "Loading Game Data");
/*  30 */     for (int b = 0; b < this.bobType.length; b++)
/*     */     {
/*  32 */       this.gif.addElement(Loader.getImage(this.bobType[b].getName() + ".gif", wiz3));
/*  33 */       Score.incProgress(wiz3);
/*     */     }
/*     */ 
/*  36 */     for (int i = 0; i < this.au.length; i++)
/*     */     {
/*  38 */       this.au[i] = Loader.getAudio(this.sound[i], wiz3);
/*  39 */       Score.incProgress(wiz3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  46 */     this.bob.removeAllElements();
/*  47 */     Wiz3.ls.resetSprites();
/*  48 */     createBob(0, 0, 0, 0, 0, 0);
/*  49 */     createBob(0, 0, 0, 0, 12, 0);
/*  50 */     for (int x = Wiz3.ls.lx / 16; x < Wiz3.ls.lx / 16 + 32; x++)
/*     */     {
/*  52 */       for (int y = 0; y < 16; y++) {
/*  53 */         if (Wiz3.ls.land[x][y].sprite > 0)
/*     */         {
/*  55 */           createBob(x * 16, y * 16 + 16, x, y, Wiz3.ls.land[x][y].sprite, Wiz3.ls.land[x][y].flag);
/*  56 */           Wiz3.ls.land[x][y].sprite = (-Wiz3.ls.land[x][y].sprite);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  61 */     Score.fade = 128;
/*  62 */     this.dead = false;
/*  63 */     this.nextLevel = false;
/*     */   }
/*     */ 
/*     */   public void createBob(int x, int y, int startX, int startY, int s, int flag)
/*     */   {
/*     */     try
/*     */     {
/*  70 */       this.bob.addElement(this.bobType[s].newInstance());
/*     */     }
/*     */     catch (InstantiationException e1)
/*     */     {
/*  74 */       System.out.println("Cannot Instantiate");
/*     */     }
/*     */     catch (IllegalAccessException e2)
/*     */     {
/*  78 */       System.out.println("Cannot Access");
/*     */     }
/*  80 */     this.bb = ((Bob)this.bob.elementAt(this.bob.size() - 1));
/*  81 */     this.bb.init(x, y, startX, startY, (Image)this.gif.elementAt(s), this, flag);
/*     */   }
/*     */ 
/*     */   public void deleteBob(int x, int y, int b)
/*     */   {
/*  86 */     Wiz3.ls.land[x][y].sprite = (-Wiz3.ls.land[x][y].sprite);
/*  87 */     this.bob.removeElementAt(b);
/*     */   }
/*     */ 
/*     */   public void paintAll(Graphics g)
/*     */   {
/*  92 */     for (int b = this.bob.size() - 1; b >= 0; b--)
/*     */     {
/*  94 */       this.bb = ((Bob)this.bob.elementAt(b));
/*  95 */       this.bb.paint(g);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void moveAll()
/*     */   {
/* 102 */     Wiz3.ls.move(((Bob)this.bob.elementAt(0)).r.x);
/* 103 */     if (Score.fade > 0)
/* 104 */       return;
/* 105 */     for (int b = this.bob.size() - 1; b >= 0; b--)
/*     */     {
/* 107 */       this.bb = ((Bob)this.bob.elementAt(b));
/* 108 */       this.bb.move();
/* 109 */       if (this.bb.outOfBounds(Wiz3.ls.lx))
/* 110 */         deleteBob(this.bb.start.x, this.bb.start.y, b);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean blockAt(int x, int y, int type)
/*     */   {
/* 117 */     if (y > 255)
/* 118 */       return false;
/* 119 */     if (y < 0)
/* 120 */       return false;
/* 121 */     return (Wiz3.ls.land[(x >> 4)][(y >> 4)].block & type) != 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobManager
 * JD-Core Version:    0.6.2
 */