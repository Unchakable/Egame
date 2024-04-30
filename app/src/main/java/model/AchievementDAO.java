package model;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AchievementDAO extends BaseDaoImpl<Achievement, Integer> {
    protected AchievementDAO(ConnectionSource connectionSource, Class<Achievement> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Achievement> getQuestionsById(LocalDate date) throws SQLException {
        QueryBuilder<Achievement, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("date", date);
        PreparedQuery<Achievement> preparedQuery = queryBuilder.prepare();
        if (Objects.isNull(query(preparedQuery))) return null;
        else return query(preparedQuery);
    }

    public Integer getCountOfRecords() throws SQLException {
        QueryBuilder<Achievement, Integer> queryBuilder = queryBuilder();
        long countOfRecords = queryBuilder.countOf();
        return (int) (long) countOfRecords;
    }
}