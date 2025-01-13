import java.io.*;

public class Employee implements Serializable{
    
    private String ID = "00000000";
    String name;
    private int age;
    boolean isMale;
    private int salary;
    Position position;

    Employee(String name, String ID, Position position, boolean isMale, int salary, int age){
        this.name = name;
        setAge(age);
        setSalary(salary);
        this.isMale = isMale;
        this.position = position;
        setID(ID);}
    
    @Override
    public String toString(){
        return "\nName: "+ name+"\nID: "+ID+"\nAge: "+age+"\nMale: "+isMale+"\nSalary: "+salary+"\nPosition: "+position;
    }

    public static void writeEmployees(){
        
    }

    void setID(String ID){
        if(ID.length() != 8){
            System.out.println("ID must be exactly 8 numbers !");
            return;}
        for (int i = 0; i < ID.length(); i++)
            if(!Character.isDigit(ID.charAt(i))){
                System.out.println("ID must only contain numbers !"); 
                return;}
        this.ID = ID;}

    String getID(){
        return ID;}

    void setAge(int age){
        if(age < 16){
            System.out.println("Employee must be at least 16 !");
            return;}
        this.age = age;}
    
        int getAge(){
            return age;}
    
    void setSalary(int salary){
        if(salary < 1000){
            System.out.println("Salary must be at least 1000 Sr !");
            return;}
        this.salary = salary;}
    
    int getSalary(){
        return salary;
    }

    public static boolean isAllDigit(String ID){
        for (int i = 0; i < ID.length(); i++)
            if(!Character.isDigit(ID.charAt(i)))
                return false;
        return true;
    }

    public static boolean IDAvalible(String ID){
        for (int i = 0; i < Main.employees.size(); i++){
            String currentID = Main.employees.get(i).getID();
            if(currentID.equals(ID))
                return false;
            }
        return true;
    }               


}

