package com.jguzmandev.demoroom.data

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DemoRoomBuilder {
    private var INSTANCE: DemoRoomDatabase? = null

    fun getInstance(context: Context): DemoRoomDatabase =
        INSTANCE ?: {
            INSTANCE =
                Room.databaseBuilder(context, DemoRoomDatabase::class.java, "demo_room_app")
                    .addMigrations(MIGRATION_1_TO_2)
                    .build()
            INSTANCE
        }.invoke()!!

    private val MIGRATION_1_TO_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE Courses(" +
                        "Id INTEGER PRIMARY KEY NOT NULL," +
                        "Name TEXT NOT NULL," +
                        "Session TEXT NOT NULL," +
                        "Quorum INTEGER NOT NULL," +
                        "professorID INTEGER NOT NULL," +
                        "foreign key (professorID) references Professors (Id) ON DELETE CASCADE)"
            )
        }

    }
}