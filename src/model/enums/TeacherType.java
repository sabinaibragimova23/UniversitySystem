package model.enums;

public enum TeacherType {
    TUTOR, LECTOR, SENIOR_LECTOR, PROFESSOR;
    public boolean isAlwaysResearcher() { 
    	return this == PROFESSOR; 
    	}
}
