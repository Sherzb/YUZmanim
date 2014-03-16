/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobFish extends Bob
/*    */ {
/*    */   int yy;
/*    */ 
/*    */   BobFish()
/*    */   {
/* 13 */     this.yy = -16;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, 320, 16, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 23 */     if (!this.alive)
/*    */     {
/* 25 */       super.move();
/* 26 */       return;
/*    */     }
/* 28 */     this.r.y += this.yy / 2;
/* 29 */     this.yy += 1;
/* 30 */     if (this.r.y > 320)
/* 31 */       this.yy = -28;
/* 32 */     this.a = ((this.yy <= 0 ? 0 : 2) + (this.yy & 0x1));
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 37 */     return (this.r.x - lx < -16) || (this.r.x - lx > 512) || (this.r.y > 512);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobFish
 * JD-Core Version:    0.6.2
 */