/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobVPlatform extends BobPlatform
/*    */ {
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 17 */     super.init(x, y, sx, sy, i, bm, flag);
/* 18 */     this.yy = this.ii;
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 23 */     if (--this.c < 0)
/*    */     {
/* 25 */       this.yy = (-this.yy);
/* 26 */       this.c = this.flag;
/*    */     }
/* 28 */     if (this.r.y == 256)
/*    */     {
/* 30 */       this.r.y = -16;
/* 31 */       this.c = this.flag;
/*    */     }
/* 33 */     this.r.y += this.yy;
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 38 */     return (this.r.x - lx < -512) || (this.r.x - lx > 1023) || (this.r.y > 256);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobVPlatform
 * JD-Core Version:    0.6.2
 */