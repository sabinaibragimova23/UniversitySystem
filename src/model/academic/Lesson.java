package model.academic;

import model.users.Teacher;
import model .enums.LessonType;

import java.io.Serializable;
import java.util.Objects;

public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lessonId;
    private LessonType type;
    private String topic;
    private String room;
    private int duration;
    private Teacher teacher;

    public Lesson(String lessonId,
                  LessonType type,
                  String topic,
                  String room,
                  int duration,
                  Teacher teacher) {

        this.lessonId = lessonId;
        this.type = type;
        this.topic = topic;
        this.room = room;
        this.duration = duration;
        this.teacher = teacher;
    }

    public String getLessonId() {
        return lessonId;
    }

    public LessonType getType() {
        return type;
    }

    public String getTopic() {
        return topic;
    }

    public String getRoom() {
        return room;
    }

    public int getDuration() {
        return duration;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {

        return "Lesson[type="
                + type
                + ", topic="
                + topic
                + ", room="
                + room
                + ", duration="
                + duration
                + "min, teacher="
                + teacher
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Lesson))
            return false;
        Lesson l = (Lesson) o;
        return lessonId.equals(l.lessonId);
    }
}