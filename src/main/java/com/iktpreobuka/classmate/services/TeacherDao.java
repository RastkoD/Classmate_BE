package com.iktpreobuka.classmate.services;

import java.util.List;

import com.iktpreobuka.classmate.entities.TeacherEntity;

public interface TeacherDao {
    List<TeacherEntity> getAllTeachers();
    
    TeacherEntity getTeacherById(Long teacherId);
    
    TeacherEntity createTeacher(TeacherEntity newTeacher);
    
    TeacherEntity updateTeacher(Long teacherId, TeacherEntity updatedTeacher);
    
    void deleteTeacher(Long teacherId);

}

