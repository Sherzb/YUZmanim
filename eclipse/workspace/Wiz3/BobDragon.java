/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobDragon extends Bob
/*    */ {
/*    */   int aa;
/*    */   int c;
/*    */ 
/*    */   BobDragon()
/*    */   {
/* 13 */     this.aa = 0;
/* 14 */     this.c = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 19 */     super.init(x, y - i.getHeight(bm.wiz3), 32, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 24 */     if (!this.alive)
/*    */     {
/* 26 */       super.move();
/* 27 */       return;
/*    */     }
/* 29 */     this.a = ((this.aa++ & 0xC) >> 2);
/* 30 */     if (this.bm.blockAt(this.r.x, this.r.y + this.r.height, 3))
/* 31 */       this.yy = 0;
/* 32 */     this.c += 1;
/* 33 */     if (this.c % 50 == 0)
/*    */     {
/* 35 */       this.yy = -19;
/* 36 */       if (this.c == 200)
/*    */       {
/* 38 */         this.c = 0;
/* 39 */         this.yy = -25;
/*    */       }
/*    */     }
/* 42 */     this.yy += (this.yy != 0 ? 2 : 0);
/* 43 */     this.r.y += this.yy / 2;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobDragon
 * JD-Core Version:    0.6.2
 */