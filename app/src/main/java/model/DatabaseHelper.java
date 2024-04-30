package model;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="Egame.db";

    private static final int DATABASE_VERSION = 1;

    private QuestionsDAO questionsDAO = null;
    private AchievementDAO achievementDAO = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, Questions.class);
            TableUtils.createTable(connectionSource, Achievement.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer){
        try{
            TableUtils.dropTable(connectionSource, Questions.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }

    public QuestionsDAO getQuestionsDAO() throws SQLException{
        if(questionsDAO == null){
            questionsDAO = new QuestionsDAO(getConnectionSource(), Questions.class);
        }
        return questionsDAO;
    }

    public AchievementDAO getAchievementDAO() throws SQLException{
        if(achievementDAO == null){
            achievementDAO = new AchievementDAO(getConnectionSource(), Achievement.class);
        }
        return achievementDAO;
    }

    @Override
    public void close(){
        super.close();
        questionsDAO = null;
    }
}