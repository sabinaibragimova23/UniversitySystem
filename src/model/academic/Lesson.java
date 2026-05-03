package model.academic;

import model.enums.LessonType;
import model.users.Teacher;
import java.util.Date;
import java.util.Objects;

public class Lesson {

    private LessonType type;
    private String topic;
    private String room;
    private int duration;
    private String lessonId;
    private Teacher teacher;

    public Lesson(String lessonId, LessonType type, String topic,
                  String room, int duration, Teacher teacher) {
        this.lessonId = lessonId;
        this.type     = type;
        this.topic    = topic;
        this.room     = room;
        this.duration = duration;
        this.teacher  = teacher;
    }

    public String getInfo() {
        return getClass().getSimpleName()
                + "[type=" + type
                + ", topic=" + topic
                + ", room=" + room
                + ", duration=" + duration + "min"
                + ", teacher=" + teacher + "]";
    }

    public LessonType getType() { return type; }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        return Objects.equals(lessonId, ((Lesson) o).lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }

    public String getLessonId()  { return lessonId; }
    public String getTopic()     { return topic; }
    public String getRoom()      { return room; }
    public int getDuration()     { return duration; }
    public Teacher getTeacher()  { return teacher; }
}
