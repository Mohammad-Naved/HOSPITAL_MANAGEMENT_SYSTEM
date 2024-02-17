package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public void addPatient(){
        System.out.println("Enter Patient Name: ");
        String name=scanner.next();
        System.out.println("Enter Patient Age: ");
        String age=scanner.next();
        System.out.println("Enter Patient Gender: ");
        String gender=scanner.next();
        try {
            String query="INSERT INTO patient(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows=preparedStatement.executeUpdate();

            if(affectedRows>0)
                System.out.println("Patient added Successfully!!");
            else
                System.out.println("Failed to add Patient!!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void viewPatient(){
        String query="SELECT * FROM PATIENT";

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();

            System.out.println("Patients");
            System.out.println("+------------+--------------------+-----------+--------------+");
            System.out.println("| Patient Id | Name               | Age       | Gender      |");
            System.out.println("+------------+--------------------+-----------+--------------+");
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String age=resultSet.getString("age");
                String gender=resultSet.getString("gender");
                System.out.printf("|%-12s|%-20s|%-11s|%-13s|\n",id, name,age,gender);
                System.out.println("+------------+--------------------+-----------+--------------+");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query="SELECT * FROM patient WHERE id = ?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
