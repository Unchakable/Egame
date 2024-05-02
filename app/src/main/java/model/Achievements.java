package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "achievements")
public class Achievements {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false, columnName = "name")
    private String name;
    @DatabaseField(canBeNull = false, columnName = "is_unlocked")
    boolean isUnlocked;

    public Achievements() {
    }
    public Achievements(String name, boolean isUnlocked) {
        this.name = name;
        this.isUnlocked = isUnlocked;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }
}
