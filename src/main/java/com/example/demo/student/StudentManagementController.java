package com.example.demo.student;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINEE')")
    public List<Student> listAll(){
        System.out.println("Listing the student");
        return STUDENTS;
    }

    @PutMapping("register")
    @PreAuthorize("hasAuthority('student:create')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println("Registering the student");
        System.out.println(student);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('student:create')")
    public void deleteStudent(@PathVariable("id") int studentId){
        System.out.println("Deleting the student");
        System.out.println(studentId);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAuthority('student:create')")
    public void updateStudent( @PathVariable("id") int studentId, @RequestBody Student student){
        System.out.println("Updating the student");
        System.out.println("ID : " + studentId);
        System.out.println("Student : " + student);
    }

}
