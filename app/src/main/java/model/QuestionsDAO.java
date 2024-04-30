package model;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;


public class QuestionsDAO extends BaseDaoImpl<Questions, Integer> {

    protected QuestionsDAO(ConnectionSource connectionSource, Class<Questions> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}