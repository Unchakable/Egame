package model;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;


public class QuestionsDAO extends BaseDaoImpl<Questions, Integer> {

    protected QuestionsDAO(ConnectionSource connectionSource, Class<Questions> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Questions getQuestionsById(int id) throws SQLException {
        QueryBuilder<Questions, Integer> queryBuilder = queryBuilder();
        return queryBuilder.where().eq("id", id).queryForFirst();
    }

    public Integer getCountOfRecords() throws SQLException {
        QueryBuilder<Questions, Integer> queryBuilder = queryBuilder();
        long countOfRecords = queryBuilder.countOf();
        return (int) (long) countOfRecords;
    }
}