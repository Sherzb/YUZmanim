/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobFireball extends Bob
/*    */ {
/*    */   int xx;
/*    */   int yy;
/*    */ 
/*    */   BobFireball()
/*    */   {
/* 13 */     this.xx = -2;
/* 14 */     this.yy = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 19 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 2, i, bm, flag);
/* 20 */     this.yy = flag;
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 25 */     if (!this.bm.blockAt(this.r.x + 8, this.r.y + this.r.height, 3))
/*    */     {
/* 27 */       this.yy += (this.yy >= 14 ? 0 : 2);
/* 28 */       this.r.y += (this.yy >> 2);
/* 29 */       this.r.x += this.xx;
/*    */     }
/*    */     else {
/* 32 */       this.r.y = ((this.r.y + this.r.height & 0xFFFFFFF0) - this.r.height);
/* 33 */       this.yy = 1;
/* 34 */       this.r.x += this.xx;
/*    */     }
/* 36 */     if (this.bm.blockAt(this.r.x + 8 + this.xx * 4, this.r.y + this.r.height - this.yy, 1))
/* 37 */       this.xx = (-this.xx);
/* 38 */     this.a += 1;
/* 39 */     this.a &= 3;
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 44 */     return (this.r.x - lx < -512) || (this.r.x - lx > 1023) || (this.r.y > 256);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobFireball
 * JD-Core Version:    0.6.2
 */