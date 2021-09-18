package com.example.demo.security;

import com.google.common.collect.Sets;
import java.util.Set;
import static com.example.demo.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_CREATE, COURSE_READ, STUDENT_CREATE, STUDENT_READ));

    private Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions){
        this.permissions=permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
