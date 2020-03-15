package gradleproject1;

import java.util.*;

/**
 * Clasa Problem, rezolva problema de match-ing
 * 
 * Contine:
    * Doua liste, una pentru residenti si una pentru spitale
    * Doua obiecte: 
        * unul de tip Partition pentru a crea partitiile pentru residenti si spitale cu preferintele acestora
        * unul de tip Matching pentru a se ocupa de matching
    *
    * Metoodele: 
        * Explicatiile se gasesc deasupra fiecarei metode importante
 * @author avram
 */

public class Problem {
    
    private List<Resident> residentsList= new ArrayList<>();
    private List<Hospital> hospitalsList = new ArrayList<>();
    
    Partition partition = new Partition();
    Matching match = new Matching();

    Problem(){}
    
    public List<Resident> getResidentsList() {
        return residentsList;
    }

    
    public void setResidentsList(Resident[] residents) {
        this.residentsList.addAll(Arrays.asList(residents));
        Collections.sort(residentsList);
    }

    public List<Hospital> getHopitalsList() {
        return hospitalsList;
    }

    public void setHopitalsList(Hospital[] hospitals) {
        hospitalsList.addAll(Arrays.asList(hospitals));
        Collections.sort(hospitalsList);
    }
    
    //Afisez toti rezidentii folosind o lambda exprsie ce parcurge toti residentii si afiseaza numele lor
    public void displayResidents(){
        residentsList.forEach((_item) -> {
            System.out.println(_item.toString());
        });
    }  
    
    //Afisez toti rezidentii folosind o lambda exprsie ce parcurge toate spitalele si afiseaza numele lor
    public void displayHospitals(){
        hospitalsList.forEach((_item) -> {
            System.out.println(_item.toString());
        });
    }
    
    
    /**
     * Variabilele numberOfResidentPreferences si numberOfHopistalPreferences 
     * reprezinta doua numere intregi generate random ce nu pot depasi numarul total de spitale, respectiv rezidenti
     * Math.random() genereaza un double din [0,1) iar inmultind-ul cu numberOfHospitals/numberOfResidents si apoi fiind castate la int  
     * respecta specificatia facut mai sus
     * 
     * randomIndex este folosit pentru a alege un spital/rezident random din listele specifice
     * 
     * Sunt doua for-uri principale:
    
        * Primul parcurge rezidentii iar in interiorul lui:
            * se genereaza un numar de preferinte pentru studentul respectiv
            * daca cumva s-au generat 0 preferinte se assign-eaza default doua
            * se alege un spital si se adauga intr-un TreeSet de spitale pana se consuma preferintele
            * am ales un TreeSet pentru a asigura unicitatea preferintei
            * cum partition are doua map-uri de tipul <Resident, List<Hospital>> si <Hospital, List<Resident>>
            * pun set-urile intr-o lista si trimit rezidentul si lista de preferinte
        
        * Al doilea for face acelasi lucru ca primul doar ca pentru spitale
    
     * La final afisez partitiile cu preferinte si creez un match cu partitia, lista de rezidenti si lista de spitale  
     */
    public void solveProblem(){
    
        int numberOfResidentPreferences;
        int numberOfHopistalPreferences;
        int numberOfResidents = residentsList.size();
        int numberOfHospitals = hospitalsList.size();
        int randomIndex;
        
        for(Resident resident:residentsList){
            
            numberOfResidentPreferences = (int)(Math.random() * numberOfHospitals);
                
            TreeSet<Hospital> residentPreferences = new TreeSet<>();
            List<Hospital> residentPreferencesList = new ArrayList<>();
            
            if(numberOfResidentPreferences == 0)
                numberOfResidentPreferences = 2;
            
            for(int index = 0; index <= numberOfResidentPreferences; index++){
                
                randomIndex = (int)(Math.random()*numberOfHospitals);
                residentPreferences.add(hospitalsList.get(randomIndex));
            }
            residentPreferencesList.addAll(residentPreferences);
            partition.setResidentPreferenceMap(resident, residentPreferencesList);
        }
        
        for(Hospital hospital:hospitalsList){
            
            numberOfHopistalPreferences = (int)(Math.random() * numberOfResidents);
            TreeSet<Resident> hospitalPreferences = new TreeSet<>();
            List<Resident> hospitalPreferencesList = new ArrayList<>();
            
            if(numberOfHopistalPreferences == 0)
                numberOfHopistalPreferences = 1;
            
            for(int index = 0; index < numberOfHopistalPreferences; index++){
                randomIndex = (int)(Math.random()*numberOfResidents);
                hospitalPreferences.add(residentsList.get(randomIndex));
            }
            hospitalPreferencesList.addAll(hospitalPreferences);
            partition.setHospitalPreferenceMap(hospital, hospitalPreferencesList);
        }
        
        partition.displayPartition();
        
        match.createMatch(partition, hospitalsList, residentsList);
        
    }  

    
    /**
     * Returneaza daca match-ul facut in Match este stable 
     * @return 
     */
    public boolean checkSolution(){
        return match.verifyMatch(partition, hospitalsList, residentsList);
    }
}
