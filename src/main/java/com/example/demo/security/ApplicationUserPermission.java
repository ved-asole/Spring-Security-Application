package com.example.demo.security;

public enum ApplicationUserPermission {

    STUDENT_CREATE("student:create"),
    STUDENT_READ("student:read"),
    COURSE_CREATE("course:create"),
    COURSE_READ("course:read");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
