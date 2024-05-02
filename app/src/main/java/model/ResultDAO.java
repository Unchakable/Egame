package model;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

public class ResultDAO extends BaseDaoImpl<Result, Integer> {
    protected ResultDAO(ConnectionSource connectionSource, Class<Result> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}