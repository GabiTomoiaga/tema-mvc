package com.example.temamvc.controller;

import com.example.temamvc.model.Perfume;
import com.example.temamvc.model.PerfumeRepository;
import com.example.temamvc.model.StockRepository;
import com.example.temamvc.view.Employeeview;

import java.util.List;

public class EmployeeController {

    private final Employeeview view;
    private final PerfumeRepository perfumeRepo;
    private final StockRepository stockRepo;

    public EmployeeController(Employeeview view, PerfumeRepository perfumeRepo, StockRepository stockRepo) {
        this.view = view;
        this.perfumeRepo = perfumeRepo;
        this.stockRepo = stockRepo;

        // Conectăm butoanele din view cu metodele controllerului
        initController();

        // Inițializăm datele în tabel
        loadPerfumes();
    }

    private void initController() {
        view.getAddButton().setOnAction(e -> addPerfume());
        view.getUpdateButton().setOnAction(e -> updatePerfume());
        view.getDeleteButton().setOnAction(e -> deletePerfume());
        view.getFilterButton().setOnAction(e -> applyFilters());
        view.getResetButton().setOnAction(e -> resetFilters());
    }

    private void loadPerfumes() {
        List<Perfume> perfumes = perfumeRepo.getAll();
        view.updateTable(perfumes);
    }

    private void addPerfume() {
        Perfume perfume = view.getPerfumeInput();
        if (perfume != null) {
            boolean success = perfumeRepo.addPerfume(perfume);
            if (success) {
                view.showMessage("Parfumul a fost adăugat cu succes!");
                loadPerfumes();
            } else {
                view.showError("Eroare la adăugarea parfumului.");
            }
        } else {
            view.showError("Datele introduse sunt invalide!");
        }
    }

    private void updatePerfume() {
        Perfume perfume = view.getPerfumeInput();
        if (perfume != null && perfume.getId() != null) {
            boolean success = perfumeRepo.updatePerfume(perfume);
            if (success) {
                view.showMessage("Parfumul a fost actualizat cu succes!");
                loadPerfumes();
            } else {
                view.showError("Eroare la actualizarea parfumului.");
            }
        } else {
            view.showError("Selectați un parfum din tabel!");
        }
    }

    private void deletePerfume() {
        Perfume selected = view.getSelectedPerfume();
        if (selected != null) {
            boolean success = perfumeRepo.deletePerfume(selected.getId());
            if (success) {
                view.showMessage("Parfumul a fost șters cu succes!");
                loadPerfumes();
            } else {
                view.showError("Eroare la ștergerea parfumului.");
            }
        } else {
            view.showError("Selectați un parfum din tabel!");
        }
    }

    private void applyFilters() {
        String brandFilter = view.getBrandFilter();
        String priceFilter = view.getPriceFilter();
        boolean onlyAvailable = view.isOnlyAvailableChecked();

        List<Perfume> filtered = perfumeRepo.filterPerfumes(brandFilter, priceFilter, onlyAvailable);
        view.updateTable(filtered);
    }

    private void resetFilters() {
        view.clearFilters();
        loadPerfumes();
    }

    public void refreshTable() {
    }
}
