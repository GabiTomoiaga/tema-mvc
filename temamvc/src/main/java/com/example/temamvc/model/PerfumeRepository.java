package com.example.temamvc.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfumeRepository implements Observable {

    private StockRepository stockRepo = new StockRepository();
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public List<Perfume> getAll() {
        List<Perfume> perfumes = new ArrayList<>();
        String query = "SELECT * FROM perfume";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                perfumes.add(new Perfume(
                        rs.getInt("perfume_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perfumes;
    }

    public Perfume getById(Integer id) {
        String query = "SELECT * FROM perfume WHERE perfume_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Perfume(
                        rs.getInt("perfume_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Perfume> searchByName(String name) {
        List<Perfume> results = new ArrayList<>();
        String query = "SELECT * FROM perfume WHERE LOWER(name) LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(new Perfume(
                        rs.getInt("perfume_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public boolean addPerfume(Perfume perfume) {
        String query = "INSERT INTO perfume (name, brand, price, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, perfume.getName());
            stmt.setString(2, perfume.getBrand());
            stmt.setDouble(3, perfume.getPrice());
            stmt.setString(4, perfume.getDescription());

            boolean success = stmt.executeUpdate() > 0;
            if (success) notifyObservers();
            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePerfume(Perfume perfume) {
        String query = "UPDATE perfume SET name = ?, brand = ?, price = ?, description = ? WHERE perfume_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, perfume.getName());
            stmt.setString(2, perfume.getBrand());
            stmt.setDouble(3, perfume.getPrice());
            stmt.setString(4, perfume.getDescription());
            stmt.setInt(5, perfume.getId());

            boolean success = stmt.executeUpdate() > 0;
            if (success) notifyObservers();
            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePerfume(Integer id) {
        String query = "DELETE FROM perfume WHERE perfume_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            boolean success = stmt.executeUpdate() > 0;
            if (success) notifyObservers();
            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Perfume> filterPerfumes(String brandFilter, String priceFilter, boolean onlyAvailable) {
        List<Perfume> allPerfumes = this.getAll();
        List<Stock> stocks = stockRepo.getAll();

        return allPerfumes.stream()
                .filter(p -> brandFilter == null || brandFilter.isEmpty() || p.getBrand().equalsIgnoreCase(brandFilter))
                .filter(p -> {
                    if (priceFilter == null || priceFilter.isEmpty()) return true;
                    double price = p.getPrice();
                    return switch (priceFilter) {
                        case "<100" -> price < 100;
                        case "100-300" -> price >= 100 && price <= 300;
                        case ">300" -> price > 300;
                        default -> true;
                    };
                })
                .filter(p -> {
                    if (!onlyAvailable) return true;
                    return stocks.stream()
                            .anyMatch(s -> s.getPerfumeId() == p.getId() && s.getQuantity() > 0);
                })
                .toList();
    }
}
