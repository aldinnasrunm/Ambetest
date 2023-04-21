package com.aldin.ambetest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "drug_info")
data class DrugEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "drug_name") val drug_name: String,
    @ColumnInfo(name = "frequency") val frequency: String,
    @ColumnInfo(name = "drug_type") val drug_type: String,
    @ColumnInfo(name = "dose") val dose: Int,
    @ColumnInfo(name = "amount") val amount: Int
    )
