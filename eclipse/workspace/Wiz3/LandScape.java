/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ final class LandScape
/*     */ {
/*     */   Wiz3 parent;
/*     */   Image foreIcons;
/*     */   Image backIcons;
/*     */   Image bufferBack;
/*     */   Graphics gLandBack;
/*     */   Graphics g2;
/*     */   Land[][] land;
/*     */   String landFile;
/*     */   int level;
/* 223 */   static int NUMLEVELS = 1;
/*     */   int ox;
/*     */   int lx;
/*     */   int sx;
/*     */   int leftBound;
/*     */   int rightBound;
/*     */   static final int BOUNDARY = 30;
/*     */   private int xx;
/*     */   private int ax;
/*     */   private int ay;
/*     */   private int dx;
/*     */   static final int BLOCK = 1;
/*     */   static final int PLATFORM = 3;
/*     */   static final int DOORWAY = 7;
/*     */   static final int SWITCH_B = 8;
/*     */   static final int SWITCH_F = 16;
/*     */ 
/*     */   public LandScape(Wiz3 parent)
/*     */   {
/*  14 */     this.land = new Land[256][16];
/*  15 */     this.leftBound = 0;
/*  16 */     this.rightBound = 3583;
/*  17 */     this.dx = 0;
/*  18 */     this.parent = parent;
/*  19 */     this.backIcons = Loader.getImage("backicons.gif", parent);
/*  20 */     this.foreIcons = Loader.getImage("foreicons.gif", parent);
/*  21 */     this.bufferBack = parent.createImage(1024, 256);
/*  22 */     this.gLandBack = this.bufferBack.getGraphics();
/*  23 */     for (int x = 0; x < 256; x++) {
/*  24 */       for (int y = 0; y < 16; y++) {
/*  25 */         this.land[x][y] = new Land();
/*     */       }
/*     */     }
/*     */ 
/*  29 */     String nl = parent.getParameter("NUMLEVELS");
/*  30 */     if (nl != null)
/*  31 */       NUMLEVELS = Integer.parseInt(parent.getParameter("NUMLEVELS"));
/*     */     else {
/*  33 */       NUMLEVELS = Loader.getNumLevels(parent);
/*     */     }
/*     */ 
/*  36 */     System.out.println("NumLevels: " + NUMLEVELS);
/*     */   }
/*     */ 
/*     */   public void getFirstLevel(int l) {
/*  40 */     this.level = l;
/*  41 */     this.landFile = ("level." + this.level);
/*     */     try {
/*  43 */       Loader.landLoad(this.landFile, this.level, this.land, this.parent);
/*     */     } catch (IOException e) {
/*     */       try {
/*  46 */         Loader.landLoad(this.landFile, this.level, this.land, this.parent);
/*     */       } catch (IOException ee) {
/*  48 */         this.parent.showStatus("Error Loading " + this.landFile + " !!");
/*     */       }
/*     */     }
/*  51 */     Wiz3.bm.checkPoint = 1;
/*     */   }
/*     */ 
/*     */   public void getNextLevel() {
/*  55 */     this.level += 1;
/*  56 */     if (this.level > NUMLEVELS) this.level = 1;
/*  57 */     this.landFile = ("level." + this.level);
/*     */     try {
/*  59 */       Loader.landLoad(this.landFile, this.level, this.land, this.parent);
/*     */     } catch (IOException e) {
/*     */       try {
/*  62 */         Loader.landLoad(this.landFile, this.level, this.land, this.parent);
/*     */       } catch (IOException ee) {
/*  64 */         this.parent.showStatus("Error Loading " + this.landFile + " !!");
/*     */       }
/*     */     }
/*  67 */     Wiz3.bm.checkPoint = 1;
/*     */   }
/*     */ 
/*     */   public Point getStart(int cp, int xs) {
/*  71 */     Point s = new Point(0, 0);
/*  72 */     for (int x = 0; x < 256; 
/*  83 */       xs &= 255)
/*     */     {
/*  73 */       for (int y = 15; y >= 0; y--) {
/*  74 */         if (((this.land[xs][y].flag & 0xFFFFFFFB) == cp) && (this.land[xs][y].sprite == 0))
/*     */         {
/*  76 */           openDoor(xs, y);
/*  77 */           s.x = (xs << 4);
/*  78 */           s.y = ((y << 4) + 16);
/*  79 */           break;
/*     */         }
/*     */       }
/*  82 */       x++;
/*  83 */       xs++;
/*     */     }
/*     */ 
/*  86 */     this.leftBound = getLeftBound(s);
/*  87 */     this.rightBound = getRightBound(s);
/*  88 */     this.lx = (Math.max(Math.min(s.x - 240, this.rightBound), this.leftBound) & 0xFFFFFFF0);
/*  89 */     this.sx = (this.lx & 0x1FF);
/*  90 */     for (int x = 0; x < 32; ) {
/*  91 */       for (int y = 0; y < 16; y++) {
/*  92 */         paintBlocks(this.sx, y << 4, this.land[((this.lx >> 4) + x)][y].back, 
/*  93 */           this.land[((this.lx >> 4) + x)][y].fore);
/*     */       }
/*  95 */       x++;
/*  96 */       this.sx += 16;
/*     */     }
/*     */ 
/*  99 */     return s;
/*     */   }
/*     */ 
/*     */   public void clearCP(int cp) {
/* 103 */     for (int x = 0; x < 256; x++)
/* 104 */       for (int y = 0; y < 16; y++)
/* 105 */         if ((this.land[x][y].flag == cp) && (this.land[x][y].sprite == 0))
/* 106 */           this.land[x][y].flag = 0;
/*     */   }
/*     */ 
/*     */   public void openDoor(int xs, int ys)
/*     */   {
/* 113 */     if (this.land[xs][ys].bonus != 7) return;
/* 114 */     for (int x = Math.max(xs - 1, 0); x < Math.min(xs + 2, 256); x++)
/* 115 */       for (int y = Math.max(ys - 1, 0); y < Math.min(ys + 2, 16); y++)
/* 116 */         if (this.land[x][y].bonus == 7) {
/* 117 */           this.land[x][y].flag &= -5;
/* 118 */           this.land[x][y].fore = 0;
/*     */         }
/*     */   }
/*     */ 
/*     */   public void throwSwitch()
/*     */   {
/* 126 */     for (int x = 0; x < 256; x++)
/* 127 */       for (int y = 0; y < 16; y++)
/* 128 */         if ((this.land[x][y].flag == 16) || (this.land[x][y].bonus == 8)) {
/* 129 */           this.land[x][y].fore ^= (this.land[x][y].fore == 0 ? 0 : 1);
/* 130 */           this.land[x][y].block ^= (this.land[x][y].block == 0 ? 0 : 5);
/* 131 */           paintLand(x, y);
/*     */         }
/*     */   }
/*     */ 
/*     */   public void paintLand(int x, int y)
/*     */   {
/* 139 */     paintBlocks(x * 16 - this.lx + this.sx & 0xFFFFFFF0, y * 16, this.land[x][y].back, 
/* 140 */       this.land[x][y].fore);
/*     */   }
/*     */ 
/*     */   public int getLeftBound(Point s) {
/* 144 */     for (int x = s.x / 16; x > 0; x--) {
/* 145 */       for (int y = 15; y >= 0; y--) {
/* 146 */         if (this.land[x][y].flag == 30) return x * 16 - 16;
/*     */       }
/*     */     }
/*     */ 
/* 150 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getRightBound(Point s) {
/* 154 */     for (int x = s.x / 16; x < 256; x++) {
/* 155 */       for (int y = 0; y < 16; y++) {
/* 156 */         if (this.land[x][y].flag == 30) return x * 16 - 512 + 16;
/*     */       }
/*     */     }
/*     */ 
/* 160 */     return 3583;
/*     */   }
/*     */ 
/*     */   public void resetSprites() {
/* 164 */     for (int x = 0; x < 256; x++)
/* 165 */       for (int y = 0; y < 16; y++)
/* 166 */         this.land[x][y].sprite = Math.abs(this.land[x][y].sprite);
/*     */   }
/*     */ 
/*     */   public void paintBlocks(int x, int y, int b, int f)
/*     */   {
/* 173 */     this.g2 = this.gLandBack.create(x, y, 16, 16);
/* 174 */     this.g2.drawImage(this.backIcons, -(b & 0xF) << 4, -(b & 0xFFFFFFF0), null);
/* 175 */     if (f > 0)
/* 176 */       this.g2.drawImage(this.foreIcons, -(f & 0xF) << 4, -(f & 0xFFFFFFF0), null);
/* 177 */     this.g2.dispose();
/*     */   }
/*     */ 
/*     */   public void paintAll(Graphics g) {
/* 181 */     g.drawImage(this.bufferBack, -this.sx, 0, null);
/*     */   }
/*     */ 
/*     */   public void move(int bx) {
/* 185 */     this.ox = this.lx;
/* 186 */     this.dx = (bx - this.lx);
/* 187 */     if (this.dx < 176)
/* 188 */       this.lx = Math.max(this.lx + (this.dx - 176), this.leftBound);
/* 189 */     else if (this.dx > 320) this.lx = Math.min(this.lx + (this.dx - 320), this.rightBound);
/* 190 */     this.sx = (this.lx & 0x1FF);
/* 191 */     if (this.lx < this.ox) {
/* 192 */       this.xx = (this.sx & 0xFFFFFFF0);
/* 193 */       this.ax = (this.lx >> 4);
/* 194 */       if (((this.lx ^ this.ox) & 0x200) != 0)
/* 195 */         this.gLandBack.copyArea(0, 0, 512, 512, 512, 0);
/*     */     } else {
/* 197 */       this.xx = ((this.sx & 0xFFFFFFF0) + 512);
/* 198 */       this.ax = ((this.lx >> 4) + 32);
/* 199 */       if (((this.lx ^ this.ox) & 0x200) != 0)
/* 200 */         this.gLandBack.copyArea(512, 0, 512, 512, -512, 0);
/*     */     }
/* 202 */     this.ay = (this.sx & 0xC);
/* 203 */     for (int yy = this.ay + 3; yy >= this.ay; yy--) {
/* 204 */       paintBlocks(this.xx, yy << 4, this.land[this.ax][yy].back, this.land[this.ax][yy].fore);
/* 205 */       if (this.land[this.ax][yy].sprite > 0) {
/* 206 */         Wiz3.bm.createBob(this.ax << 4, (yy << 4) + 16, this.ax, yy, 
/* 207 */           this.land[this.ax][yy].sprite, this.land[this.ax][yy].flag);
/* 208 */         this.land[this.ax][yy].sprite = (-this.land[this.ax][yy].sprite);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     LandScape
 * JD-Core Version:    0.6.2
 */