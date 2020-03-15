package gradleproject1;

import java.util.Objects;

/**
 * Clasa Resident, clasa specifica rezidentilor sublcasa a clasei Element
 * 
 * Contine:
    * Atributul match
    * match este folosit pentru a verifica daca residentul a fost asignat unui spital
    * 
    * metoodele:
        * Setter si getter pentru match
        * Suprascrie metoda .toString
 * 
 * @author avram
 */

public class Resident extends Element{

    private boolean match = false;
    
    Resident(String name){
        super(name);
    }
    
    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
    
    @Override
    public String toString() {
        return "Resident{" + "fullName=" + this.getName() + '}';
    }
    
}
