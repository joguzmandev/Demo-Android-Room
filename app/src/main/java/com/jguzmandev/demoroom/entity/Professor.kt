package com.jguzmandev.demoroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Professors")
data class Professor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var professorID: Int = 0,
    @ColumnInfo(name = "Name")
    var professorName: String = "",
    @ColumnInfo(name = "Dni")
    var professorDNI: String = ""
){
    override fun toString(): String {
        return professorName
    }
}