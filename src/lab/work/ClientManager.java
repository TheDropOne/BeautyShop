package lab.work;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientManager extends DBHelper<ClientData> {

    @Override
    public String getTableName() {
        return "Clients";
    }

    @Override
    public ClientData loadObject(ResultSet rs) throws SQLException {
        return new ClientData(rs.getInt("ID"), rs.getString("name"));
    }

}
