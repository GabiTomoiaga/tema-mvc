package com.example.temamvc.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockRepository implements Observable {

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

    public List<Stock> getAll() {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM stock";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                stocks.add(new Stock(
                        rs.getInt("stock_id"),
                        rs.getInt("store_id"),
                        rs.getInt("perfume_id"),
                        rs.getInt("quantity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    public boolean updateStockQuantity(int stockId, int newQuantity) {
        String query = "UPDATE stock SET quantity = ? WHERE stock_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, stockId);

            boolean success = stmt.executeUpdate() > 0;
            if (success) notifyObservers();
            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
