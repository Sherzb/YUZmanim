/*    */ import java.applet.AudioClip;
/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ final class BobFPlatform extends BobPlatform
/*    */ {
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 17 */     super.init(x, y, sx, sy, i, bm, flag);
/* 18 */     this.yy = 0;
/* 19 */     this.ii = 0;
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 24 */     this.r.y += (this.yy = this.ii / 8);
/* 25 */     if (this.on)
/*    */     {
/* 27 */       this.ii += 1;
/* 28 */       if (this.ii == 8)
/* 29 */         this.bm.au[4].play();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobFPlatform
 * JD-Core Version:    0.6.2
 */