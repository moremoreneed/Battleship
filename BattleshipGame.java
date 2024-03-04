import java.util.Scanner;
public class BattleshipGame {
    public static final int NumShips = 5;
    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    public static final int NumRows = 10;
    public static final int NumCols = 10;
    
    public static Scanner reader = new Scanner(System.in);
    public Rand rander = new Rand();
    
 Battleship[] ships;
 grids myGrids = new grids();
 grids peerGrids = new grids();
 
 //Grid lastGrid = null;
 //Grid[][] userGrids;
 Grid lastHitGrid = null;
 // 0 - horizontal, 1 - vertical
 int lastDirection = 0;
 
 public static void main(String[] args) 
 {
  BattleshipGame game = new BattleshipGame();
  
  //game.initialUserGrids();
  game.ships = new Battleship[NumShips];
  for(int i=0;i<NumShips;i++)
  {
   Battleship tmp_ship = new Battleship(game.toName(i), SHIP_LENGTHS[i]);
   game.ships[i] = tmp_ship;
  }

  for(int i=0;i<NumShips;i++)
  {
   game.myGrids.initializeAndPlacementOfShip(game.ships[i]);   
  }
  int greet = game.greeting();
  
  while (true)
  {
   if (greet==0)  //user first
   {
    System.out.println("Please input the Row:");
    int x = reader.nextInt();
    System.out.println("Please intput the Col:");
    int y = reader.nextInt();
    game.hitOrMiss(x,y);
    // switch competitor
    greet = 1;
   }
   else if(greet == 1)
   {
    game.attack();
    
    greet = 0;
   }
   else 
   {
    break;
   }
  }
 }
 
 void attack()
 {
  if (lastHitGrid == null)
  {
   while (true)
   {
    //Generate the location randomly
    int x = rander.nextInt(0,9);
    int y = rander.nextInt(0,9);
    
    if (peerGrids.maps[x][y].isAttacked())
    {
     continue;
    }
    else 
    {
     System.out.println("The guessed coorinate: row-"+x+", col-"+y+", please tell Hit or Miss; Hit:0 Miss:1");
     int result = reader.nextInt();
     if(result == 0)
     {
      peerGrids.maps[x][y].setStatus(Grid.HIT);
      
      lastHitGrid = peerGrids.maps[x][y];
      peerGrids.hits++;
      
      System.out.println("Please tell if the ship is sunk: 1 - sunk");
      result = reader.nextInt();
      if (result == 1)
      {
       System.out.println("I am so great!");
       lastHitGrid = null;
       
       peerGrids.hits = 0;
      }
     }
     else
     {
      peerGrids.maps[x][y].setStatus(Grid.MISSED);
     }
     break;
    }
   }

  }
  else {
   Grid gridToGuess = null;
   
   //There is only one grid hit so far, guess any of the up|down|left|right grid of the lastHitGrid
   if (peerGrids.hits == 1) {
    
    //This is the first grid hit of the ship, go through the directions(up->right->down->left) to get the next ship grid.
    
    //guess the upper grid
    if (lastHitGrid.row > 0) {
     if (peerGrids.maps[lastHitGrid.row-1][lastHitGrid.col].getStatus() == Grid.UNATTACK) {
      gridToGuess = peerGrids.maps[lastHitGrid.row-1][lastHitGrid.col];
      lastDirection = 1;
     }
    }
    
    //no available, guess the right
    if (gridToGuess == null) {
     if (lastHitGrid.col < grids.NumCols-1) {
      if (peerGrids.maps[lastHitGrid.row][lastHitGrid.col+1].getStatus() == Grid.UNATTACK) {
       gridToGuess = peerGrids.maps[lastHitGrid.row][lastHitGrid.col+1];
       lastDirection = 0;
      }
     }
    }
    
    //no available, guess the down
    if (gridToGuess == null) {
     if (lastHitGrid.row < grids.NumRows-1) {
      if (peerGrids.maps[lastHitGrid.row+1][lastHitGrid.col].getStatus() == Grid.UNATTACK) {
       gridToGuess = peerGrids.maps[lastHitGrid.row+1][lastHitGrid.col];
       lastDirection = 1;
      }
     }
    }
    
    //no available, guess the left
    if (gridToGuess == null) {
     if (lastHitGrid.col > 0) {
      if (peerGrids.maps[lastHitGrid.row][lastHitGrid.col-1].getStatus() == Grid.UNATTACK) {
       gridToGuess = peerGrids.maps[lastHitGrid.row][lastHitGrid.col-1];
       lastDirection = 0;
      }
     }
    }
   }
   else
   {//more than 1 grids are hit 
    //looking for the other adjacent grid who is already hit. 
    //lastDirection should work
    if (lastDirection == 0) {
     //looking for the next grid in horizontal direction
     
     //right first
     int col = lastHitGrid.col+1;
     
     while (col < grids.NumCols) {    
      if (peerGrids.maps[lastHitGrid.row][col].getStatus() == Grid.MISSED) 
       //don't need to go on the right direction;
       break;
     
      if (peerGrids.maps[lastHitGrid.row][col].getStatus() == Grid.UNATTACK)
      {
       gridToGuess = peerGrids.maps[lastHitGrid.row][col];
       break;
      }
      col++;
     }
    
     //ready to go to left
     if (gridToGuess == null) {
      col = lastHitGrid.col-1;
      
      while (col >= 0) {     
       if (peerGrids.maps[lastHitGrid.row][col].getStatus() == Grid.MISSED) 
        //don't need to go on the left direction;
        break;
      
       if (peerGrids.maps[lastHitGrid.row][col].getStatus() == Grid.UNATTACK)
       {
        gridToGuess = peerGrids.maps[lastHitGrid.row][col];
        break;
       }
       col--;
      }
     }
    }
    else {
     //looking for the next grid in vertical direction
     
     //up first
     int row = lastHitGrid.row-1;
     
     while (row >= 0) {    
      if (peerGrids.maps[row][lastHitGrid.col].getStatus() == Grid.MISSED) 
       //don't need to go on the up direction;
       break;
     
      if (peerGrids.maps[row][lastHitGrid.col].getStatus() == Grid.UNATTACK)
      {
       gridToGuess = peerGrids.maps[row][lastHitGrid.col];
       break;
      }
      row--;
     }
     
     //ready to go to down
     if (gridToGuess == null) {
      row = lastHitGrid.row+1;
      
      while (row < NumRows) {     
       if (peerGrids.maps[row][lastHitGrid.col].getStatus() == Grid.MISSED) 
        //don't need to go on the down direction;
        break;
      
       if (peerGrids.maps[row][lastHitGrid.col].getStatus() == Grid.UNATTACK)
       {
        gridToGuess = peerGrids.maps[row][lastHitGrid.col];
        break;
       }
       row++;
      }
     }
    }
   }
   
   if (gridToGuess == null)
   {
    System.out.println("Something strange happens!");
    return;
   }
   
   System.out.println("The guessed coorinate: row-"+gridToGuess.row+", col-"+gridToGuess+", please tell Hit or Miss; Hit:0 Miss:1");
   int result = reader.nextInt();
   if (result == 0)
   {
    gridToGuess.setStatus(Grid.HIT);
    
    lastHitGrid = gridToGuess;
    peerGrids.hits++;
    
    System.out.println("Please tell if the ship is sunk: 1 - sunk");
    result = reader.nextInt();
    if (result == 1)
    {
     System.out.println("I am so great!");
     lastHitGrid = null;
     
     peerGrids.hits = 0;
    }
   }
   else
   {
    gridToGuess.setStatus(Grid.MISSED);
   }
  }
 }
 
 

 boolean hitOrMiss(int row, int col)
 {
  for(int i =0;i<NumShips;i++)
  {
   if(ships[i].isSunk)
    continue;
   for(int j=0;j<ships[i].getLength();j++)
   {
    
    if (ships[i].loc_grids[j].getStatus() == Grid.HIT)
     //The grid is already hit, skip
     continue;
    
    if(ships[i].loc_grids[j].checkHitOrMiss(row,col))
    {
     System.out.println("Hit: " + ships[i].getName() + " at row-"+row+",col-"+col);
     ships[i].hitNum++;
     if(ships[i].hitNum == ships[i].getLength())
     {
      ships[i].isSunk = true;  
      System.out.println(ships[i].getName() + " is sunk!");
     }
     return true;
    }
   }
  }
  System.out.println("Miss at Row"+row+" Col:"+col);
  return false;
 }
 
 int greeting()
 {
  System.out.println("Welcoming the BattleShip Game");
  System.out.println("Please input your Name:");
  String name = reader.next();
  System.out.println("Please input your promopt 0:you first 1:computer first:");
  int greet_result = reader.nextInt();
  return greet_result;  
 }
 
 String toName(int i)
 {
  switch(i)
  {
   case 0: return "ship0";
   case 1: return "ship1";
   case 2: return "ship2";
   case 3: return "ship3";
   case 4: return "ship4";
   default: return "ship";
  }
 }
 
 /*void initialUserGrids()
 {
  userGrids = new Grid[NumRows][NumCols];

  for(int i=0;i<NumRows;i++)
  {
   for(int j= 0;j<NumCols;j++)
   {
    Grid tmp_grid = new Grid();
    tmp_grid.setPos(i,j);
    userGrids[i][j] = tmp_grid;
   }
  }  
 }*/
}
