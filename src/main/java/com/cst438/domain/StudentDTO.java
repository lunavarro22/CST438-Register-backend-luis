package com.cst438.domain;

import com.cst438.domain.ScheduleDTO.CourseDTO;

public class StudentDTO {
    public int student_id;
    public String name;
    public String email;
    public String status;
    public int statusCode;


    public String toString() {
        return "StudentDTO [student_id=" + student_id + ", name=" + name + ", email=" + email
                + ", status=" + status + ", status_code=" + statusCode + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentDTO other = (StudentDTO) obj;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (student_id != other.student_id)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (statusCode != other.statusCode)
            return false;
        return true;
    }

}
