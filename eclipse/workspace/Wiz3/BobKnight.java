/*    */ import java.awt.Image;
/*    */ 
/*    */ final class BobKnight extends Bob
/*    */ {
/*    */   int aa;
/*    */ 
/*    */   BobKnight()
/*    */   {
/* 13 */     this.aa = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 1, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 23 */     if (!this.alive)
/*    */     {
/* 25 */       super.move();
/* 26 */       return;
/*    */     }
/* 28 */     this.a = ((this.aa++ & 0x18) / 24);
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobKnight
 * JD-Core Version:    0.6.2
 */