package lab.work;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsManager extends DBHelper<GoodsData> {

    @Override
    public String getTableName() {
        return "Goods";
    }

    @Override
    public GoodsData loadObject(ResultSet rs) throws SQLException {
        return new GoodsData(rs.getInt("ID"), rs.getString("name"));
    }

}
