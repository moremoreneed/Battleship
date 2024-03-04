import java.util.*;

public class Rand {

  public static Random theInstance = null;
  
  public Rand(){
   
  }
  
  public static Random getInstance(){
   if(theInstance == null){
    theInstance = new Random();
   }
   return theInstance;
  }
  
  public static boolean nextBoolean(){
   return Rand.getInstance().nextBoolean();
  }

  public static boolean nextBoolean(double probability){
   return Rand.nextDouble() < probability;
  }
  
  public static int nextInt(){
   return Rand.getInstance().nextInt();
  }

  public static int nextInt(int n){
   return Rand.getInstance().nextInt(n);
  }

  public static int nextInt(int min, int max){
   return min + Rand.nextInt(max - min + 1);
  }

  public static double nextDouble(){
   return Rand.getInstance().nextDouble();
  }

  public static double nextDouble(double min, double max){
   return min + (max - min) * Rand.nextDouble();
  }
}
