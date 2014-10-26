/**
  * VerticalWall class
  * This is the definition for a wall (in y-direction).
  * 
  * @author  Mike Ryu
  * @version 1.0
  */

/* import statements */

import java.awt.Point;

public class VerticalWall extends Component {

   //////////////////
   // constructors //
   //////////////////
   
   public VerticalWall(int size) {
      super(new Point(1, size));
   }

   public VerticalWall(int size, Point position) {
      super(new Point(1, size), position);
   }

   public VerticalWall(int size, Point position, String name) {
      super(new Point(1, size), position, name);
   }

   public VerticalWall(int size, int xPos, int yPos) {
      super(1, size, xPos, yPos);
   }

   public VerticalWall(int size, int xPos, int yPos, String name) {
      super(1, size, xPos, yPos, name);
   }
}