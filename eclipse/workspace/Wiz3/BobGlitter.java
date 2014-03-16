/*    */ import java.applet.AudioClip;
/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Vector;
/*    */ 
/*    */ final class BobGlitter extends Bob
/*    */ {
/*    */   int c;
/*    */   Bob b;
/*    */ 
/*    */   BobGlitter()
/*    */   {
/* 13 */     this.c = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 18 */     super.init(x, y - i.getHeight(bm.wiz3), 32, i.getHeight(bm.wiz3), 0, sx, sy, 0, i, bm, flag);
/* 19 */     this.b = ((Bob)bm.bob.elementAt(0));
/* 20 */     bm.au[9].play();
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 25 */     this.r.x = (this.b.r.x - 8);
/* 26 */     this.r.y = (this.b.r.y - 8);
/* 27 */     this.c += 1;
/* 28 */     this.a = (this.c / 2 & 0x3);
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx)
/*    */   {
/* 33 */     return this.c > 32;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobGlitter
 * JD-Core Version:    0.6.2
 */