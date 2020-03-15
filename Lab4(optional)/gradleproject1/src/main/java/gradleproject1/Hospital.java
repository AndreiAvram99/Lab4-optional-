package gradleproject1;

import java.util.Objects;

/**
 * Clasa Hospital, clasa specifica spitalelor  sublcasa a clasei Element
 * 
 * Contine:
    * Atributul capacity
    * capacity reprezinta capacitatea spitalului
    * 
    * Metodele:
        * Setter si getter pentru capacity
        * Suprascrie metoda .toString
 * 
 * @author avram
 */

public class Hospital extends Element{
    
    private int capacity;

    Hospital(){}
    
    Hospital(String name, int capacity){
        super(name); 
        this.capacity = capacity;
    }
    
    public int getCapacity(){
        return capacity;
    }
    
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Hospital{" + "name=" + this.getName() + ", capacity=" + capacity + '}';
    }
   
}
