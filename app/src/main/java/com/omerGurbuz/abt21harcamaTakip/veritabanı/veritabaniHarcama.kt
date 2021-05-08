package com.omerGurbuz.abt21harcamaTakip.veritabanı

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = arrayOf(veritabaniYapisi::class),version = 1)
abstract class veritabaniHarcama : RoomDatabase() {

    abstract fun veritabaniDAO() : veritabaniDAO

    companion object{
        private var instance: veritabaniHarcama? = null

        fun veritabaniniAl(context: Context): veritabaniHarcama {
            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    veritabaniHarcama::class.java,
                    "expense_table"
                ).allowMainThreadQueries().build()
            }
            return instance as veritabaniHarcama
        }
    }

}