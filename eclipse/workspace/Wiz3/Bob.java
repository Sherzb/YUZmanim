/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.awt.Point;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ class Bob
/*    */ {
/*    */   BobManager bm;
/*    */   Image i;
/*    */   int a;
/*    */   int bx;
/*    */   Graphics g2;
/*    */   Rectangle r;
/*    */   Point start;
/*    */   int flag;
/*    */   boolean alive;
/*    */   int yy;
/*    */   int touch;
/*    */   static final int FRIENDLY = 0;
/*    */   static final int FATAL = 1;
/*    */   static final int FIRE = 2;
/*    */   static final int BONUS = 3;
/*    */   static final int PLATFORM = 4;
/*    */ 
/*    */   Bob()
/*    */   {
/* 13 */     this.yy = -16;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     init(x, y, 0, 0, 0, sx, sy, this.touch, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int w, int h, int a, int sx, int sy, int touch, Image i, BobManager bm, int flag)
/*    */   {
/* 24 */     this.r = new Rectangle(x, y, w, h);
/* 25 */     this.start = new Point(sx, sy);
/* 26 */     this.a = a;
/* 27 */     this.i = i;
/* 28 */     this.bm = bm;
/* 29 */     this.touch = touch;
/* 30 */     this.flag = flag;
/* 31 */     this.alive = true;
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g)
/*    */   {
/* 36 */     this.g2 = g.create(this.r.x - Wiz3.ls.lx, this.r.y, this.r.width, this.r.height);
/* 37 */     this.g2.drawImage(this.i, -this.a * this.r.width, 0, null);
/* 38 */     this.g2.dispose();
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 43 */     return (this.r.x - lx < -16) || (this.r.x - lx > 512) || (this.r.y > 256);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 48 */     this.r.y += this.yy / 2;
/* 49 */     this.yy += 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     Bob
 * JD-Core Version:    0.6.2
 */