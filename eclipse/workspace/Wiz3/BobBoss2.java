/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobBoss2 extends Bob
/*    */ {
/*    */   static int sx;
/*    */   static int sy;
/*    */   static int ex;
/*    */   static int ey;
/* 82 */   static boolean oob = false;
/*    */   int yy;
/*    */   int y1;
/*    */   int xx;
/*    */   int x1;
/*    */   int c;
/*    */ 
/*    */   BobBoss2()
/*    */   {
/* 13 */     this.yy = 0;
/* 14 */     this.y1 = -1;
/* 15 */     this.xx = -24;
/* 16 */     this.x1 = -1;
/* 17 */     this.c = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 22 */     super.init(x, y - i.getHeight(null), 64, i.getHeight(null), 0, sx, sy, 1, i, bm, flag);
/* 23 */     if (flag > 0)
/*    */     {
/* 25 */       oob = false;
/* 26 */       this.a = 1;
/*    */     }
/*    */     else {
/* 29 */       sx = this.r.x;
/* 30 */       sy = this.r.y;
/* 31 */       this.a = 0;
/*    */     }
/* 33 */     if (flag < 8)
/*    */     {
/* 35 */       bm.createBob(x, y - 16, 0, 0, 22, flag + 1);
/*    */     }
/*    */     else {
/* 38 */       ex = this.r.x;
/* 39 */       ey = this.r.y;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 45 */     this.c += 1;
/* 46 */     if (this.c < 8)
/* 47 */       return;
/* 48 */     if (this.flag == 0)
/*    */     {
/* 50 */       if (this.xx == 24)
/* 51 */         this.x1 = -1;
/* 52 */       if (this.xx == -24)
/* 53 */         this.x1 = 1;
/* 54 */       if (this.yy == 12)
/* 55 */         this.y1 = -1;
/* 56 */       if (this.yy == -12)
/* 57 */         this.y1 = 1;
/* 58 */       this.xx += this.x1;
/* 59 */       this.yy += this.y1;
/* 60 */       this.r.x += this.xx / 4;
/* 61 */       this.r.y += this.yy / 4;
/* 62 */       sx = this.r.x;
/* 63 */       sy = this.r.y;
/*    */     }
/*    */     else {
/* 66 */       this.r.x = (sx + (ex - sx) * this.flag / 8);
/* 67 */       this.r.y = (sy + (ey - sy) * this.flag / 8);
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 73 */     if ((this.r.x - lx < -96) || (this.r.x - lx > 544) || (oob))
/* 74 */       oob = true;
/* 75 */     return oob;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobBoss2
 * JD-Core Version:    0.6.2
 */