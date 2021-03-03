package com.jguzmandev.demoroom.data

import androidx.room.ColumnInfo

data class ProfessorTuple(
    @ColumnInfo(name = "Id")
    var id: Int = 0,
    @ColumnInfo(name = "Name")
    var name: String = "",
){
    override fun toString(): String {
        return name
    }
}