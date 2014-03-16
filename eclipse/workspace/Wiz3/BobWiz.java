/*     */ import java.applet.AudioClip;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.PixelGrabber;
/*     */ import java.util.Vector;
/*     */ 
/*     */ final class BobWiz extends Bob
/*     */ {
/*     */   int yy;
/*     */   int xx;
/*     */   int aa;
/*     */   int tl;
/*     */   int tr;
/*     */   int tt;
/*     */   int tm;
/*     */   int tb;
/*     */   int tbb;
/*     */   int oldy;
/*     */   int j1;
/*     */   int px;
/*     */   Point h;
/*     */   Bob bb;
/*     */   BobPlatform platform;
/*     */   Image mask;
/*     */   int[] test;
/*     */   Graphics gm;
/*     */   PixelGrabber pg;
/*     */   JoyStick joy;
/*     */   Wiz3 wiz3;
/*     */   boolean switchFlag;
/*     */   int bonus;
/*     */   static final int STAR = 1;
/*     */   static final int BOTTLE = 2;
/*     */   static final int EXTRA = 3;
/*     */   static final int KEY = 4;
/*     */   static final int CHECKPOINT = 5;
/*     */   static final int UGA = 6;
/*     */   static final int DOORWAY = 7;
/*     */   static final int SWITCH = 8;
/*     */   static final int EOL = 9;
/*     */   static final int BOUNCE = 10;
/*     */   int leftBound;
/*     */   int rightBound;
/*     */ 
/*     */   BobWiz()
/*     */   {
/*  17 */     this.yy = 0;
/*  18 */     this.xx = 0;
/*  19 */     this.aa = 0;
/*  20 */     this.px = 0;
/*  21 */     this.h = new Point();
/*  22 */     this.switchFlag = false;
/*  23 */     this.leftBound = 16;
/*  24 */     this.rightBound = 4063;
/*     */   }
/*     */ 
/*     */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*     */   {
/*  29 */     super.init(x, y - i.getHeight(null), 16, i.getHeight(null), 0, sx, sy, 0, i, bm, flag);
/*  30 */     this.wiz3 = bm.wiz3;
/*  31 */     this.joy = Wiz3.joy;
/*  32 */     this.h = Wiz3.ls.getStart(bm.checkPoint, bm.startX);
/*  33 */     this.r.x = this.h.x;
/*  34 */     this.r.y = (this.h.y - this.r.height);
/*  35 */     this.leftBound = (Wiz3.ls.leftBound + 16);
/*  36 */     this.rightBound = (Wiz3.ls.rightBound + 480);
/*  37 */     this.a = ((this.rightBound - this.leftBound) / 2 + this.leftBound >= this.r.x ? 0 : 16);
/*  38 */     this.mask = this.wiz3.createImage(this.r.width, this.r.height);
/*  39 */     this.test = new int[this.r.width * this.r.height];
/*  40 */     this.gm = this.mask.getGraphics();
/*  41 */     if (bm.dead)
/*     */     {
/*  43 */       Score.lives -= 1;
/*  44 */       if (Score.restore)
/*     */       {
/*  46 */         bm.au[5].play();
/*  47 */         Score.restorePowerups();
/*  48 */         bm.au[5].play();
/*     */       }
/*     */     }
/*  51 */     if (Score.powerup > 4)
/*  52 */       bm.createBob(0, 0, 0, 0, 16, 0);
/*     */   }
/*     */ 
/*     */   public void move()
/*     */   {
/*  57 */     if (!this.alive)
/*     */     {
/*  59 */       super.move();
/*  60 */       if (this.r.y > 255)
/*     */       {
/*  62 */         this.bm.dead = true;
/*  63 */         this.wiz3.gameState = Wiz3.RESTART;
/*     */       }
/*  65 */       return;
/*     */     }
/*  67 */     Score.invincible -= (Score.invincible <= 0 ? 0 : 1);
/*  68 */     if ((!JoyStick.right) && (!JoyStick.left))
/*  69 */       this.a = ((this.a & 0x10) + 8);
/*  70 */     if (JoyStick.down)
/*     */     {
/*  72 */       JoyStick.left = false;
/*  73 */       JoyStick.right = false;
/*  74 */       this.xx = this.px;
/*  75 */       if (Score.invisible)
/*     */       {
/*  77 */         this.a = 13;
/*  78 */         if (!JoyStick.downflag)
/*     */         {
/*  80 */           this.bm.createBob(0, 0, 0, 0, 12, 0);
/*  81 */           JoyStick.downflag = true;
/*     */         }
/*     */       }
/*     */       else {
/*  85 */         this.a = ((this.a & 0x10) + 10);
/*     */       }
/*     */     }
/*  88 */     if (JoyStick.right)
/*     */     {
/*  90 */       this.aa += 1;
/*  91 */       this.a = (this.aa & 0x7);
/*  92 */       this.xx = Math.min(this.xx + 2, 16);
/*     */     }
/*  94 */     else if (JoyStick.left)
/*     */     {
/*  96 */       this.aa -= 1;
/*  97 */       this.a = ((this.aa & 0x7) + 16);
/*  98 */       this.xx = Math.max(this.xx - 2, -16);
/*     */     }
/* 100 */     if ((JoyStick.fire) && (this.j1 < Score.maxJumps) && ((this.yy & 0xFFFFFFF8) == 0))
/*     */     {
/* 102 */       this.yy = Score.highJump;
/* 103 */       JoyStick.fire = false;
/* 104 */       this.j1 += 1;
/* 105 */       this.bm.au[12].play();
/*     */     }
/* 107 */     if (JoyStick.change)
/* 108 */       JoyStick.keyChange(this.wiz3, this.joy);
/* 109 */     this.r.x += this.xx / 4;
/* 110 */     this.r.x = Math.min(Math.max(this.r.x, this.leftBound), this.rightBound);
/* 111 */     this.tl = (this.r.x + 4 >> 4);
/* 112 */     this.tr = (this.r.x + 11 >> 4);
/* 113 */     this.tt = Math.max(this.r.y >> 4, 0);
/* 114 */     this.tm = Math.max(this.r.y + 16 >> 4, 0);
/* 115 */     this.tb = Math.max(this.r.y + 23 >> 4, 0);
/* 116 */     if (this.xx < this.px)
/*     */     {
/* 118 */       this.xx += 1;
/* 119 */       if (((Wiz3.ls.land[this.tl][this.tt].block | Wiz3.ls.land[this.tl][this.tm].block | Wiz3.ls.land[this.tl][this.tb].block) & 0x1) > 0)
/*     */       {
/* 121 */         this.r.x = ((this.r.x & 0xFFFFFFF0) + 12);
/* 122 */         this.xx = -3;
/*     */       }
/* 124 */       if (!JoyStick.left)
/* 125 */         this.a = 27;
/*     */     }
/* 127 */     else if (this.xx > this.px)
/*     */     {
/* 129 */       this.xx -= 1;
/* 130 */       if (((Wiz3.ls.land[this.tr][this.tt].block | Wiz3.ls.land[this.tr][this.tm].block | Wiz3.ls.land[this.tr][this.tb].block) & 0x1) > 0)
/*     */       {
/* 132 */         this.r.x = ((this.r.x & 0xFFFFFFF0) + 4);
/* 133 */         this.xx = 3;
/*     */       }
/* 135 */       if (!JoyStick.right)
/* 136 */         this.a = 11;
/*     */     }
/* 138 */     this.oldy = this.r.y;
/* 139 */     this.tbb = this.tb;
/* 140 */     if (this.yy < 48)
/* 141 */       this.yy += 2;
/* 142 */     this.r.y += this.yy / 4;
/* 143 */     if (this.r.y > 232)
/*     */     {
/* 145 */       this.r.y = 232;
/* 146 */       die();
/* 147 */       return;
/*     */     }
/* 149 */     this.tl = (this.r.x + 4 >> 4);
/* 150 */     this.tr = (this.r.x + 11 >> 4);
/* 151 */     this.tt = Math.max(this.r.y >> 4, 0);
/* 152 */     this.tm = Math.max(this.r.y + 16 >> 4, 0);
/* 153 */     this.tb = Math.max(this.r.y + 23 >> 4, 0);
/* 154 */     if (((Wiz3.ls.land[this.tl][this.tt].block | Wiz3.ls.land[this.tr][this.tt].block) & 0x1) > 0)
/*     */     {
/* 156 */       this.r.y = ((this.r.y & 0xFFFFFFF0) + 16);
/* 157 */       this.yy = 4;
/*     */     }
/* 159 */     if ((((Wiz3.ls.land[this.tl][this.tb].block | Wiz3.ls.land[this.tr][this.tb].block) & 0x3) > 0) && (this.tb > this.tbb))
/*     */     {
/* 161 */       this.j1 = 0;
/* 162 */       this.r.y = ((this.r.y + 24 & 0xFFFFFFF0) - 24);
/* 163 */       this.yy = 4;
/* 164 */       this.platform = null;
/* 165 */       if (Wiz3.ls.land[this.tl][this.tb].bonus == 10)
/*     */       {
/* 167 */         this.yy = -42;
/* 168 */         this.j1 += 1;
/* 169 */         this.bm.createBob(this.tl * 16, this.tb * 16 + 4, 0, 0, 10, 0);
/* 170 */         this.bm.au[8].play();
/*     */       }
/* 172 */       else if (Wiz3.ls.land[this.tr][this.tb].bonus == 10)
/*     */       {
/* 174 */         this.yy = -42;
/* 175 */         this.j1 += 1;
/* 176 */         this.bm.createBob(this.tr * 16, this.tb * 16 + 4, 0, 0, 10, 0);
/* 177 */         this.bm.au[8].play();
/*     */       }
/*     */     }
/* 180 */     else if (this.platform != null)
/*     */     {
/* 182 */       if ((this.yy < 0) || (this.r.x + this.r.width <= this.platform.r.x) || (this.r.x >= this.platform.r.x + this.platform.r.width - 1))
/*     */       {
/* 184 */         this.platform = null;
/*     */       }
/*     */       else {
/* 187 */         this.r.y = (this.oldy + this.platform.yy);
/* 188 */         if (this.r.y > 232)
/*     */         {
/* 190 */           this.r.y = 232;
/* 191 */           die();
/* 192 */           return;
/*     */         }
/* 194 */         this.px = this.platform.xx;
/* 195 */         this.j1 = 0;
/* 196 */         this.yy = 4;
/*     */       }
/*     */     }
/*     */     else {
/* 200 */       this.a = ((this.a & 0x10) + 9);
/* 201 */       this.px = 0;
/*     */     }
/* 203 */     this.h = hitAnyBob();
/* 204 */     if (this.h.x > 0)
/* 205 */       if ((this.h.y == 1) || ((this.h.y == 2) && (!Score.asbestos))) {
/* 206 */         checkDie();
/*     */       }
/* 208 */       else if ((this.h.y != 3) && (this.h.y == 4))
/*     */       {
/* 210 */         this.platform = ((BobPlatform)this.bm.bob.elementAt(this.h.x));
/* 211 */         if (this.platform.r.y + 4 > this.oldy + this.r.height)
/*     */         {
/* 213 */           this.platform.on = true;
/* 214 */           this.r.y = (this.platform.r.y - this.r.height);
/*     */         }
/*     */         else {
/* 217 */           this.platform = null;
/*     */         }
/*     */       }
/* 220 */     this.tr = (this.r.x + 8 >> 4);
/* 221 */     this.tm = Math.max(this.r.y + 7 >> 4, 0);
/* 222 */     if ((Wiz3.ls.land[this.tr][this.tm].bonus > 0) || (Wiz3.ls.land[this.tr][(this.tm + 1)].bonus > 0))
/*     */     {
/* 224 */       this.tm += (Wiz3.ls.land[this.tr][(this.tm + 1)].bonus <= 0 ? 0 : 1);
/* 225 */       this.bonus = Wiz3.ls.land[this.tr][this.tm].bonus;
/* 226 */     }switch (this.bonus)
/*     */     {
/*     */     case 2:
/* 229 */       this.bm.au[1].play();
/* 230 */       Score.incPotion();
/* 231 */       this.bm.createBob(this.tr * 16, this.tm * 16, 0, 0, 8, 0);
/* 232 */       if (Score.potion == 24)
/*     */       {
/* 234 */         for (int i = 1; i < 9; i++) {
/* 235 */           this.bm.createBob(this.r.x, this.r.y + 4, 0, 0, 5, i);
/*     */         }
/* 237 */         this.bm.createBob(0, 0, 0, 0, 12, 0);
/* 238 */         Score.potion = 124;
/* 239 */         Score.nextPowerup(this.tr * 16, this.tm * 16, this.bm);
/*     */       }
/* 241 */       clearBonus(this.tr, this.tm);
/* 242 */       break;
/*     */     case 1:
/* 245 */       this.bm.au[2].play();
/* 246 */       Score.addScore(1000);
/* 247 */       this.bm.createBob(this.tr * 16, this.tm * 16, 0, 0, 5, 0);
/* 248 */       clearBonus(this.tr, this.tm);
/* 249 */       break;
/*     */     case 6:
/* 252 */       Score.invincible = 1600;
/* 253 */       this.bm.createBob(this.tr * 16, this.tm * 16, 0, 0, 10, 3);
/* 254 */       this.bm.createBob(0, 0, 0, 0, 12, 0);
/* 255 */       clearBonus(this.tr, this.tm);
/* 256 */       break;
/*     */     case 4:
/* 259 */       this.bm.au[11].play();
/* 260 */       Score.addScore(1000);
/* 261 */       Score.key = true;
/* 262 */       this.bm.createBob(this.tr * 16, this.tm * 16, 0, 0, 10, 1);
/* 263 */       clearBonus(this.tr, this.tm);
/* 264 */       break;
/*     */     case 3:
/* 267 */       Score.extraMan(this.tr * 16, this.tm * 16, this.bm);
/* 268 */       clearBonus(this.tr, this.tm);
/* 269 */       break;
/*     */     case 5:
/* 272 */       this.bm.au[9].play();
/* 273 */       this.bm.checkPoint = 1;
/* 274 */       Wiz3.ls.clearCP(this.bm.checkPoint);
/* 275 */       Wiz3.ls.land[this.tr][this.tm].flag = this.bm.checkPoint;
/* 276 */       this.bm.startX = this.tr;
/* 277 */       this.bm.createBob(this.tr * 16, this.tm * 16, 0, 0, 10, 5);
/* 278 */       clearBonus(this.tr, this.tm);
/* 279 */       System.gc();
/* 280 */       break;
/*     */     case 7:
/* 283 */       if (JoyStick.up)
/*     */       {
/* 285 */         JoyStick.up = false;
/* 286 */         this.xx = 0;
/* 287 */         if (((Wiz3.ls.land[this.tr][this.tm].flag & 0x4) == 0) || (Score.key))
/*     */         {
/* 289 */           if ((Wiz3.ls.land[this.tr][this.tm].flag & 0x4) != 0)
/* 290 */             Score.key = false;
/* 291 */           this.bm.au[6].play();
/* 292 */           Wiz3.ls.openDoor(this.tr, this.tm);
/* 293 */           this.bm.checkPoint = Wiz3.ls.land[this.tr][this.tm].flag;
/* 294 */           this.bm.startX = (this.tr + 2);
/* 295 */           this.wiz3.gameState = Wiz3.RESTART;
/*     */         }
/*     */         else {
/* 298 */           this.bm.au[10].play();
/* 299 */           this.bm.createBob(this.tr * 16, this.tm * 16, 0, 0, 10, 4);
/*     */         }
/*     */       }
/* 302 */       break;
/*     */     case 8:
/* 305 */       if (!this.switchFlag)
/*     */       {
/* 307 */         this.bm.au[7].play();
/* 308 */         Wiz3.ls.throwSwitch();
/* 309 */         this.switchFlag = true;
/*     */       }
/* 311 */       break;
/*     */     case 9:
/* 314 */       endOfLevel();
/*     */     default:
/* 316 */       break;
/*     */ 
/* 319 */       this.switchFlag = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endOfLevel()
/*     */   {
/* 325 */     this.bm.nextLevel = true;
/* 326 */     this.wiz3.gameState = Wiz3.RESTART;
/* 327 */     this.bm.au[6].play();
/*     */   }
/*     */ 
/*     */   public void clearBonus(int x, int y)
/*     */   {
/* 332 */     Wiz3.ls.land[x][y].bonus = 0;
/* 333 */     Wiz3.ls.land[x][y].fore = 0;
/* 334 */     Wiz3.ls.paintBlocks(x * 16 - Wiz3.ls.lx + Wiz3.ls.sx & 0xFFFFFFF0, y * 16, Wiz3.ls.land[x][y].back, Wiz3.ls.land[x][y].fore);
/*     */   }
/*     */ 
/*     */   public void checkDie()
/*     */   {
/* 339 */     if (Score.invincible > 80)
/*     */     {
/* 341 */       Score.invincible = 80;
/* 342 */       this.bm.au[3].play();
/* 343 */       return;
/*     */     }
/* 345 */     if (Score.invincible > 0)
/*     */     {
/* 347 */       return;
/*     */     }
/* 349 */     die();
/*     */   }
/*     */ 
/*     */   public void die()
/*     */   {
/* 355 */     this.bm.au[3].play();
/* 356 */     this.xx = (-this.xx);
/* 357 */     this.alive = false;
/* 358 */     this.yy = -16;
/* 359 */     this.a = ((this.a & 0x10) + 9);
/* 360 */     this.wiz3.paintAll();
/*     */     try
/*     */     {
/* 363 */       Thread.sleep(1000L);
/*     */     } catch (InterruptedException localInterruptedException) {
/*     */     }
/* 366 */     if (Score.key)
/* 367 */       this.bm.createBob(this.tr * 16, this.tm * 16, 0, 0, 10, 1);
/* 368 */     Score.key = false;
/* 369 */     this.a = 12;
/* 370 */     Score.resetPowerups();
/* 371 */     this.bm.au[4].play();
/*     */   }
/*     */ 
/*     */   public Point hitAnyBob()
/*     */   {
/* 376 */     this.h.x = 0;
/* 377 */     this.h.y = -1;
/* 378 */     for (int b = 1; b < this.bm.bob.size(); b++)
/*     */     {
/* 380 */       this.bb = ((Bob)this.bm.bob.elementAt(b));
/* 381 */       if ((this.r.intersects(this.bb.r)) && (this.h.y < this.bb.touch) && (testMask(this.bb)))
/*     */       {
/* 383 */         this.h.x = b;
/* 384 */         this.h.y = this.bb.touch;
/*     */       }
/*     */     }
/*     */ 
/* 388 */     return this.h;
/*     */   }
/*     */ 
/*     */   public boolean testMask(Bob bb)
/*     */   {
/* 393 */     this.gm.setPaintMode();
/* 394 */     this.gm.setClip(0, 0, this.r.width, this.r.height);
/* 395 */     this.gm.drawImage(this.i, -this.a * this.r.width, 0, Color.black, this.wiz3);
/* 396 */     this.gm.clipRect(bb.r.x - this.r.x, bb.r.y - this.r.y, bb.r.width, bb.r.height);
/* 397 */     this.gm.drawImage(bb.i, -bb.a * bb.r.width + bb.r.x - this.r.x, bb.r.y - this.r.y, this.wiz3);
/* 398 */     this.gm.setXORMode(Color.black);
/* 399 */     this.gm.drawImage(bb.i, -bb.a * bb.r.width + bb.r.x - this.r.x, bb.r.y - this.r.y, this.wiz3);
/* 400 */     this.gm.setClip(0, 0, this.r.width, this.r.height);
/* 401 */     this.gm.drawImage(this.i, -this.a * this.r.width, 0, this.wiz3);
/* 402 */     this.pg = new PixelGrabber(this.mask, 0, 0, this.r.width, this.r.height, this.test, 0, this.r.width);
/*     */     try
/*     */     {
/* 405 */       this.pg.grabPixels();
/*     */     } catch (InterruptedException localInterruptedException) {
/*     */     }
/* 408 */     for (int b = this.test.length - 1; b >= 0; b--) {
/* 409 */       if (this.test[b] != -16777216)
/* 410 */         return true;
/*     */     }
/* 412 */     return false;
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/* 417 */     super.paint(g);
/* 418 */     if (((Score.invincible & 0x2) == 0) && (Score.invincible < 80))
/*     */     {
/* 420 */       return;
/*     */     }
/* 422 */     Graphics g2 = g.create(this.r.x - Wiz3.ls.lx, this.r.y, this.r.width, this.r.height);
/* 423 */     g2.setXORMode(Color.white);
/* 424 */     g2.fillRect(0, 0, this.r.width, this.r.height);
/* 425 */     g2.setPaintMode();
/* 426 */     g2.drawImage(this.i, -this.a * this.r.width, 0, null);
/* 427 */     g2.setXORMode(Color.white);
/* 428 */     g2.fillRect(0, 0, this.r.width, this.r.height);
/* 429 */     g2.dispose();
/*     */   }
/*     */ 
/*     */   public boolean outOfBounds(int lx)
/*     */   {
/* 435 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobWiz
 * JD-Core Version:    0.6.2
 */