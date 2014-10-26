/**
  * HorizontalWall class
  * This is the definition for a wall (in x-direction).
  * 
  * @author  Mike Ryu
  * @version 1.0
  */

/* import statements */

//V: I don't see any particular reason we cant combine horizontalwall and vertical wall into just like a... rectangularwall or something

import java.awt.Point;

public class HorizontalWall extends Component {

   //////////////////
   // constructors //
   //////////////////
   
   public HorizontalWall(int size) {
      super(new Point(size, 1));
   }

   public HorizontalWall(int size, Point position) {
      super(new Point(size, 1), position);
   }

   public HorizontalWall(int size, Point position, String name) {
      super(new Point(size, 1), position, name);
   }

   public HorizontalWall(int size, int xPos, int yPos) {
      super(size, 1, xPos, yPos);
   }

   public HorizontalWall(int size, int xPos, int yPos, String name) {
      super(size, 1, xPos, yPos, name);
   }
}