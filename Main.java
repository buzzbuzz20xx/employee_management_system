import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class Main {

    static ArrayList <Employee> employees = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileInputStream fileInput = new FileInputStream("employees.ser");
        ObjectInputStream objectIn = new ObjectInputStream(fileInput);

        employees = (ArrayList<Employee>) objectIn.readObject();
        objectIn.close();

        showLogo();
        mainMenu();
    }

    static void showLogo(){
        System.out.println("\n-----------------------------");
        System.out.println("          Database");
        System.out.println("-----------------------------");}

    static void mainMenu(){
        System.out.println("\nEnter '1' to add new employees");
        System.out.println("Enter '2' to remove employees");
        System.out.println("Enter '3' to search for an employee");
        System.out.println("Enter '4' to view all employees");
        System.out.println("Enter '5' to edit employees data");
        System.out.println("Enter '6' to quit");

        int input;
        while(true){
            try{
                String tempInput = scanner.next();
                scanner.nextLine(); /* Consume the \n char.*/
                input = Integer.parseInt(tempInput);
                break;}
            catch(NumberFormatException e){
                System.out.println("Enter a number !");
            }
        }

        switch(input){
            case 1 -> addEmployees();
            case 2 -> removeEmployees();
            case 3 -> searchForEmployee();
            case 4 -> viewAllEmployees();
            case 5 -> editEmployees();
            //case 6 -> {try{saveEmployees();} catch(IOException e){}}
            case 6 ->  System.exit(0);
            default -> {System.out.println("Invalid"); mainMenu();}
        }
    }

    static void addEmployees(){
        System.out.print("\nEnter '0' anytime to return...");

        System.out.print("\nEnter the employee name: ");
        String name = scanner.nextLine();
        if(name.equals("0")) mainMenu();

        String ID;
        do {
        System.out.print("\nEnter the employee ID (8 numbers): ");
        ID = scanner.next();
        if(ID.equals("0")) mainMenu();
        if(! Employee.IDAvalible(ID))
            System.out.println("\nID already taken !");}
        while(ID.length() != 8 || !Employee.isAllDigit(ID) || !Employee.IDAvalible(ID));

        int age = 0;
        while(age < 16){
        System.out.print("\nEnter the employee age (Must be 16 or above): ");
        String tempAge = scanner.next();
        if(tempAge.equals("0")) mainMenu();
        try{
            age = Integer.parseInt(tempAge);}
        catch(NumberFormatException e){
            System.out.println("\nPlease enter digits only !");}
        }   
        
        int sex = -1;
        boolean isMale = true;
        do{
        System.out.print("\nenter 1 if male or 2 if female: ");
        String tempSex = scanner.next();
        if(tempSex.equals("0")) mainMenu();
        try{sex = Integer.parseInt(tempSex);}
        catch(NumberFormatException e){
            System.out.println("\nPlease enter 1 or 2 !");
        }}
        while(sex != 1 && sex != 2);
        if(sex == 1) isMale = true; else if(sex == 2) isMale = false;
        
        int salary = 0;
        do{
            System.out.print("\nEnter the employee salary (1000 or more): ");
            String tempSalary = scanner.next();
            if(tempSalary.equals("0")) mainMenu();
            try{
                salary = Integer.parseInt(tempSalary);}
            catch(NumberFormatException e){
                System.out.println("\nPlease enter a number !");
            }}
            while(salary < 1000);
        
        //Position:
        int index = -1;
        do{System.out.println("\nPlease enter the employee's position (number): ");

        for (int i = 0; i < Position.values().length; i++){
            System.out.print((i + 1) + " - ");
            System.out.println(Position.values()[i]);}
        
        String tempIndex = scanner.next();
        if(tempIndex.equals("0")) mainMenu();
        try{
            index = Integer.parseInt(tempIndex);}
        catch(NumberFormatException e){
            System.out.println("Please enter a number !");
        }}
        while(index < 1 || index > Position.values().length);
        
        
        Employee employee = new Employee(name, ID, Position.values()[index - 1], isMale, salary, age);
        employees.add(employee);
        System.out.println("Done, "+employee.name+" has been added!");
        try{saveEmployees();} catch(IOException e){}
        System.out.print("Enter anything to continue: ");
        scanner.next();
        mainMenu();

    }

    static void removeEmployees(){
        System.out.println("\nEnter '0' anytime to return...");

        if(employees.size() <= 0){
            System.out.println("No employees found !");
            mainMenu();}

        for (int i = 0; i < employees.size(); i++) 
            System.out.println((i + 1) + " - " + employees.get(i).name + " - " + employees.get(i).getID());

        int index = 0;
        do{
            System.out.print("\nWhich employee to remove ? (number): ");
            String tempIndex = scanner.next();
            if(tempIndex.equals("0")) mainMenu();
            try{
                index = Integer.parseInt(tempIndex);
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a number !");
            }}
        while(index < 1 || index > employees.size());

        Employee employeeToDelete = employees.get(index - 1);
        System.out.println("\nAre you sure you want delete this employee ?");
        System.out.println(employeeToDelete.name + " - " + employeeToDelete.getID());
        System.out.print("Enter 'Yes' to continue: ");
        String choice = scanner.next();
        if(choice.equals("Yes")){
            employees.remove(index - 1);
            System.out.println(employeeToDelete.name + " Has been deleted !");
            try{saveEmployees();} catch(IOException e){}}
        else
            System.out.println("No employees deleted.");
        System.out.print("\nEnter anything to continue: ");
        scanner.next();
        removeEmployees();
    }
    static void searchForEmployee(){
        System.out.println("Enter '0' anytime to return");
        String ID;
        do{
            System.out.print("Please enter the employee's ID (8 numbers): ");
            ID = scanner.next();
            if(ID.equals("0")) mainMenu();
        }
        while(ID.length() != 8 || !Employee.isAllDigit(ID));
        boolean found = false;
        int i = 0;
        for (  ; i < employees.size(); i++){
            String employeeID = employees.get(i).getID();
            if(employeeID.equals(ID)){
                System.out.println(employees.get(i));
                found = true;
                System.out.print("\nEnter anything to continue: ");
                scanner.next();
                searchForEmployee();
            }
        }
        if(!found){
            System.out.println("\nEmployee not found !");
            searchForEmployee();
        }   
    }

    static void viewAllEmployees(){
        System.out.println("\nEnter '0' anytime to return...");

        if(employees.size() <= 0){
            System.out.println("No employees found !");
            mainMenu();}

        for (int i = 0; i < employees.size(); i++) 
            System.out.println((i + 1) + " - " + employees.get(i).name + " - " + employees.get(i).getID());
            

        int index = 0;
        do{
            System.out.print("\nWhich employee ? (number): ");
            String tempIndex = scanner.next();
            if(tempIndex.equals("0")) mainMenu();
            try{
                index = Integer.parseInt(tempIndex);
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a number !");
            }}
        while(index < 1 || index > employees.size());

        System.out.println(employees.get(index - 1));
        System.out.println("\nEnter anything to continue...");
        scanner.next();
        viewAllEmployees();

    }

    static void editEmployees(){
        System.out.println("\nEnter '0' anytime to return...");

        if(employees.size() <= 0){
            System.out.println("No employees found !");
            mainMenu();}

        for (int i = 0; i < employees.size(); i++) 
            System.out.println((i + 1) + " - " + employees.get(i).name + " - " + employees.get(i).getID());

        int index = 0;
        do{
            System.out.print("\nWhich employee to edit? (number): ");
            String tempIndex = scanner.next();
            if(tempIndex.equals("0")) mainMenu();
            try{
                index = Integer.parseInt(tempIndex);
                }
            catch(NumberFormatException e){
                System.out.println("Please enter a number !");
                }}
        while(index < 1 || index > employees.size());

        String choice = "";
        boolean valid = false;
        while(!valid){
            System.out.println(employees.get(index - 1) + "\n\nWhat to edit ?");
            System.out.println("Enter '1' for name");
            System.out.println("Enter '2' for ID");
            System.out.println("Enter '3' for age");
            System.out.println("Enter '4' for sex");
            System.out.println("Enter '5' for salary");
            System.out.println("enter '6' for position");

            choice = scanner.next();
            scanner.nextLine(); /* Consume the \n char.*/
        
            switch(choice){
                case "1" -> valid = true;
                case "2" -> valid = true;
                case "3" -> valid = true;
                case "4" -> valid = true;
                case "5" -> valid = true;
                case "6" -> valid = true;}
            }

        if(choice.equals("1")){
            System.out.print("\nEnter the new employee name: ");
            String newName = scanner.nextLine();
            if(newName.equals("0")) mainMenu();
            employees.get(index -1).name = newName;
            System.out.println("\nName has been edited !");
            try{saveEmployees();} catch(IOException e){}
            editEmployees();
        }

        if(choice.equals("2")){
            String newID;
            do {
                System.out.print("\nEnter the new ID (8 numbers): ");
                newID = scanner.next();
                if(newID.equals("0")) mainMenu();
                if(! Employee.IDAvalible(newID))
                    System.out.println("\nID already taken !");}
            while(newID.length() != 8 || !Employee.isAllDigit(newID) || !Employee.IDAvalible(newID));
            employees.get(index - 1).setID(newID);
            System.out.println("\nID has been edited !");
            try{saveEmployees();} catch(IOException e){}
            editEmployees();
        }

        if(choice.equals("3")){
            int newAge = 0;
            while(newAge < 16){
                System.out.print("\nEnter the new employee age (Must be 16 or above): ");
                String tempAge = scanner.next();
                if(tempAge.equals("0")) mainMenu();
                try{
                    newAge = Integer.parseInt(tempAge);}
                catch(NumberFormatException e){
                    System.out.println("\nPlease enter digits only !");}
            }
            employees.get(index - 1).setAge(newAge);
            System.out.println("\nAge has been edited !");
            try{saveEmployees();} catch(IOException e){}
            editEmployees();
        }

        if(choice.equals("4")){
        int sex = -1;
        boolean isMale = true;
        do{
            System.out.print("\nenter 1 if male or 2 if female: ");
            String tempSex = scanner.next();
            if(tempSex.equals("0")) mainMenu();
            try{sex = Integer.parseInt(tempSex);}
            catch(NumberFormatException e){
                System.out.println("\nPlease enter 1 or 2 !");
        }}
        while(sex != 1 && sex != 2);
        if(sex == 1) isMale = true; else if(sex == 2) isMale = false;
        employees.get(index - 1).isMale = isMale;
        System.out.println("\nSex has been edited !");
        try{saveEmployees();} catch(IOException e){}
        editEmployees();}

        if(choice.equals("5")){
            int newSalary = 0;
            do{
                System.out.print("\nEnter the new employee salary (1000 or more): ");
                String tempSalary = scanner.next();
                if(tempSalary.equals("0")) mainMenu();
                try{
                    newSalary = Integer.parseInt(tempSalary);}
                catch(NumberFormatException e){
                    System.out.println("\nPlease enter a number !");
                }}
                while(newSalary < 1000);
            employees.get(index - 1).setSalary(newSalary);
            System.out.println("\nSalary has been edited !");
            try{saveEmployees();} catch(IOException e){}
            editEmployees();
        }

        int posIndex = -1;
        if(choice.equals("6")){
            do{System.out.println("\nPlease enter the employee's new position (number): ");
                for (int i = 0; i < Position.values().length; i++){
                    System.out.print((i + 1) + " - ");
                    System.out.println(Position.values()[i]);}
                
                String tempIndex = scanner.next();
                if(tempIndex.equals("0")) mainMenu();
                try{
                    posIndex = Integer.parseInt(tempIndex);}
                catch(NumberFormatException e){
                    System.out.println("\nPlease enter a number !");
                }}
            while(posIndex < 1 || posIndex > Position.values().length);

            employees.get(index - 1).position = Position.values()[posIndex - 1];
            System.out.println("\nPosition has been edited !");
            try{saveEmployees();} catch(IOException e){}
            editEmployees();
        }

        }

        static void saveEmployees() throws IOException{

            ObjectOutputStream objectOut = null;
            FileOutputStream fileOut = null;

            
            fileOut = new FileOutputStream("employees.ser");
            objectOut = new ObjectOutputStream(fileOut);

            ArrayList<Employee> tempList = new ArrayList<>();

            for (int i = 0; i < employees.size(); i++)
                tempList.add(employees.get(i));
            objectOut.writeObject(tempList);
            
            if(objectOut != null){
                objectOut.close();}
            
        }

    }

