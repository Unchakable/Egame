package model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "question")
public class Questions {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String question;
    @DatabaseField(canBeNull = false)
    private String decision;
    @DatabaseField(canBeNull = false, columnName = "right_answer")
    private String RightAnswer;
    @DatabaseField(canBeNull = false, columnName = "row_answer_1")
    private String RowAnswer1;
    @DatabaseField(canBeNull = false, columnName = "row_answer_2")
    private String RowAnswer2;
    @DatabaseField(canBeNull = false, columnName = "row_answer_3")
    private String RowAnswer3;
    @DatabaseField(canBeNull = false, columnName = "in_used")
    private boolean inUsed;

    public Questions() {

    }
    public Questions(String question, String decision, String rightAnswer, String rowAnswer1, String rowAnswer2, String rowAnswer3, boolean inUsed) {
        this.question = question;
        this.decision = decision;
        this.RightAnswer = rightAnswer;
        this.RowAnswer1 = rowAnswer1;
        this.RowAnswer2 = rowAnswer2;
        this.RowAnswer3 = rowAnswer3;
        this.inUsed = inUsed;
    }

    public boolean getInUsed() {
        return inUsed;
    }

    public void setInUsed(boolean inUsed) {
        this.inUsed = inUsed;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getRightAnswer() {
        return RightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        RightAnswer = rightAnswer;
    }

    public String getRowAnswer1() {
        return RowAnswer1;
    }

    public void setRowAnswer1(String rowAnswer1) {
        RowAnswer1 = rowAnswer1;
    }

    public String getRowAnswer2() {
        return RowAnswer2;
    }

    public void setRowAnswer2(String rowAnswer2) {
        RowAnswer2 = rowAnswer2;
    }

    public String getRowAnswer3() {
        return RowAnswer3;
    }

    public void setRowAnswer3(String rowAnswer3) {
        RowAnswer3 = rowAnswer3;
    }
}
