/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobArrow extends Bob
/*    */ {
/*    */   boolean h;
/*    */   int xx;
/*    */   int d;
/*    */   int yy;
/*    */ 
/*    */   BobArrow()
/*    */   {
/* 13 */     this.h = false;
/* 14 */     this.yy = -4;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 19 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/* 20 */     this.d = (flag == 0 ? 0 : 4);
/* 21 */     this.xx = (this.d * 2 - 4);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 26 */     if (!this.alive)
/*    */     {
/* 28 */       super.move();
/* 29 */       return;
/*    */     }
/* 31 */     this.a += 1;
/* 32 */     this.a = (this.a & 0x3 | this.d);
/* 33 */     if (this.bm.blockAt(this.r.x + 8 + this.xx, this.r.y, 1))
/*    */     {
/* 35 */       this.h = true;
/* 36 */       this.touch = 0;
/*    */     }
/* 38 */     if (!this.h)
/*    */     {
/* 40 */       this.r.x += this.xx;
/*    */     }
/*    */     else {
/* 43 */       this.r.x -= this.xx / 2;
/* 44 */       this.r.y += this.yy;
/* 45 */       this.yy += 1;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobArrow
 * JD-Core Version:    0.6.2
 */