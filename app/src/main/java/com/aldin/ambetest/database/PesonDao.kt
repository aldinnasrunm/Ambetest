package com.aldin.ambetest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personEntity: PersonEntity)

    @Query("SELECT * FROM person_data")
    fun getAllMyEntities(): List<PersonEntity>


    @Query("DELETE FROM person_data WHERE id = :id")
    fun deleteById(id: Int)
}


@Dao
interface DrugDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drugEntity: DrugEntity)

    @Query("SELECT * FROM drug_info")
    fun getAllMyEntities(): List<DrugEntity>

    @Query("DELETE FROM drug_info WHERE id = :id")
    fun deleteById(id: Int)
}

