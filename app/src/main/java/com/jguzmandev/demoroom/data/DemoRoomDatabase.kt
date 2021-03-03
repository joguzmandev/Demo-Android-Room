package com.jguzmandev.demoroom.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jguzmandev.demoroom.entity.Course
import com.jguzmandev.demoroom.entity.Professor

@Database(
    entities = [Professor::class, Course::class],
    version = 2,
)
abstract class DemoRoomDatabase : RoomDatabase() {

    abstract fun professorDao(): ProfessorDAO

    abstract fun courseDao(): CourseDAO


}