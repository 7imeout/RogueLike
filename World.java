/**
  * World class
  * This is a text-based representtion of a retangular map.
  * 
  * @author  Mike Ryu
  * @version 1.0
  */

//V: talk to me about comments and the MSGs
//V: file getting somewhat large, i suggest separating/injecting the view and possibly the ai away

/* import statements */

//V: I dont like having dependencies on AWT in a non-windowed application, especially one so small
import java.awt.Point;
import java.util.Random;

public class World {

   /* class constants */
   private static final int MAX_X = 80;
   private static final int MAX_Y = 24;
   private static final Point CUROSR_INIT_POINT = new Point(1, 1);
   private static final boolean DEFAULT_DEBUG_STATE = false;

   private static final char WALL = '#';
   private static final char GROUND = '.';
   private static final char CURSOR = '@';
   private static final char GOBLIN = 'g';

   private static final String SIZE_WARNING = "Max. world size is 80 X 24.";
   private static final String LOC_WARNING = "Invalid cursor location.";

   private static final int BREAK_ODDS = 5;
   private static final int GOBLIN_ODDS = 66;
   private static final int NUM_DIRECTONS = 4;


   /* private instance variables */
   private int x = 0;
   private int y = 0;
   private Point cursorLoc = CUROSR_INIT_POINT;
   private char[][] mapData = null;
   private String mapView = ""; //V: BAD! unnecessary state! storing this when you can very easily keep it as local variables.
   private boolean isDebugMode = DEFAULT_DEBUG_STATE;
   private Random prg = new Random(System.currentTimeMillis()); //V: Dont seed with time


   ///////////////////
   // constructors  //
   ///////////////////

   //V: kill most these constructors

   /**
    * Default constructor; creates world of size 80x24 with cursor at (1,1).
    * @return default world object
    */
   public World() {
      setWorldSize(MAX_X, MAX_Y);
      cursorLoc = CUROSR_INIT_POINT;
   }

   /**
    * Constructor with specific width and height.
    * @param  width  width of the new world object (x).
    * @param  height height of the new world object (y).
    * @return        new world object as specified.
    */
   public World(int width, int height) {
      setWorldSize(width, height);
      cursorLoc = CUROSR_INIT_POINT;
   }

   /**
    * Constructor with speficit dimensions and cursor location.
    * @param  width  width of the new world object (x).
    * @param  height height of the new world object (y).
    * @param  initX  initial x coordinate for the cursor.
    * @param  initY  initial y coordinate for the cursor.
    * @return        new world objct as specified.
    */
   public World(int width, int height, int initX, int initY) {
      setWorldSize(width, height);
      setCursorLoc(initX, initY);
   }

   /**
    * Constructor with specific dimensions (in two separate ints)
    * and initial cursor location (java.awt.Point object).
    * @param  width  width of the new world object (x).
    * @param  height height of the new world object (y).
    * @param  initCursorLoc initial coordinate of the cursor.
    * @return               new world object as specified
    */
   public World(int width, int height, Point initCursorLoc) {
      setWorldSize(width, height);
      setCursorLoc(initCursorLoc);
   }

   /////////////////////
   // public methods  //
   /////////////////////

   /**
    * Enables debugging printing when called with true and vice versa.
    * @param state True to enable dubugging prints, false to disable.
    */
   public void setDebugState(boolean state) {
      isDebugMode = state;
   }

   /**
    * Returns java.awt.Point object for the dimensions for the world.
    * @return java.awt.Point object with x: width and y:height of the world.
    */
   public Point getWorldSize() {
      return new Point(x, y);
   }

   /**
    * Sets the world size to given value if values are within bounds.
    * @param  width  desired (new) width for the world.
    * @param  height desired (new) height for the world.
    * @return        warning string if out of bounds, empty string otherwise.
    */
   public String setWorldSize(int width, int height) {
      if (width > MAX_X || height > MAX_Y) {
         return print(SIZE_WARNING);
      }
      else {
         x = width;
         y = height;
         return "";
      }
   }

   /**
    * Returns current cursor location in java.awt.Point object.
    * @return java.awt.Point object for the current cursor location.
    */
   public Point getCursorLoc() {
      return cursorLoc;
   }

   /**
    * Sets cursor location to the as specified by the given java.awt.Point 
    * object, if values arewithin bounds and on the ground; note that this
    * DOES NOT update the rendering of the map - use redrawWorld and pass
    * in the new location for the cursor in order to update the map as well.
    * @param  location java.awt.Point object for new cursor location.
    * @return          waring string if valid input, empty string otherwise.
    */
   public String setCursorLoc(Point location) {
      if (location.x >= x && location.y >= y) {
         // case: out of bounds
         return print(LOC_WARNING);
      }
      else if (mapData != null && mapData[location.x][location.y] == WALL) {
         // case: cursor on the wall
         return print(LOC_WARNING);
      }
      else if (mapData != null) {
         // case: regular case (map initialized, in bounds, on the ground)
         mapData[cursorLoc.x][cursorLoc.y] = GROUND;
         if (mapData[location.x][location.y] == GOBLIN)
            // subcase: cursor steps on a goblin
            relocateGoblin(location);
         cursorLoc.x = location.x;
         cursorLoc.y = location.y;
         mapData[cursorLoc.x][cursorLoc.y] = CURSOR;
         return "";
      }
      else {
         // case: map not initialized yet
         letThereBeLight();
         return setCursorLoc(location);
      }
   }

   //V: id prefer asserts or exceptions to returning the warning, warnings are ignorable, which is dangerous
   /** 
    * Overloaded method for separate value input rather than awt.java.Point.
    * @param  x desired x coordinate for the cursor.
    * @param  y desired y coordinate for the cursor.
    * @return   warning string if valid input, empty string otherwise.
    */
   public String setCursorLoc(int x, int y) {
      return setCursorLoc(new Point(x, y));
   }

   //V: lol nice name, memorable, but not very descriptive
   /**
    * Initializes the world; the data and the rendering of it.
    * @return rendering of the world(map).
    */
   public String letThereBeLight() {
      print("World  : " + x + "x" + y + "\n" +
            "Cursor : (" + cursorLoc.x + ", " + cursorLoc.y + ")");

      mapData = new char[x][y];
      for (int j = 0; j < y;j++) {
         for (int i = 0; i < x; i++) {
            // print("[" + i + ", " + j + "] " + x + "x" + y);
            if (i == 0 || j == 0 || i == x-1 || j == y-1)
               mapData[i][j] = WALL;
            else if (i == cursorLoc.x && j == cursorLoc.y)
               mapData[i][j] = CURSOR;
            else
               mapData[i][j] = GROUND;
            mapView += mapData[i][j];
         }
         mapView += "\n";
      }

      return mapView;
   }

   //V: extremely misleading function, modifies member variables when it gives no indication of doing so
   //in either the docs or the code (i thought mapView was a local, but it turns out youre changing members)
   //V: also, take out the ability to set the new point with this function, i cant see any reason why they
   //should be combined
   /**
    * Re-draws the world(map) to reflect the up-to-date information;
    * use this to update cursor movement or pass null for simple re-draw.
    * @param  newCursorLoc java.awt.Point object for the new cursor location.
    * @return              updated rendering of the world(map).
    */
   public String redrawWorld(Point newCursorLoc) {
      if (newCursorLoc != null)
         setCursorLoc(newCursorLoc);
         
      for (int j = 0; j < y;j++) {
         for (int i = 0; i < x; i++) {
            mapView += mapData[i][j];
         }
         mapView += "\n";
      }

      return mapView;
   }

   /**
    * Adds a given component object to the world.
    * @param  item        component object to add to the world.
    * @param  randomHoles true to put random holes in the components walls.
    * @return             updated rendering of the world(map).
    */
   public String addComponent(Component item, boolean randomHoles) {
      int currentX, currentY, randomHolesTrigger;
      boolean boundsExceeded = false;

      if (randomHoles)
         randomHolesTrigger = 0;
      else
         randomHolesTrigger = 1;

      if (mapData != null) {
         // case: regular (map already initialized)
         for (int j = 0; j < item.dim.y; j++) {
            for (int i = 0; i < item.dim.x; i++) {
               currentX = i + item.pos.x;
               currentY = j + item.pos.y;

               if (currentX >= x || currentY >= y) {
                  boundsExceeded = true;
                  break;
               }

               if (i == 0 || j == 0 || 
                   i == item.dim.x-1 || j == item.dim.y-1) {
                  if ((mapData[currentX][currentY] != CURSOR) &&
                      ((prg.nextInt(BREAK_ODDS) + randomHolesTrigger) != 0))
                     mapData[currentX][currentY] = WALL;
               }
            }
            if (boundsExceeded)
               break;
         }
      }
      else {
         // case: map not initialized yet
         letThereBeLight();
         addComponent(item, randomHoles);
      }
      //V: why is this here
      return redrawWorld(null);
   }

   /**
    * Plant goblins (g) at random locations on a map.
    * @return world(map) rendering now with goblins planted.
    */
   public String plantGoblins() {
      StringBuilder goblinPlanter = new StringBuilder(mapView);
         
      for (int j = 0; j < y;j++) {
         for (int i = 0; i < x; i++) {
            if ((mapData[i][j] == GROUND) && (prg.nextInt(GOBLIN_ODDS) == 0))
               mapData[i][j] = GOBLIN;
            mapView += mapData[i][j];
         }
         mapView += "\n";
      }

      return mapView;
   }

   ////////////////////////////
   // private helper methods //
   ////////////////////////////

   /**
    * When debug state is true, prints the message passed in to console.
    * @param  msg dubugging message to print.
    * @return     debugging message passed in.
    */
   private String print(String msg) {
      if (isDebugMode)
         System.out.println(msg);
      return msg;
   }

   /**
    * Randomly relocates a goblin to an adjacent coordinate.
    * @param location goblin's current location.
    */
   private void relocateGoblin(Point location) {
      boolean relocateSuccessful = false;
      char currentOccupant;
      while (!relocateSuccessful) {
         switch (prg.nextInt(NUM_DIRECTONS)) {
            case 0:
               currentOccupant = mapData[location.x][location.y+1];
               if (currentOccupant != WALL && currentOccupant != GOBLIN) {
                  mapData[location.x][location.y+1] = GOBLIN;
                  relocateSuccessful = true;
               }
               break;
            case 1:
               currentOccupant = mapData[location.x][location.y-1];
               if (currentOccupant != WALL && currentOccupant != GOBLIN) {
                  mapData[location.x][location.y-1] = GOBLIN;
                  relocateSuccessful = true;
               }
               break;
            case 2:
               currentOccupant = mapData[location.x+1][location.y];
               if (currentOccupant != WALL && currentOccupant != GOBLIN) {
                  mapData[location.x+1][location.y] = GOBLIN;
                  relocateSuccessful = true;
               }
               break;
            case 3:
               currentOccupant = mapData[location.x-1][location.y];
               if (currentOccupant != WALL && currentOccupant != GOBLIN) {
                  mapData[location.x-1][location.y] = GOBLIN;
                  relocateSuccessful = true;
               }
               break;
            default:
               print("Invalid PRN for Goblin relocation.");
               break;
         }
      }
   }
}