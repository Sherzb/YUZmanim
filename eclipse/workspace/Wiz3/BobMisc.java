/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobMisc extends Bob
/*    */ {
/*    */   int c;
/*    */ 
/*    */   BobMisc()
/*    */   {
/* 13 */     this.c = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, y - 16, 16, 16, 0, sx, sy, 0, i, bm, flag);
/* 19 */     this.flag *= 16;
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 24 */     this.c += 1;
/* 25 */     this.r.y -= (this.flag <= 0 ? 0 : 1);
/* 26 */     this.a += 1;
/* 27 */     this.a &= 7;
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g)
/*    */   {
/* 32 */     this.g2 = g.create(this.r.x - Wiz3.ls.lx, this.r.y, this.r.width, this.r.height);
/* 33 */     this.g2.drawImage(this.i, -this.a * this.r.width, -this.flag, null);
/* 34 */     this.g2.dispose();
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 39 */     return (this.c > 32) || ((this.flag == 0) && (this.c > 7));
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobMisc
 * JD-Core Version:    0.6.2
 */