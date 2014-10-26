/**
  * Component class
  * This is the definition for a general component that is in thr world.
  * 
  * @author  Mike Ryu
  * @version 1.0
  */

/* import statements */

import java.awt.*;

//V: very ambiguous name, "component" is as bad a name as "object" or "entity" or "manager"
//V: consider immutability for these components
public class Component {

   /* class constants */
   private static final Point DEFAULT_POSITION = new Point(1, 1);

   /* public fields of a component */
   public Point dim = null;
   public Point pos = null;
   public String name = "noname";


   /////////////////
   // constuctors //
   /////////////////
   
   //V: Get rid of most of these constructors
   public Component(Point dimension) {
      this.dim = dimension;
      this.pos = DEFAULT_POSITION;
   }

   public Component(Point dimension, Point position) {
      this.dim = dimension;
      this.pos = position;
   }

   public Component(int xDim, int yDim, int xPos, int yPos) {
      this.dim = new Point(xDim, yDim);
      this.pos = new Point(xPos, yPos);
   }

   public Component(Point dimension, Point position, String name) {
      this.dim = dimension;
      this.pos = position;
      this.name = name;
   }

   public Component(int xDim, int yDim, int xPos, int yPos, String name) {
      this.dim = new Point(xDim, yDim);
      this.pos = new Point(xPos, yPos);
      this.name = name;
   }


   ////////////////////
   // public methods //
   ////////////////////

   public void move(Point delta) {
      pos = new Point(pos.x + delta.x, pos.y + delta.y);
   }

   public void scale(int scale) {
      dim = new Point(dim.x + scale, dim.y + scale);
   }
}