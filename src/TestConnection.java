import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection conn = DatabaseManager.getConnection();

            if (conn != null) {
                System.out.println("Berhasil connect ke database");
            }

        } catch (Exception e) {
            System.out.println("Gagal connect");
            e.printStackTrace();
        }

    }

}