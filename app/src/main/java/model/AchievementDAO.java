package model;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

public class AchievementDAO extends BaseDaoImpl<Achievement, Integer> {
    protected AchievementDAO(ConnectionSource connectionSource, Class<Achievement> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}