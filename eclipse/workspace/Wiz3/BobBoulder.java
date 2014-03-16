/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobBoulder extends Bob
/*    */ {
/*    */   int yy;
/*    */   int c;
/*    */   int aa;
/*    */ 
/*    */   BobBoulder()
/*    */   {
/* 13 */     this.yy = 0;
/* 14 */     this.c = 0;
/* 15 */     this.aa = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 20 */     super.init(x, y - 256, 16, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 25 */     if (!this.alive)
/*    */     {
/* 27 */       super.move();
/* 28 */       return;
/*    */     }
/* 30 */     if ((this.bm.blockAt(this.r.x, this.r.y + this.r.height, 3)) && (!this.bm.blockAt(this.r.x, this.r.y + this.r.height - 16, 3)) && (this.r.y > this.c))
/*    */     {
/* 32 */       this.r.y &= -16;
/* 33 */       this.c = (this.r.y + 16);
/* 34 */       this.yy = -16;
/*    */     }
/* 36 */     this.yy += (this.yy >= 16 ? 0 : 2);
/* 37 */     this.r.y += (this.yy >> 2);
/* 38 */     if (this.r.y > 256)
/*    */     {
/* 40 */       this.r.y = -64;
/* 41 */       this.c = 0;
/*    */     }
/* 43 */     this.a = ((this.aa++ & 0x6) >> 1);
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 48 */     return (this.r.x - lx < -16) || (this.r.x - lx > 512) || (this.r.y > 512);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobBoulder
 * JD-Core Version:    0.6.2
 */