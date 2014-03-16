/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobBottle extends Bob
/*    */ {
/*    */   int yy;
/*    */ 
/*    */   BobBottle()
/*    */   {
/* 13 */     this.yy = -8;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 0, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 23 */     this.a += 1;
/* 24 */     this.a &= 7;
/* 25 */     this.r.y += this.yy;
/* 26 */     this.yy += 1;
/* 27 */     this.r.x -= 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobBottle
 * JD-Core Version:    0.6.2
 */