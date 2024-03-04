import java.util.Scanner;
import java.util.*;

public class grids {
    public static final int NumRows = 10;
    public static final int NumCols = 10;
 
 //int xCoordinate;
 //String yCoordinate;
 //boolean hit;
 //boolean attacked;
 public int counter;
 Grid[][] maps;

 public int shots;
 public int hits;
 public int misses;

 //public static Rand randmizer = new Rand();
 
 public grids() 
 {
  initialMaps();

  counter = 0;
  shots = 0;
  hits = 0;
  misses = 0;

 }

 public void initialMaps()
 {
  maps = new Grid[NumRows][NumCols];
  for(int i=0;i<NumRows; i++)
  {
   for(int j=0;j<NumCols;j++)
   {
    Grid loc = new Grid();
    loc.setPos(i, j);
    maps[i][j] = loc;
   }
  }
 }


 boolean isEnd() {
  if(counter == 5)
  {
   return true;
  }
  else 
   return false;
 }
 
 public void initializeAndPlacementOfShip(Battleship p)
 {
  int dir;
  int x;
  int y;
  
  
  int idx = 0;
  while(true)
  {
   x = Rand.nextInt(0,9);
   y = Rand.nextInt(0,9);
   dir = Rand.nextInt(0,3); //0:from left to right 1:from right to left 2:from up to down 3:from down to up
   System.out.println("initializeAndPlacementOfShip, x-"+x+", y-"+y+", dir-"+dir);
   if(canput(x,y,dir, p.getLength()))
   {
    if(dir == 0)
    {//from left to right
     for(int i=y;i<(y+p.getLength()); i++)
     {
      maps[x][i].setShip(true);
      p.loc_grids[idx] = maps[x][i];
      idx++;
     }
    }
    else if(dir == 1)
    {
     for(int i=(y-p.getLength()+1);i<y; i++)
     {
      maps[x][i].setShip(true);
      p.loc_grids[idx] = maps[x][i];
      idx++;     }
    }
    else if(dir == 2)
    {
     for(int i=x;i<(x+p.getLength());i++)
     {
      maps[i][y].setShip(true);
      p.loc_grids[idx] = maps[i][y];
      idx++;
     }
    }
    else if(dir == 3)
    {
     for(int i=(x-p.getLength()+1);i<x;i++)
     {
      maps[i][y].setShip(true);
      p.loc_grids[idx] = maps[i][y];
      idx++;
     }     
    }
    break;
   }
  }
 }
 
 public boolean canput(int row, int col, int dir, int len)
 {
  if (dir == 0)
  {//from left to right
   if ((col+1+len)>= NumCols)
   {
    return false;
   }
   for(int i=col;i<(col+len);i++)
   {
    if(this.maps[row][i].hasShip())
    {
     return false;
    }
   }
   return true;
  }
  else if (dir == 1)
  {// from right to left
   if ((col+1 - len) <0)
   {
    return false;
   }
   for(int i=(col+1 -len);i<=col; i++)
   {
    if (this.maps[row][i].hasShip())
    {
     return false;
    }
   }
   return true;
  }
  else if(dir == 2)
  {//from bottom to top
   if((row+1+len)>=NumRows)
   {
    return false;
   }
   for(int i=row; i<(row+len);i++)
   {
    if(this.maps[i][col].hasShip())
    {
     return false;
    }
   }
   return true;
  }
  else if(dir ==3)
  {// from top to bottom
   if((row+1-len)<0)
   {
    return false;
   }
   for(int i=(row+1-len); i<=row;i++)
   {
    if(this.maps[i][col].hasShip())
    {
     return false;
    }
   } 
   return true;
  }
  return false;
 }


 void displayInfor()
 {
  System.out.println("Num Shots:" + shots);
  System.out.println("Num Hits:" + hits + " Num Misses:"+misses);
  System.out.println("Left ship:" + (5-counter));
 }

}
