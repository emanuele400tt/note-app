package com.example.eznote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eznote.model.Note
import com.example.eznote.utils.NoteConverters


@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(NoteConverters::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}