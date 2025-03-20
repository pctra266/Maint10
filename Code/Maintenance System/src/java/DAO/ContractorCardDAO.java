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
        String sql = "INSERT INTO ContractorCard (WarrantyCardID, StaffID, ContractorID, Status, Note) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, card.getWarrantyCardID());
            stmt.setInt(2, card.getStaffID());
            stmt.setInt(3, card.getContractorID());
            stmt.setString(4, card.getStatus());
            stmt.setString(5, card.getNote());

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
            System.out.println(e);
        }
        return list;
    }

    public List<ContractorCard> searchContractorCards(
            int contractorId,
            String warrantyCardCode,
            String staffName,
            String date,
            String status,
            String note,
            int offset,
            int rowCount) {

        List<ContractorCard> contractorCards = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT wc.WarrantyCardID, wc.WarrantyCardCode, s.[Name] AS StaffName, cc.[Date], cc.[Status], cc.Note "
                + "FROM ContractorCard cc "
                + "INNER JOIN WarrantyCard wc ON cc.WarrantyCardID = wc.WarrantyCardID "
                + "INNER JOIN Staff s ON cc.StaffID = s.StaffID "
                + "WHERE cc.ContractorID = ?"
        );

        if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
            sql.append(" AND wc.WarrantyCardCode LIKE ?");
        }
        if (staffName != null && !staffName.trim().isEmpty()) {
            sql.append(" AND s.[Name] LIKE ?");
        }
        if (date != null && !date.trim().isEmpty()) {
            sql.append(" AND CONVERT(varchar(10), cc.[Date], 120) = ?");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND cc.[Status] LIKE ?");
        }
        if (note != null && !note.trim().isEmpty()) {
            sql.append(" AND cc.Note LIKE ?");
        }

        sql.append(" ORDER BY cc.[Date] DESC ");
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setInt(index++, contractorId);

            if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
                ps.setString(index++, "%" + warrantyCardCode + "%");
            }
            if (staffName != null && !staffName.trim().isEmpty()) {
                ps.setString(index++, "%" + staffName + "%");
            }
            if (date != null && !date.trim().isEmpty()) {
                ps.setString(index++, date);
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(index++, "%" + status + "%");
            }
            if (note != null && !note.trim().isEmpty()) {
                ps.setString(index++, "%" + note + "%");
            }

            ps.setInt(index++, offset);
            ps.setInt(index++, rowCount);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ContractorCard dto = new ContractorCard();
                    dto.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                    dto.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                    dto.setStaffName(rs.getString("StaffName"));
                    dto.setDate(rs.getTimestamp("Date"));
                    dto.setStatus(rs.getString("Status"));
                    dto.setNote(rs.getString("Note"));
                    contractorCards.add(dto);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return contractorCards;
    }

    public int countContractorCards(
            int contractorId,
            String warrantyCardCode,
            String staffName,
            String date,
            String status,
            String note) {

        int count = 0;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) "
                + "FROM ContractorCard cc "
                + "INNER JOIN WarrantyCard wc ON cc.WarrantyCardID = wc.WarrantyCardID "
                + "INNER JOIN Staff s ON cc.StaffID = s.StaffID "
                + "WHERE cc.ContractorID = ?"
        );

        if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
            sql.append(" AND wc.WarrantyCardCode LIKE ?");
        }
        if (staffName != null && !staffName.trim().isEmpty()) {
            sql.append(" AND s.[Name] LIKE ?");
        }
        if (date != null && !date.trim().isEmpty()) {
            sql.append(" AND CONVERT(varchar(10), cc.[Date], 120) = ?");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND cc.[Status] LIKE ?");
        }
        if (note != null && !note.trim().isEmpty()) {
            sql.append(" AND cc.Note LIKE ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            ps.setInt(index++, contractorId);

            if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
                ps.setString(index++, "%" + warrantyCardCode + "%");
            }
            if (staffName != null && !staffName.trim().isEmpty()) {
                ps.setString(index++, "%" + staffName + "%");
            }
            if (date != null && !date.trim().isEmpty()) {
                ps.setString(index++, date);
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(index++, "%" + status + "%");
            }
            if (note != null && !note.trim().isEmpty()) {
                ps.setString(index++, "%" + note + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return count;
    }

    public boolean updateContractorStatus(int contractorCardId, String newStatus) {
        String sql = "UPDATE ContractorCard SET Status = ? WHERE ContractorCardID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, contractorCardId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating contractor status: " + e.getMessage());
            return false;
        }
    }

}
