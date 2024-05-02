package model;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

public class AchievementsDAO extends BaseDaoImpl<Achievements, Integer>{
    protected AchievementsDAO(ConnectionSource connectionSource, Class<Achievements> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
