package com.omerGurbuz.abt21harcamaTakip.veritabanı

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface veritabaniDAO {
    // Data Access Object

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(harcama : veritabaniYapisi)

    @Query("SELECT * FROM veritabaniYapisi")
    fun getAllHarcama() : List<veritabaniYapisi>

    @Delete
    fun deleteHarcama(expense: veritabaniYapisi)

    @Query("SELECT * FROM veritabaniYapisi WHERE id= :harcamaID")
    fun getHarcama(harcamaID : Int) : veritabaniYapisi

}