package com.aldin.ambetest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities =  [PersonEntity::class], version = 1)
abstract class PersonData : RoomDatabase(){
    abstract fun personDao() : PersonDao


    companion object {
        private const val DATABASE_NAME = "ambetest"

        @Volatile
        private var INSTANCE: PersonData? = null

        @Synchronized
        fun getInstance(context: Context): PersonData {
            if(INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    PersonData::class.java,
                    DATABASE_NAME
                    ).fallbackToDestructiveMigration()
                    .build()

            return INSTANCE!!

            }
        }
    }

