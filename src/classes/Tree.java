package classes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Tree {
  
  public int width;
  public int height;
  
  public List<Enhancement> Enhancements;
  
  //Returns a borderpane with a sort system
  
  public static class Enhancement {
    public String name;
    public String description;
    public URL icon;
    
    //x pos, y pos, or core position
    public int x;
    public int y;
    public int c;
    
    //Prerequisites
    public int reqPoints;
    public List<Enhancement> reqEnhancements;
    //public List<Requirement> preReqs;
    
    public List<Attribute> attributes;
    
    public Enhancement() {
      attributes = new ArrayList<Attribute>();
      name = "";
      description = "";
      icon = null;
      reqPoints = 0;
      reqEnhancements = new ArrayList<Enhancement>();
      //preReqs = new ArrayList<Requirement>();
    }
    
  }
}
