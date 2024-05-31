package com.iktpreobuka.classmate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.iktpreobuka.classmate.entities.ClassEntity;
import com.iktpreobuka.classmate.entities.StudentEntity;
import com.iktpreobuka.classmate.repositories.StudentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class StudentDaoImpl implements StudentDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<StudentEntity> getAllStudents() {
		return (List<StudentEntity>) studentRepository.findAll();
	}

	@Override
	public StudentEntity getStudentById(Long studentId) {
		return studentRepository.findById(studentId).orElse(null);
	}

	@Override
	@Transactional
    @Validated
	public StudentEntity createStudent(@Valid StudentEntity newStudent) {
		return studentRepository.save(newStudent);
	}

	@Override
	@Transactional
    @Validated
	public StudentEntity updateStudent(Long studentId, @Valid StudentEntity updatedStudent) {
		if(!studentRepository.existsById(studentId)) {
			return null;
		}
		
		StudentEntity existingStudent = studentRepository.findById(studentId).orElse(null);
		
		if (existingStudent != null) {
			updatedStudent.setVersion(existingStudent.getVersion());
	    }
		
		updatedStudent.setUserId(studentId);
		return studentRepository.save(updatedStudent);
	}

	@Override
	public void deleteStudent(Long studentId) {
		studentRepository.deleteById(studentId);
		
	}

	@Override
	public boolean existsById(Long studentId) {
		return studentRepository.existsById(studentId);
	}

	@Override
	public void save(StudentEntity student) {
		studentRepository.save(student);
		
	}

	@Override
	public List<StudentEntity> findByAttendingClass(ClassEntity clazz) {
		return studentRepository.findByAttendingClass(clazz);
	}
}
