/**
  * Island class
  * This is the definition for a wall (in x-direction).
  * 
  * @author  Mike Ryu
  * @version 0.1
  */

/* import statements */

import java.awt.Point;

public class Island extends Component {

   //////////////////
   // constructors //
   //////////////////
   
   public Island(Point dimension) {
      super(dimension);
   }

   public Island(Point dimension, Point position) {
      super(dimension, position);
   }

   public Island(int xDim, int yDim, int xPos, int yPos) {
      super(xDim, yDim, xPos, yPos);
   }

   public Island(Point dimension, Point position, String name) {
      super(dimension, position, name);
   }

   public Island(int xDim, int yDim, int xPos, int yPos, String name) {
      super(xDim, yDim, xPos, yPos, name);
   }
}