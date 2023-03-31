package com.cst438.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://registerf-cst438.herokuapp.com/"})
public class StudentController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    GradebookService gradebookService;


    @PostMapping("/student")
    @Transactional
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) {
        Student other = studentRepository.findByEmail(studentDTO.email);

        if (other == null) {
            Student student = new Student();
            student.setName(studentDTO.name);
            student.setEmail(studentDTO.email);
            student.setStatusCode(studentDTO.statusCode);
            student.setStatus(studentDTO.status);

            Student savedStudent = studentRepository.save(student);

            StudentDTO result = createStudentDTO(savedStudent);

            return result;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student email already exists in the system: " + studentDTO.email);
        }
    }

    @PutMapping("/student/hold/{email}")
    @Transactional
    public StudentDTO putStudentOnHold(@PathVariable("email") String email) {
        Student student = studentRepository.findByEmail(email);

        if (student != null) {
            student.setStatus("HOLD");
            Student savedStudent = studentRepository.save(student);
            StudentDTO result = createStudentDTO(savedStudent);

            return result;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student email does not exist in the system: " + email);
        }
    }

    @PutMapping("/student/release/{email}")
    @Transactional
    public StudentDTO releaseStudentHold(@PathVariable("email") String email) {
        Student student = studentRepository.findByEmail(email);

        if (student != null) {
            student.setStatus("OK");
            Student savedStudent = studentRepository.save(student);

            return createStudentDTO(savedStudent);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student email does not exist in the system: " + email);
        }
    }
    //convert student object to studentDTO
    private StudentDTO createStudentDTO(Student s) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.statusCode = s.getStatusCode();
        studentDTO.status = s.getStatus();
        studentDTO.email = s.getEmail();
        studentDTO.name = s.getName();
        studentDTO.student_id = s.getStudent_id();
        return studentDTO;
    }

}