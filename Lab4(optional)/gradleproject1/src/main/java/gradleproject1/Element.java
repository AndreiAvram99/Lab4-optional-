package gradleproject1;

import java.util.Objects;

/**
 * Clasa Element, este o superclasa claselor Resident si Hospital
 * 
 * Contine:
    * atributul name comun pentru spital si rezident
    * 
    * metoodele:
        * Setter si getter pentru name
        * Suprascrie metodele hashCode, equals si compareTo (din interfata Comparable),
        * ultima pentru ca in problem creez un TreeSet cu rezidentii&spitalele preferati de spitale&rezidenti si vreau sa stiu sa le compar
        * pentru a putea TreeSet-ul sa le tina sortate si equals pentru a asigura unicitatea
 * 
 * @author avram
 */

public class Element implements Comparable<Element>{
    
    private String name;
    
    Element(){}
    
    Element(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
     @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Element OTHER = (Element) obj;
        return Objects.equals(this.name, OTHER.name);
    }
    
    @Override
    public int compareTo(Element otherElement) {
        return this.name.compareTo(otherElement.name);
    }
}
