/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Vector;
/*    */ 
/*    */ final class BobArcher extends Bob
/*    */ {
/*    */   int c;
/*    */   Bob b;
/*    */ 
/*    */   BobArcher()
/*    */   {
/* 13 */     this.c = 48;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/* 19 */     this.b = ((Bob)bm.bob.elementAt(0));
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 24 */     if (!this.alive)
/*    */     {
/* 26 */       super.move();
/* 27 */       return;
/*    */     }
/* 29 */     this.c += 1;
/* 30 */     if (this.c == 56)
/* 31 */       this.a = (this.b.r.x <= this.r.x ? 1 : 3);
/* 32 */     if (this.c == 64)
/*    */     {
/* 34 */       this.a &= -2;
/* 35 */       this.c = 0;
/* 36 */       this.bm.createBob(this.r.x + (this.a & 0x2) * 4 - 4, this.r.y + 12, 0, 0, 4, this.a & 0x2);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobArcher
 * JD-Core Version:    0.6.2
 */