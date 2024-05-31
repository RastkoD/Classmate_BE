package com.iktpreobuka.classmate.entities.dto;

public class TeacherDTO extends UserAccountDTO {
    
    private String consultations;

    public TeacherDTO() {
        super();
    }

    public TeacherDTO(String consultations) {
        super();
        this.consultations = consultations;
    }

    public String getConsultations() {
        return consultations;
    }

    public void setConsultations(String consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        return "TeacherDTO [consultations=" + consultations + "]";
    }
}
