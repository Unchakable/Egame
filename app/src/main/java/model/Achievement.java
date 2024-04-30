package model;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "achievement")
public class Achievement {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private Date date;

    @DatabaseField(canBeNull = false, columnName = "number_of_correct_answer")
    private int numberOfCorrectAnswers;

    @DatabaseField(canBeNull = false, columnName = "number_of_wrong_answer")
    private int numberOfWrongAnswers;

    @DatabaseField(columnName = "series_test", canBeNull = false)
    private int seriesTest;

    public Achievement() {
    }

    public Achievement(Date date, int numberOfCorrectAnswers, int numberOfWrongAnswers, int seriesTest) {
        this.date = date;
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
        this.numberOfWrongAnswers = numberOfWrongAnswers;
        this.seriesTest = seriesTest;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public int getNumberOfWrongAnswers() {
        return numberOfWrongAnswers;
    }

    public void setNumberOfWrongAnswers(int numberOfWrongAnswers) {
        this.numberOfWrongAnswers = numberOfWrongAnswers;
    }

    public int getSeriesTest() {
        return seriesTest;
    }

    public void setSeriesTest(int seriesTest) {
        this.seriesTest = seriesTest;
    }
}