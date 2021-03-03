package com.jguzmandev.demoroom.data

import androidx.room.*
import com.jguzmandev.demoroom.entity.Professor

@Dao
interface ProfessorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(professor: Professor)

    @Query("SELECT * FROM professors")
    fun getAll():List<Professor>

    @Update
    fun updateProfessor(professor: Professor)

    @Delete
    fun deleteProfessor(professor: Professor)

    @Query("SELECT * FROM professors WHERE Id = :professorID")
    fun findProfessorById(professorID:Int): Professor?

    @Query("SELECT Id,Name FROM Professors")
    fun getAllProfessorNameAndIdList():List<ProfessorTuple>
}