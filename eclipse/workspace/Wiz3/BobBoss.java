/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobBoss extends Bob
/*    */ {
/*    */   int bx;
/*    */   int by;
/*    */   static int sx;
/*    */   static int sy;
/*    */   int yy;
/*    */   int y1;
/*    */   int xx;
/*    */   int x1;
/*    */   int c;
/*    */ 
/*    */   BobBoss()
/*    */   {
/* 13 */     this.yy = 16;
/* 14 */     this.y1 = 1;
/* 15 */     this.xx = 8;
/* 16 */     this.x1 = 1;
/* 17 */     this.c = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 22 */     super.init(x, y - i.getHeight(null) + 16, 64, i.getHeight(null), 0, sx, sy, 1, i, bm, flag);
/* 23 */     this.bx = this.r.x;
/* 24 */     this.by = this.r.y;
/* 25 */     if (flag > 0)
/*    */     {
/* 27 */       this.touch = 0;
/* 28 */       this.a = 1;
/*    */     }
/*    */     else {
/* 31 */       sx = this.r.x;
/* 32 */       sy = this.r.y;
/* 33 */       this.a = 0;
/*    */     }
/* 35 */     if (flag < 8)
/* 36 */       bm.createBob(x, y, 0, 0, 20, flag + 1);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 41 */     this.c += 1;
/* 42 */     if (this.c < 8)
/* 43 */       return;
/* 44 */     if (this.flag == 0)
/*    */     {
/* 46 */       if (this.r.x > this.bx)
/* 47 */         this.x1 = -1;
/*    */       else
/* 49 */         this.x1 = 1;
/* 50 */       if (this.r.y > this.by - 48)
/* 51 */         this.y1 = -1;
/*    */       else
/* 53 */         this.y1 = 1;
/* 54 */       if ((this.r.y > 224) && (this.c > 14))
/*    */       {
/* 56 */         this.bm.createBob(this.r.x, this.r.y + 16, 0, 0, 13, -32);
/* 57 */         this.c = 8;
/*    */       }
/* 59 */       this.xx += this.x1;
/* 60 */       this.yy += this.y1;
/* 61 */       this.r.x += this.xx / 4;
/* 62 */       this.r.y += this.yy / 4;
/* 63 */       sx = this.r.x;
/* 64 */       sy = this.r.y;
/*    */     }
/*    */     else {
/* 67 */       this.r.x = (sx + (this.bx - sx) * this.flag / 8);
/* 68 */       this.r.y = (sy + (this.by - sy) * this.flag / 8);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobBoss
 * JD-Core Version:    0.6.2
 */