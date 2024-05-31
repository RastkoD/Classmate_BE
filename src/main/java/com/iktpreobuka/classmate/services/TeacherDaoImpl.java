package com.iktpreobuka.classmate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.classmate.entities.TeacherEntity;
import com.iktpreobuka.classmate.repositories.TeacherRepository;

@Service
public class TeacherDaoImpl implements TeacherDao {
    
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<TeacherEntity> getAllTeachers() {
        return (List<TeacherEntity>) teacherRepository.findAll();
    }

    @Override
    public TeacherEntity getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    @Override
    public TeacherEntity createTeacher(TeacherEntity newTeacher) {
        return teacherRepository.save(newTeacher);
    }

    @Override
    public TeacherEntity updateTeacher(Long teacherId, TeacherEntity updatedTeacher) {
        if (!teacherRepository.existsById(teacherId)) {
            return null;
        }
        
        TeacherEntity existingTeacher = teacherRepository.findById(teacherId).orElse(null);
        
        if (existingTeacher != null) {
        	updatedTeacher.setVersion(existingTeacher.getVersion());
	    }

        updatedTeacher.setUserId(teacherId);
        return teacherRepository.save(updatedTeacher);
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}

