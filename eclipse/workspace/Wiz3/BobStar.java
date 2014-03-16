/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobStar extends Bob
/*    */ {
/*    */   int xx;
/*    */   int yy;
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 17 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 0, i, bm, flag);
/* 18 */     if (flag == 0)
/*    */     {
/* 20 */       this.xx = 0;
/* 21 */       this.yy = -8;
/*    */     }
/* 23 */     else if (flag == 1)
/*    */     {
/* 25 */       this.xx = 0;
/* 26 */       this.yy = -24;
/*    */     }
/* 28 */     else if (flag == 2)
/*    */     {
/* 30 */       this.xx = 16;
/* 31 */       this.yy = -16;
/*    */     }
/* 33 */     else if (flag == 3)
/*    */     {
/* 35 */       this.xx = 24;
/* 36 */       this.yy = 0;
/*    */     }
/* 38 */     else if (flag == 4)
/*    */     {
/* 40 */       this.xx = 16;
/* 41 */       this.yy = 16;
/*    */     }
/* 43 */     else if (flag == 5)
/*    */     {
/* 45 */       this.xx = 0;
/* 46 */       this.yy = 24;
/*    */     }
/* 48 */     else if (flag == 6)
/*    */     {
/* 50 */       this.xx = -16;
/* 51 */       this.yy = 16;
/*    */     }
/* 53 */     else if (flag == 7)
/*    */     {
/* 55 */       this.xx = -24;
/* 56 */       this.yy = 0;
/*    */     }
/* 58 */     else if (flag == 8)
/*    */     {
/* 60 */       this.xx = -16;
/* 61 */       this.yy = -16;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 67 */     this.a += 1;
/* 68 */     this.a &= 7;
/* 69 */     this.r.y += (this.yy >> 2);
/* 70 */     this.r.x += (this.xx >> 2);
/* 71 */     this.yy += 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobStar
 * JD-Core Version:    0.6.2
 */