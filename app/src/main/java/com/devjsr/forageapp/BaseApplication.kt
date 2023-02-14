package com.devjsr.forageapp

import android.app.Application
import com.devjsr.forageapp.data.ForageDatabase

class BaseApplication : Application() {
    val database : ForageDatabase by lazy { ForageDatabase.getDatabase( this) }
}