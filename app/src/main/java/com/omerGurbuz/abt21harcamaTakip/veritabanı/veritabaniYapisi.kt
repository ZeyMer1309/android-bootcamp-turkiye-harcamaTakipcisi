package com.omerGurbuz.abt21harcamaTakip.veritabanı

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class veritabaniYapisi (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val aciklama: String,
    val tutar: Int,
    val harcamaTipi: Int,
    val paraBirimi: Int
    )
{}