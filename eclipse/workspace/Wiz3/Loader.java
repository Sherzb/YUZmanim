/*     */ import java.applet.Applet;
/*     */ import java.applet.AudioClip;
/*     */ import java.awt.Image;
/*     */ import java.awt.MediaTracker;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ final class Loader
/*     */ {
/*     */   private static MediaTracker tracker;
/*     */   static String cookie;
/*     */ 
/*     */   public static Image getImage(String gif, Applet parent)
/*     */   {
/*  26 */     Image i = parent.getImage(parent.getCodeBase(), "images/" + gif);
/*  27 */     tracker = new MediaTracker(parent);
/*  28 */     tracker.addImage(i, 0);
/*     */     try {
/*  30 */       tracker.waitForID(0);
/*     */     } catch (InterruptedException localInterruptedException) {
/*     */     }
/*  33 */     if ((tracker.statusID(0, true) & 0x4) != 0)
/*  34 */       System.err.println("ERROR loading image " + gif);
/*  35 */     return i;
/*     */   }
/*     */ 
/*     */   public static AudioClip getAudio(String au, Applet parent) {
/*  39 */     AudioClip a = parent.getAudioClip(parent.getCodeBase(), "sounds/" + au);
/*  40 */     return a;
/*     */   }
/*     */ 
/*     */   public static void landLoad(String landFile, int level, Land[][] land, Wiz3 parent) throws IOException
/*     */   {
/*     */     try {
/*  46 */       URL inputURL = new URL(parent.getCodeBase() + "/data/" + landFile);
/*  47 */       DataInputStream in = new DataInputStream(new BufferedInputStream(
/*  48 */         inputURL.openStream()));
/*  49 */       Score.initProgress(16, "Loading Level " + level);
/*  50 */       for (int y = 0; y < 16; y++) {
/*  51 */         for (int x = 0; x < 256; x++) {
/*  52 */           land[x][y].back = in.readByte();
/*  53 */           land[x][y].fore = in.readByte();
/*  54 */           land[x][y].flag = in.readInt();
/*  55 */           land[x][y].sprite = in.readByte();
/*  56 */           land[x][y].bonus = in.readByte();
/*  57 */           land[x][y].block = in.readByte();
/*  58 */           land[x][y].back += (land[x][y].back >= 0 ? 0 : 256);
/*  59 */           land[x][y].fore += (land[x][y].fore >= 0 ? 0 : 256);
/*  60 */           land[x][y].sprite += (land[x][y].sprite >= 0 ? 0 : 256);
/*  61 */           land[x][y].bonus += (land[x][y].bonus >= 0 ? 0 : 256);
/*  62 */           land[x][y].block += (land[x][y].block >= 0 ? 0 : 256);
/*     */         }
/*     */ 
/*  65 */         Score.incProgress(parent);
/*     */       }
/*     */ 
/*  68 */       in.close();
/*     */     } catch (FileNotFoundException e) {
/*  70 */       throw new IOException();
/*     */     } catch (IOException e) {
/*  72 */       throw new IOException();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getNumLevels(Applet parent) {
/*  77 */     int numLevels = 1;
/*  78 */     BufferedInputStream bis = null;
/*  79 */     String landFile = "level.";
/*  80 */     int l = 1;
/*     */     while (true) {
/*     */       try {
/*  83 */         URL inputURL = new URL(parent.getCodeBase() + "/data/" + 
/*  84 */           landFile + l);
/*  85 */         bis = new BufferedInputStream(inputURL.openStream());
/*  86 */         bis.close();
/*     */       } catch (FileNotFoundException e) {
/*     */         try {
/*  89 */           if (bis != null) bis.close(); 
/*     */         }
/*  91 */         catch (IOException ee) { ee.printStackTrace(); }
/*     */ 
/*  93 */         numLevels = l - 1;
/*  94 */         break;
/*     */       } catch (IOException e) {
/*     */         try {
/*  97 */           bis.close();
/*     */         } catch (IOException ee) {
/*  99 */           ee.printStackTrace();
/*     */         }
/* 101 */         numLevels = l - 1;
/* 102 */         break;
/*     */       }
/* 104 */       l++;
/*     */     }
/* 106 */     return numLevels;
/*     */   }
/*     */ }

/* Location:           C:\Users\Shmuel\Downloads\wiz3v5.jar
 * Qualified Name:     Loader
 * JD-Core Version:    0.6.2
 */