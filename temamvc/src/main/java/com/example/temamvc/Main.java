package com.example.temamvc;

import com.example.temamvc.controller.EmployeeController;
import com.example.temamvc.model.PerfumeRepository;
import com.example.temamvc.model.StockRepository;
import com.example.temamvc.view.Employeeview;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Inițializăm Repositories (Model)
        PerfumeRepository perfumeRepository = new PerfumeRepository();
        StockRepository stockRepository = new StockRepository();

        // 2. Încărcăm View-ul din FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeView.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        Employeeview employeeView = loader.getController();

        // 3. Creăm Controller-ul și îl legăm de View + Model
        EmployeeController employeeController = new EmployeeController(employeeView, perfumeRepository, stockRepository);

        // 4. Adăugăm View-ul ca Observer la repository-uri
        perfumeRepository.addObserver(employeeView);
        stockRepository.addObserver(employeeView);

        // 5. Setăm scena
        primaryStage.setTitle("Employee - Perfume Management");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 6. Inițializăm tabelul cu date (dacă există)
        employeeController.refreshTable();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
