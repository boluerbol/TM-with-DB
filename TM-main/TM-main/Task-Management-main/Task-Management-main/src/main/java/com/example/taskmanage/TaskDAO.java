package com.example.taskmanage;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskDAO implements Task {
    private Connection con;
    private String taskId;
    private String taskName;
    private String taskDescription;
    private Priority priority;
    private LocalDate deadline;
    private boolean isComplete;

    public TaskDAO(){
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String pass = "123";
        try{
            this.con = DriverManager
                    .getConnection(url, username, pass);
            System.out.println("database successfully connected");
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }
    }
    @Override
    public void createTask(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public void markAsIncomplete() {

    }

    @Override
    public void markAsComplete() {
        this.isComplete = true;
    }

    @Override
    public Priority getPriority() {
        return null;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void setDeadline(LocalDate date) {
        this.deadline = date;
    }

    @Override
    public String getTaskName() {
        return this.taskName;
    }

    @Override
    public String getTaskDescription() {
        return null;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public int getTaskId() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    public void insertTask(Task task) {
        String sql = "INSERT INTO tasks (task_name, task_description, deadline, priority, is_completed) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, task.getTaskName());
            pstmt.setString(2, task.getTaskDescription());
            pstmt.setDate(3, Date.valueOf(task.getDeadline()));
            pstmt.setString(4, task.getPriority().toString()); // Assuming getPriority returns an enum value
            pstmt.setBoolean(5, task.isComplete());
            pstmt.executeUpdate();
            System.out.println("Task inserted successfully");
        } catch (SQLException e) {
            System.out.println("Insertion error: " + e.getMessage());
        }
    }


    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET task_name = ?, task_description = ?, deadline = ?, priority = ?, is_complete = ? WHERE task_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, task.getTaskName());
            pstmt.setString(2, task.getTaskDescription());
            pstmt.setDate(3, Date.valueOf(task.getDeadline()));
            pstmt.setString(4, task.getPriority().toString()); // Assuming getPriority returns an enum value
            pstmt.setBoolean(5, task.isComplete());
            pstmt.setInt(6, task.getTaskId()); // Assuming you have a task ID
            pstmt.executeUpdate();
            System.out.println("Task updated successfully");
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }


    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.executeUpdate();
            System.out.println("Task deleted successfully");
        } catch (SQLException e) {
            System.out.println("Deletion error: " + e.getMessage());
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new HomeworkTask(); // Instantiate your Task implementation
                // Populate task attributes from the ResultSet
                task.setTaskName(rs.getString("task_name"));
                task.setTaskDescription(rs.getString("task_description"));
                // Set other attributes
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Retrieval error: " + e.getMessage());
        }
        return tasks;
    }




}

