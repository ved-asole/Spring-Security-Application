package com.example.demo.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Thomas Elva Einstein"),
            new Student(2, "Stan Lee"),
            new Student(3, "Newton")
    );

    @GetMapping("all")
    public List<Student> listAll(){
        return STUDENTS;
    }

    @PutMapping("register")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping("delete/{id}")
    public void deleteStudent(@PathVariable("id") int studentId){
        System.out.println(studentId);
    }

    @PutMapping("update/{id}")
    public void updateStudent( @PathVariable("id") int studentId, @RequestBody Student student){
        System.out.println("ID : " + studentId);
        System.out.println("Student : " + student);
    }

}
