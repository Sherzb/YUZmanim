/*    */ import java.awt.Image;
/*    */ 
/*    */ final class BobFlame extends Bob
/*    */ {
/*    */   int aa;
/*    */ 
/*    */   BobFlame()
/*    */   {
/* 13 */     this.aa = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 2, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 23 */     this.aa += 1;
/* 24 */     this.a = ((this.aa & 0x20) / 16 + (this.aa & 0x1));
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobFlame
 * JD-Core Version:    0.6.2
 */