package com.aldin.ambetest.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



data class PersonDataCarry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "gender") val gender: String,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(address)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonDataCarry> {
        override fun createFromParcel(parcel: Parcel): PersonDataCarry {
            return PersonDataCarry(parcel)
        }

        override fun newArray(size: Int): Array<PersonDataCarry?> {
            return arrayOfNulls(size)
        }
    }
}
