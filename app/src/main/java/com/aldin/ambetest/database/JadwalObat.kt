package com.aldin.ambetest.database

class JadwalObat(
    var jadwal : Int

){

    fun getJadwal() : ArrayList<String>{
        var arrayJadwal = ArrayList<String>()

        if (jadwal == 1){
            arrayJadwal.add("08.00")
        }else if (jadwal == 2){
            arrayJadwal.add("08.00")
            arrayJadwal.add("12.00")
        }else if (jadwal == 3){
            arrayJadwal.add("08.00")
            arrayJadwal.add("12.00")
            arrayJadwal.add("16.00")
        }

        return arrayJadwal
    }

}
