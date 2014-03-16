/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobHPlatform extends BobPlatform
/*    */ {
/*    */   int x1;
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 17 */     super.init(x, y, sx, sy, i, bm, flag);
/* 18 */     this.xx = this.ii;
/* 19 */     this.x1 = (this.xx >= 0 ? 1 : -1);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 24 */     if ((--this.c < 0) || (this.bm.blockAt(this.r.x - 16, this.r.y, 1)) || (this.bm.blockAt(this.r.x + this.r.width + 16, this.r.y, 1)))
/*    */     {
/* 26 */       this.x1 = (-this.x1);
/* 27 */       this.c = this.flag;
/*    */     }
/* 29 */     if (((this.xx > -8) && (this.x1 < 0)) || ((this.xx < 8) && (this.x1 > 0)))
/* 30 */       this.xx += this.x1;
/* 31 */     this.r.x += this.xx / 4;
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 36 */     return (this.r.x - lx < -512) || (this.r.x - lx > 1023) || (this.r.y > 256);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobHPlatform
 * JD-Core Version:    0.6.2
 */