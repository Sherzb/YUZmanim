/*    */ import java.awt.Image;
/*    */ 
/*    */ final class BobFire extends Bob
/*    */ {
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 17 */     super.init(x, y - i.getHeight(bm.wiz3), 32, i.getHeight(bm.wiz3), 0, sx, sy, 2, i, bm, flag);
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 22 */     this.a += 1;
/* 23 */     this.a &= 3;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobFire
 * JD-Core Version:    0.6.2
 */