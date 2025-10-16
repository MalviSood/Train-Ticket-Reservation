import java.sql.*;

public class TrainTicketReservation {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/TrainTicketReservation",
                    "root",
                    "Ms@1972t"
            );

            // Insert 2 tickets only if not already present
            insertTicket(con, "Rahul Sharma", "12345", "A1");
            insertTicket(con, "Priya Singh", "54321", "B2");

            // Update seat of ticket_id 1
            String updateSql = "UPDATE TrainTickets SET seat_no = ? WHERE ticket_id = ?";
            PreparedStatement updatePstmt = con.prepareStatement(updateSql);
            updatePstmt.setString(1, "C3");
            updatePstmt.setInt(2, 1);
            updatePstmt.executeUpdate();

            // Retrieve and display all tickets
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM TrainTickets");
            System.out.println("ID | Name | Train No | Seat No");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("ticket_id") + " | "
                                + rs.getString("passenger_name") + " | "
                                + rs.getString("train_no") + " | "
                                + rs.getString("seat_no")
                );
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to avoid duplicate inserts
    private static void insertTicket(Connection con, String name, String trainNo, String seatNo) throws SQLException {
        // Check if passenger already exists with the same train
        String checkSql = "SELECT COUNT(*) FROM TrainTickets WHERE passenger_name = ? AND train_no = ?";
        PreparedStatement checkStmt = con.prepareStatement(checkSql);
        checkStmt.setString(1, name);
        checkStmt.setString(2, trainNo);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();

        if (rs.getInt(1) == 0) { // No existing record found → safe to insert
            String insertSql = "INSERT INTO TrainTickets (passenger_name, train_no, seat_no) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1, name);
            pstmt.setString(2, trainNo);
            pstmt.setString(3, seatNo);
            pstmt.executeUpdate();
            System.out.println("Inserted: " + name);
        }
    }
}
