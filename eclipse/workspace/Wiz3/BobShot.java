/*    */ import java.applet.AudioClip;
/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Vector;
/*    */ 
/*    */ final class BobShot extends Bob
/*    */ {
/*    */   int xx;
/*    */   int yy;
/*    */   Bob b;
/*    */   Bob b1;
/*    */   int bb;
/*    */ 
/*    */   BobShot()
/*    */   {
/* 13 */     this.xx = 0;
/* 14 */     this.yy = 0;
/* 15 */     this.bb = 0;
/*    */   }
/*    */ 
/*    */   public void init(int x, int y, int sx, int sy, Image i, BobManager bm, int flag)
/*    */   {
/* 20 */     super.init(x, y - i.getHeight(bm.wiz3), 16, i.getHeight(bm.wiz3), 0, sx, sy, 0, i, bm, flag);
/* 21 */     this.b = ((Bob)bm.bob.elementAt(0));
/* 22 */     this.r.x = (this.b.r.x + 8);
/* 23 */     this.r.y = -16;
/*    */   }
/*    */ 
/*    */   public void move()
/*    */   {
/* 28 */     if ((this.r.x < this.b.r.x) && (this.xx < 16)) {
/* 29 */       this.xx += 1;
/*    */     }
/* 31 */     else if (this.xx > -16)
/* 32 */       this.xx -= 1;
/* 33 */     if ((this.r.y < this.b.r.y - 4) && (this.yy < 12)) {
/* 34 */       this.yy += 1;
/*    */     }
/* 36 */     else if (this.yy > -12)
/* 37 */       this.yy -= 1;
/* 38 */     this.r.x += this.xx / 4;
/* 39 */     this.r.y += this.yy / 4;
/* 40 */     this.a += 1;
/* 41 */     this.a &= 3;
/* 42 */     if (this.b.touch != 1)
/*    */     {
/* 44 */       this.bb = ((this.bb + 1) % this.bm.bob.size());
/* 45 */       this.b1 = ((Bob)this.bm.bob.elementAt(this.bb));
/* 46 */       if ((this.b1.touch == 1) && (this.b1.r.x > Wiz3.ls.lx) && (this.b1.r.x < Wiz3.ls.lx + 480))
/* 47 */         this.b = ((Bob)this.bm.bob.elementAt(this.bb));
/*    */     }
/* 49 */     else if (this.r.intersects(this.b.r))
/*    */     {
/* 51 */       this.xx = 0;
/* 52 */       this.yy = 0;
/* 53 */       Score.addScore(500);
/* 54 */       this.b.touch = 0;
/* 55 */       this.b.alive = false;
/* 56 */       this.b = ((Bob)this.bm.bob.elementAt(0));
/* 57 */       this.bm.au[4].play();
/*    */     }
/* 59 */     else if ((this.b.r.x < Wiz3.ls.lx) || (this.b.r.x > Wiz3.ls.lx + 480) || (this.b.r.y > 248)) {
/* 60 */       this.b = ((Bob)this.bm.bob.elementAt(0));
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean outOfBounds(int lx) {
/* 65 */     return this.r.y > 1024;
/*    */   }
/*    */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     BobShot
 * JD-Core Version:    0.6.2
 */