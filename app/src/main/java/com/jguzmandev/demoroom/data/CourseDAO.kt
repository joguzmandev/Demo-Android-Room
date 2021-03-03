package com.jguzmandev.demoroom.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jguzmandev.demoroom.entity.Course

@Dao
interface CourseDAO {

    @Insert
    fun add(course: Course)

    @Query("SELECT * FROM Courses WHERE professorID = :professorId")
    fun getAllCoursesByProfessorId(professorId:Int):List<Course>

    @Query("SELECT * FROM Courses")
    fun getAllCourses():List<Course>
}