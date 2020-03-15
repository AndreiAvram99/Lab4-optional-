package gradleproject1;
import com.github.javafaker.Faker;
import java.util.stream.IntStream;

/**
 * Clasa Main reprezinta clasa pricipala a proiectului,
 * aici sunt create instantele claselor Resident si Hospital, iar pentru 
 * rezolvarea problemei clasa problem.
 * 
 * Pentru instantieri am folosit IntStrem cu rangeClose pentru a crea un stream 
 * de numere intregi peste intervalul acela(inchis)
 * 
 * Cu mapToObj convertim elementele stream-ului la obj de tip Resident/Hospital
 * si cream un nou stream cu ele 
 * 
 * .toArray este folosit pentru a crea un array cu acele obiecte.
 * 
 * Pentru generarea de nume random am folosit com.github.javafaker.Faker, third-party library
 * 
 * Dupa este creata o instanta a clasei Problem ce apeleaza metodele 
 * .setHopitalsList pentru a trimite spitalele create problemei
 * .setResidentsList pentru a trimite rezidentii creati problemei
 * .solveProblem afiseaza un match intre rezidenti si spitale
 * .checkSolution verifica daca match-ul este stable
 * 
 * @author avram
 */

public class Main {

    public static void main(String[] args) {
        Faker faker = new Faker();
       
        Resident[] residents = IntStream.rangeClosed(0, 5)
                                 .mapToObj(i -> new Resident(faker.name().fullName() /*"R" + i*/))
                                 .toArray(Resident[]::new);
        
        
        Hospital[] hospitals = IntStream.rangeClosed(0, 3)
                                 .mapToObj(i -> new Hospital(faker.company().name() /*"H" + i*/,(int)((Math.random() * 4 + 1))))
                                 .toArray(Hospital[]::new);
        
        Problem problemInstance = new Problem();
        
        problemInstance.setHopitalsList(hospitals);
        problemInstance.setResidentsList(residents);
       
        problemInstance.solveProblem();
        
        System.out.println();
        if(problemInstance.checkSolution())
            System.out.println("Stable");
        else System.out.println("Not stable");
    }
        
}
