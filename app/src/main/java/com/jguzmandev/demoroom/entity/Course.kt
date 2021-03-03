package com.jguzmandev.demoroom.entity

import androidx.room.*
import com.jguzmandev.demoroom.entity.Professor

@Entity(
    tableName = "Courses", foreignKeys = [ForeignKey(
        entity = Professor::class,
        parentColumns = ["Id"],
        childColumns = ["professorID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.NO_ACTION
    )]
)
data class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var courseId: Int = 0,
    @ColumnInfo(name = "Name")
    var courseName: String = "",
    @ColumnInfo(name = "Session")
    var courseSession: String = "",
    @ColumnInfo(name = "Quorum")
    var courseQuorum: Int = 0,
    @ColumnInfo(name = "professorID")
    var professor: Int = 0

)