package gradleproject1;

import java.util.*;

/**
 * Clasa Matching este pentru cuplarea fiecarui rezident cu un spital
 * 
 * Contine:
    * Un map de forma <Resident, Hospital> , fiecarui resident ii este asignat un spital
    
    * Metoodele: 
        * Explicatiile se gasesc deasupra fiecarei metode importante 
        
 * @author avram
 */

public class Matching {    
    
    Matching(){};
    
    private Map<Resident, Hospital> residentHospitalMatch = new HashMap<>(); 
    
    // Returneaza numarul total de locuri disponibile
    private int getTotalCapacity(List<Hospital> hospitalsList){
        int totalCapacity = 0;
        for(Hospital hospital:hospitalsList)
        {
            totalCapacity += hospital.getCapacity();
        }
        return totalCapacity;
    }
    
    /**
     * Prima data verific daca numarul total de rezidenti nu este mai mare decat
     * numarul total de spatii disponibile si arunc exceptie daca este
     * 
     * Folosesc un algoritm greedy in care iau preferintele residentului in ordine
     * si pentru fiecare preferinta verific daca rezidentul se afla in lista spitalului 
     * daca il gasesc fac match cu spitalul respectiv asta o sa presupuna ca daca el
     * era preferat mai tare de un spital match-ul tot ramane stable deoarece el nu prefera 
     * mai tare acel spital decat cel cu care a facut match in concluzie nu indeplineste ambele
     * conditii pentru a nu fi stable. Dupa ce ii asignez scad  capacitatea spitalului si totodata 
     * inainte sa ii asignez verific daca spitalul mai are locuri libere
     * 
     * E posibil sa ramana residenti neasignati, deoarece e posibil sa placa niste spitale dar
     * acestea sa nu il placa, de acee la final parcurc residentii care nu au facut match, 
     * imi dau seama care sunt pentru ca au un atribut care spune care nu sunt partitionati,
     * dupa ce ii parcurg ii asignez la primul spital ce mai are capacitate ceea ce respecta din nou
     * proprietatea de stable deoarece nu exista un spital mai preferat care sa il placa
     * 
     * Singura varianta pentru care nu e stable matching-ul e daca a fost repartionat un rezident la 
     * un spital care era printre ultimele prefirinte si el se regaseste printre ultimele preferinte si
     * ramane spitalul cu capacitate 0 ceea ce presupune ca daca se gaseste dupa un resident ce 
     * prefera acel spital mai mult si este si el mai preferat de  spital nu o sa poata fi asignat, 
     * dar din ce am inteles din enunt trebuia doar sa facem un match si sa verificam daca e stable
     * 
     * Descrierea codului:
        * - prima data parcurg residentii
        * - ma folosesc de partition pentru a determina lista cu preferintele residentului curent
        * - parcurg preferintele, iau capacitatea si prefeintele spitalului
        * - apoi iau toti residentii din cei preferati de spitalul curent si verific daca cooincide 
        * cu residentul de la care am plecat si daca capacitateaspitalului nu este 0;
        * - dupa ce fac prima asignare ii parcurg pe cei ramasi si ii pun la primul spital ce mai are locuri
        * - la final afisez elementele din map 
     * 
     * @param partition
     * @param hospitalsList
     * @param residentsList 
     */
    public void createMatch(Partition partition, List<Hospital> hospitalsList, List<Resident> residentsList){
        
        int totalCapacity = getTotalCapacity(hospitalsList);
        int hospitalCapacity;
        
        int numberOfResidents = residentsList.size();
        
        List<Hospital> residentPreferences = new ArrayList<>();
        List<Resident> hospitalPreferences = new ArrayList<>();
        
        if(totalCapacity < numberOfResidents){
            throw new ArithmeticException("Not enough space"); 
        }
        
        else{
            
            for(Resident resident:residentsList){
                residentPreferences = partition.getResidentPreference(resident);
                for(Hospital hospital:residentPreferences){
                   hospitalCapacity = hospital.getCapacity();
                   hospitalPreferences = partition.getHospitalPreference(hospital);
                   for(Resident preferedResident:hospitalPreferences){
                       if(preferedResident.getName().equals(resident.getName()) && hospitalCapacity != 0 && !resident.isMatch()){
                           residentHospitalMatch.put(resident, hospital);
                           hospital.setCapacity(hospitalCapacity - 1);
                           resident.setMatch(true);
                       }
                   }
                }
            }
            
            for(Resident resident:residentsList){
                if(!resident.isMatch())
                {
                    for(Hospital hospital:hospitalsList){
                        if(hospital.getCapacity() != 0){
                            residentHospitalMatch.put(resident, hospital);
                            hospital.setCapacity(hospital.getCapacity() - 1);
                            break;
                        }
                    }
                }
            }
            
            System.out.println();
            residentHospitalMatch.entrySet().forEach((entry) -> {
                System.out.println("Resident = " + entry.getKey().getName() +
                        " ---> Hospital = " + entry.getValue().getName() + "\t | Remain capacity:" + entry.getValue().getCapacity()); 
            });
            
        }
        
    }
    
    
    /**
     * Pentru verificare, parcurg rezidentii apoi spitalele din preferintele sale
     * iau spitalul care ia fost asignat residentului, daca ajunge sa coincida cu unul din preferinte
     * pot trece la urmatorul rezident, daca nu coincide parcurg in acelasi timp preferintele spitalului asignat 
     * si cele ale spitalului din preferinte gasit inaintea celui asignat (adica mai preferat decat cel asignat, deoarece 
     * am zis ca daca ajung la cel asignat nu are rost sa mai continui deoarece urmatoarele spitale nu sunt mai preferate)
     * parcurgand in acelasi timp preferintele celor doua  spitale, daca il gasesc inainte in cel neasignat inseamna ca
     * matching-ul nu este stable altfel pot sa continui.
     * 
     * Daca ajung la final inseama ca matching-ul este stable
     * 
     * @param partition
     * @param hospitalsList
     * @param residentsList
     * @return 
     */
    
    public boolean verifyMatch(Partition partition, List<Hospital> hospitalsList, List<Resident> residentsList){
        
        Hospital assignedHospital = new Hospital();
        List<Resident> assignedHospitalPreferences = new ArrayList<>();
        List<Resident> currentHospitalPreferences = new ArrayList<>();
        List<Hospital> currentResidentPreferences = new ArrayList<>();
        
        for(Resident resident:residentsList){
            currentResidentPreferences = partition.getResidentPreference(resident);
            for(Hospital hospital:currentResidentPreferences){
                assignedHospital = residentHospitalMatch.get(resident);
                if(hospital.getName().equals(assignedHospital.getName())){
                    break;
                }    
                else{            
                    currentHospitalPreferences = partition.getHospitalPreference(hospital);
                    assignedHospitalPreferences = partition.getHospitalPreference(assignedHospital);
                    
                    for(int index = 0; index < currentHospitalPreferences.size(); index++){
                        if(index > assignedHospitalPreferences.size())
                            break;
                        if(currentHospitalPreferences.get(index).getName().equals(resident.getName()) 
                            && !assignedHospitalPreferences.get(index).getName().equals(resident.getName()))
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
