/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobGuard extends Bob
/*    */ {
/*    */   int aa;
/*    */   int xx;
/*    */   int yy;
/*    */ 
/*    */   BobGuard()
/*    */   {
/* 13 */     this.aa = 0;
/* 14 */     this.xx = -1;
/* 15 */     this.yy = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 20 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 25 */     if (!this.alive)
/*    */     {
/* 27 */       super.move();
/* 28 */       return;
/*    */     }
/* 30 */     if (!this.bm.blockAt(this.r.x + 8 - this.xx * 4, this.r.y + this.r.height, 3))
/*    */     {
/* 32 */       this.yy += (this.yy >= 16 ? 0 : 1);
/* 33 */       this.r.y += (this.yy >> 2);
/*    */     }
/*    */     else {
/* 36 */       this.r.y = ((this.r.y + this.r.height & 0xFFFFFFF0) - this.r.height);
/* 37 */       this.yy = 0;
/* 38 */       this.r.x += this.xx;
/*    */     }
/* 40 */     if (this.bm.blockAt(this.r.x + 8 + this.xx * 8, this.r.y + this.r.height - 1, 1))
/* 41 */       this.xx = (-this.xx);
/* 42 */     this.a = ((this.aa++ & 0x4) >> 2 | this.xx & 0x2);
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 47 */     return (this.r.x - lx < -512) || (this.r.x - lx > 1023) || (this.r.y > 256);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobGuard
 * JD-Core Version:    0.6.2
 */