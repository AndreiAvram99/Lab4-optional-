package gradleproject1;

import java.util.*;

/**
 * Clasa Partition este folosita pentru gestionarea preferintelor
 * 
 * Contine:
    * Doua map-uri, unul pentru rezident si preferintele sale si unul pentru spital si preferintele sale
    
    * Metoodele: 
        * Doua setter-re pentru crearea map-urilor
        * Doua getter-re pentru a returna o lista de preferinte ale rezidentului specificat, respectiv ale spitalului
        * La final o metoda ce afiseaza rezidentii si preferintele, respectiv spitalele si preferintele
 * @author avram
 */

public class Partition {
    
    Partition(){}
    
    private Map<Resident, List<Hospital>> residentPreferenceMap = new HashMap<>();
    
    private Map<Hospital, List<Resident>> hospitalPreferenceMap = new TreeMap<>();
    
    public void setResidentPreferenceMap(Resident resident, List<Hospital> hospitals){
        residentPreferenceMap.put(resident, hospitals);
    }
    
    public void setHospitalPreferenceMap(Hospital hospital, List<Resident> residents){
        hospitalPreferenceMap.put(hospital, residents);
    }

    public List<Hospital> getResidentPreference(Resident resident) {
        return residentPreferenceMap.get(resident);
    }

    public List<Resident> getHospitalPreference(Hospital hospital) {
        return hospitalPreferenceMap.get(hospital);
    }
    
    public void displayPartition(){
       
         System.out.println();
        
        residentPreferenceMap.entrySet().forEach((entry) -> {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue()); 
        });
        
        System.out.println();
        
        hospitalPreferenceMap.entrySet().forEach((entry) -> {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        });
    }
}
