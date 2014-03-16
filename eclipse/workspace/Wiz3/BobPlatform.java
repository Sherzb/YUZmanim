/*    */ import java.awt.Image;
/*    */ 
/*    */ class BobPlatform extends Bob
/*    */ {
/*    */   int xx;
/*    */   int yy;
/*    */   int ii;
/*    */   int c;
/*    */   boolean on;
/*    */ 
/*    */   BobPlatform()
/*    */   {
/* 13 */     this.on = false;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, y - i.getHeight(null), 32, i.getHeight(null), 0, sx, sy, 4, i, bm, flag);
/* 19 */     this.ii = (flag >= 0 ? 2 : -2);
/* 20 */     this.flag = (Math.abs(flag) / 2);
/* 21 */     this.c = this.flag;
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobPlatform
 * JD-Core Version:    0.6.2
 */