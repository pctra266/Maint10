/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ContractorCard;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ADMIN
 */
public class ContractorCardDAO extends DBContext {

    public boolean addContractorCard(ContractorCard card) {
        String sql = "INSERT INTO ContractorCard (WarrantyCardID, StaffID, ContractorID, Status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, card.getWarrantyCardID());
            stmt.setInt(2, card.getStaffID());
            stmt.setInt(3, card.getContractorID());
            stmt.setString(4, card.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    card.setContractorCardID(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateContractorCard(ContractorCard card) {
        String sql = "UPDATE ContractorCard SET WarrantyCardID = ?, StaffID = ?, ContractorID = ?, Status = ? WHERE ContractorCardID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, card.getWarrantyCardID());
            stmt.setInt(2, card.getStaffID());
            stmt.setInt(3, card.getContractorID());
            stmt.setString(4, card.getStatus());
            stmt.setInt(5, card.getContractorCardID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteContractorCard(int contractorCardID) {
        String sql = "DELETE FROM ContractorCard WHERE ContractorCardID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contractorCardID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ContractorCard getContractorCardById(int contractorCardID) {
        String sql = "SELECT * FROM ContractorCard WHERE ContractorCardID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contractorCardID);
            ResultSet rs = stmt.executeQuery();
            ContractorCard contractorCard = new ContractorCard();
            if (rs.next()) {
                contractorCard.setContractorCardID(rs.getInt("ContractorCardID"));
                contractorCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                contractorCard.setStaffID(rs.getInt("StaffID"));
                contractorCard.setContractorID(rs.getInt("ContractorID"));
                contractorCard.setStatus(rs.getString("Status"));
                contractorCard.setNote(rs.getString("Note"));
                return contractorCard;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ContractorCard> getAllContractorCards() {
        List<ContractorCard> list = new ArrayList<>();
        String sql = "SELECT * FROM ContractorCard";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ContractorCard contractorCard = new ContractorCard();
                contractorCard.setContractorCardID(rs.getInt("ContractorCardID"));
                contractorCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                contractorCard.setStaffID(rs.getInt("StaffID"));
                contractorCard.setContractorID(rs.getInt("ContractorID"));
                contractorCard.setStatus(rs.getString("Status"));
                contractorCard.setNote(rs.getString("Note"));
                contractorCard.setDate(rs.getTimestamp("Date"));
                list.add(contractorCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
