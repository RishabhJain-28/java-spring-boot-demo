package com.rishabh.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Taken");
        }
        studentRepository.save(student);

    }

    public void deleteStudentById(Long id) {
        boolean idExists = studentRepository.existsById(id);

        if(idExists == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student with id " + id + " does not exists");
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
                .orElseThrow(
                        ()-> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "user with id " + id + " does not exist."
                        )
                );
        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)){
            student.setName(name);
        }
        if(email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)){
            studentRepository.findStudentByEmail(email).orElseThrow(
                    ()-> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "user with email:  " + email + " already exists."
                    )
            );
            student.setEmail(email);
        }
    }
}
