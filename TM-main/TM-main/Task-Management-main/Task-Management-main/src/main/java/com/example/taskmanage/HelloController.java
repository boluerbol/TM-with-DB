package com.example.taskmanage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.w3c.dom.events.MouseEvent;

import java.time.LocalDate;

public class HelloController {
    @FXML
    private CheckBox Completed;
    @FXML
    private RadioButton Homework;
    @FXML
    private RadioButton Meeting;
    @FXML
    private RadioButton Shopping;
    @FXML
    private DatePicker Deadline;

    @FXML
    private Button Delete;
    @FXML
    private ListView<Task> listView;
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputDesc;

    @FXML
    private Label label;

    private TaskDAO taskDAO; // Instantiate TaskDAO

    public HelloController() {
        taskDAO = new TaskDAO(); // Initialize TaskDAO in the constructor
        tasks = FXCollections.observableArrayList(taskDAO.getAllTasks());
    }
    @FXML
    protected void onListViewSelected(MouseEvent event) {
        // Your implementation for handling the ListView selection
    }

    @FXML
    public void initialize() {
        listView.setItems(tasks);
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        Task selectedTask = listView.getSelectionModel().getSelectedItem();
                        displaySelectedTask(selectedTask);
                    }
                }
        );
    }

    private void displaySelectedTask(Task selectedTask) {
        inputName.setText(selectedTask.getTaskName());
        inputDesc.setText(selectedTask.getTaskDescription());
        Deadline.setValue(selectedTask.getDeadline());
        String taskName = selectedTask.getTaskName();

        switch (taskName) {
            case "Homework":
                Homework.setSelected(true);
                break;
            case "Meeting":
                Meeting.setSelected(true);
                break;
            case "Shopping":
                Shopping.setSelected(true);
                break;
            default:
                Homework.setSelected(false);
                Meeting.setSelected(false);
                Shopping.setSelected(false);
                break;
        }
        // Set the appropriate radio button based on the task type
        // Implement this logic based on your task type structure
    }

    @FXML
    protected void onSaveButtonClick() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if (selectedIndex > 0) {
            Task selectedTask = tasks.get(selectedIndex);
            updateSelectedTask(selectedTask);

            taskDAO.updateTask(selectedTask); // Update the task in the database using TaskDAO method
            tasks.set(selectedIndex, selectedTask); // Refresh the ListView to reflect changes
            clearFieldsAndSelection();
        } else {
            Task newTask = createNewTask();
            taskDAO.insertTask(newTask); // Add the new task to the database using TaskDAO method
            tasks.add(newTask); // Add the new task to the tasks list and update the UI
            clearFieldsAndSelection();
        }
    }

    private void updateSelectedTask(Task task) {
        task.setTaskName(inputName.getText());
        task.setTaskDescription(inputDesc.getText());
        LocalDate selectedDate = Deadline.getValue();
        if (selectedDate != null) {
            task.setDeadline(selectedDate);
        }

        // Update other task properties not handled by UI elements
        if (Homework.isSelected()) {
            task.setPriority(Priority.HIGH); // Example: Setting priority for a Homework task
        } else if (Meeting.isSelected()) {
            task.setPriority(Priority.MEDIUM); // Example: Setting priority for a Meeting task
        } else if (Shopping.isSelected()) {
            task.setPriority(Priority.LOW); // Example: Setting priority for a Shopping task
        }

        if (Completed.isSelected()) {
            task.markAsComplete();
        } else {
            task.markAsIncomplete();
        }
    }


    private Task createNewTask() {
        Task newTask = new HomeworkTask(); // Create a new task object
        newTask.createTask(inputName.getText(), inputDesc.getText());
        LocalDate selectedDate = Deadline.getValue();
        if (selectedDate != null) {
            newTask.setDeadline(selectedDate);
        }

        // Set other task properties not managed by UI elements
        if (Homework.isSelected()) {
            newTask.setPriority(Priority.HIGH); // Example: Setting priority for a Homework task
        } else if (Meeting.isSelected()) {
            newTask.setPriority(Priority.MEDIUM); // Example: Setting priority for a Meeting task
        } else if (Shopping.isSelected()) {
            newTask.setPriority(Priority.LOW); // Example: Setting priority for a Shopping task
        }
        // Set completion status
        if (Completed.isSelected()) {
            newTask.markAsComplete();
        } else {
            newTask.markAsIncomplete();
        }

        // Set other properties as needed

        return newTask;
    }


    @FXML
    protected void onDeleteButtonClick() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskDAO.deleteTask(selectedTask.getId()); // Delete the selected task from the database using TaskDAO method
            tasks.remove(selectedTask); // Remove the selected task from the tasks list and update the UI
        }
        clearFieldsAndSelection();
    }

    private void clearFieldsAndSelection() {
        inputDesc.clear();
        inputName.clear();
        Deadline.setValue(null);
        Homework.setSelected(false);
        Meeting.setSelected(false);
        Shopping.setSelected(false);
        Completed.setVisible(false);
        listView.getSelectionModel().clearSelection();
    }

    public void onListViewSelected(javafx.scene.input.MouseEvent mouseEvent) {
    }
}
