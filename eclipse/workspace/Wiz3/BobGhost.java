/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Vector;
/*    */ 
/*    */ final class BobGhost extends Bob
/*    */ {
/*    */   int xx;
/*    */   int yy;
/*    */   int aa;
/*    */   Bob b;
/*    */ 
/*    */   BobGhost()
/*    */   {
/* 13 */     this.xx = 0;
/* 14 */     this.yy = 0;
/* 15 */     this.aa = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 20 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/* 21 */     this.b = ((Bob)bm.bob.elementAt(0));
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 26 */     if ((this.r.y < this.b.r.y) && (this.yy < 16))
/*    */     {
/* 28 */       this.yy += 1;
/* 29 */       if ((this.r.x < this.b.r.x) && (this.xx < 16)) {
/* 30 */         this.xx += 1;
/*    */       }
/* 32 */       else if (this.xx > -16)
/* 33 */         this.xx -= 1;
/*    */     }
/* 35 */     else if (this.yy > -16)
/*    */     {
/* 37 */       this.yy -= 1;
/* 38 */       this.xx = (this.xx >= 0 ? 8 : -8);
/*    */     }
/* 40 */     this.r.x += this.xx / 8;
/* 41 */     this.r.y += this.yy / 8;
/* 42 */     this.aa += 1;
/* 43 */     this.aa &= 7;
/* 44 */     this.a = (this.xx >= 0 ? 2 + this.aa / 4 : this.aa / 4);
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 49 */     return (this.r.x - lx < -512) || (this.r.x - lx > 1023) || (this.r.y > 256);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobGhost
 * JD-Core Version:    0.6.2
 */