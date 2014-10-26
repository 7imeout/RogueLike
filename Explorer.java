/**
  * Explorer class
  * This is the class that handles IO for the world and its components.
  * 
  * @author  Mike Ryu
  * @version 1.0
  */
 
/* import statements */

import java.awt.Point;
import java.util.*;

public class Explorer {
   
   /* class constants */
   private static final int NUM_BROKEN_WALLS = 30;
   private static final int NUM_BROKEN_ISLANDS = 10;
   private static final int NUM_LINES_TO_PRINT = 24;

   /* private instance variable */
   private static World world = new World(80, 22, 2, 2);
   private static Scanner scan = new Scanner(System.in);


   /////////////////
   // main method //
   /////////////////

   /**
    * Main prints loading screen, builds world, and inits IO.
    */
   public static void main(String[] args) {
      printManyLines();
      System.out.print("New world is now loding ...");
      buildRandomWorld();
      explore();
   }


   ////////////////////////////
   // private static methods //
   ////////////////////////////

   /**
    * Build a randomly-generated world with broken walls and islands.
    */
   private static void buildRandomWorld() {
      Random prg = new Random(System.currentTimeMillis());

      for (int i = 0; i < NUM_BROKEN_ISLANDS; i++) {
         world.addComponent(new Island(prg.nextInt(20)+2, 
                                       prg.nextInt(20)+2, 
                                       prg.nextInt(80)+2, 
                                       prg.nextInt(24)+2), true);
      }

      for (int j = 0; j < NUM_BROKEN_WALLS; j++) {
         world.addComponent(new HorizontalWall(prg.nextInt(10)+5, 
                                               prg.nextInt(80)+5,
                                               prg.nextInt(24)+2), true);
         world.addComponent(new VerticalWall(prg.nextInt(10)+5, 
                                             prg.nextInt(80)+5,
                                             prg.nextInt(24)+2), true);
      }

      world.plantGoblins();
      System.out.println(world.redrawWorld(null));
   }

   /**
    * IO Handler; lets the user move around the map.
    */
   private static void explore() {
      String input = "";
      Point loc = null;

      do {
         System.out.print("MOVE (Q to Exit): ");
         input = scan.nextLine();

         //V: theres something weird to me about setting the player's location via a redrawWorld function.

         for (int i = 0; i < input.length(); i++) {
            clearScreen();
            loc = world.getCursorLoc();
            switch (input.charAt(i)) {
               case 'a': 
                  System.out.println(world.redrawWorld(
                     new Point(loc.x-1, loc.y))); 
                  break;
               case 'd':
                  System.out.println(world.redrawWorld(
                     new Point(loc.x+1, loc.y)));
                  break;
               case 'w':
                  System.out.println(world.redrawWorld(
                     new Point(loc.x, loc.y-1)));
                  break;
               case 's':
                  System.out.println(world.redrawWorld(
                     new Point(loc.x, loc.y+1)));
                  break;
               case 'Q':
                  printManyLines();
                  System.out.println("Exploration complete.");
                  break;
               default:
                  System.out.println("Invalid move detected.");
                  break;
            }
         } 
      } while (!input.contains("Q"));
   }

   /**
    * Supposed to execute bash "clear" command; currently not working.
    */
   private final static void clearScreen() {
      try {
         Runtime.getRuntime().exec("clear");
      }
       catch (final Exception e) {
            System.out.println("Could not clear screen: " + e);
      }
   }

   /**
    * Alternative for clearScreen; just prints many lines.
    */
   private final static void printManyLines() {
         for (int i = 0; i < NUM_LINES_TO_PRINT; i++) {
            System.out.println("");
      }
   }
}
 