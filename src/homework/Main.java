package homework;
import java.sql.*;
import java.util.*;

public class Main {
    private static DataSourceImp dataSourceImp;
    private static Students students = new Students();

    public static void main(String[] args) {
        dataSourceImp = new DataSourceImp();
        Scanner input = new Scanner(System.in);
        int op;
        do {
            System.out.println("1.Insert new student");
            System.out.println("2.Show all students");
            System.out.println("3.Update new student");
            System.out.println("4.Delete student");
            System.out.println("5.Search student");
            System.out.println("6.Exit");
            op = input.nextInt();
            switch (op) {
                case 1 -> {
                    System.out.println("Enter student ID:");
                    students.setId(input.nextInt());
                    input.nextLine();
                    System.out.println("Enter student name: ");
                    students.setName(input.nextLine());
                    System.out.println("Enter student age");
                    students.setAge(input.nextInt());
                    insertStudent(students);

                }
                case 2 -> {
                    printStudent();

                }
                case 3 -> {
                 //   updateStudent();

                }
                case 4 -> {
                    deleteStudent();


                }
                case 5 -> {


                }
                default -> {
                    System.out.println("Input the wrong option!!!! ");
                }

            }

        } while (op != 5);
    }

    public static void printStudent() {
        try (Connection connection = dataSourceImp.dataSource().getConnection()) {
            //1.Create sql statement object
            String selectSQL = "SELECT * FROM students";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            //2.Execute
            ResultSet resultSet = preparedStatement.executeQuery();
            //3.Process result with result set
            List<Students> studentsList = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                studentsList.add(new Students(id, name, age));
            }
            studentsList.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void insertStudent(Students students) {
        try (Connection connection = dataSourceImp.dataSource().getConnection()) {
            String insertSQL = "INSERT INTO students (id,name,age) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, students.getId());
            preparedStatement.setString(2, students.getName());
            preparedStatement.setInt(3, students.getAge());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateStudent() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter student id to update :");
        int id= input.nextInt();
        input.nextLine();
        System.out.println("Enter student name: ");
        String name = input.nextLine();
        System.out.println("Enter student age");

      int age = input.nextInt();

            try (Connection connection = dataSourceImp.dataSource().getConnection()) {
                String updateSQL = "UPDATE students SET name=?,age=? WHERE id =?";

                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
                students.setId(id);
                students.setName(name);
                students.setAge(age);
                int rowUpdated = preparedStatement.executeUpdate();
                if (rowUpdated > 0) {
                    System.out.println("Updated successfully!!!");
                } else System.out.println("updated fail!!!!");

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    public static void deleteStudent(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter student id to delete ");
        int id = input.nextInt();
        try (Connection connection = dataSourceImp.dataSource().getConnection()) {
            String deleteSQL = "DELETE FROM students WHERE id =?";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1,id);


            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0) {
                System.out.println("Deleted successfully!!!");
            } else System.out.println("Deleted fail!!!!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

