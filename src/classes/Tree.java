package classes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Tree {
  
  
  public static class Enhancement {
    public String name;
    public String description;
    
    public Image icon;
    
    public List<Attribute> attributes;
    
    public Enhancement() {
      attributes = new ArrayList<Attribute>();
      name = "";
      description = "";
      icon = null;
    }
    
  }
}
