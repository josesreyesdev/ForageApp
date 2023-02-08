package com.devjsr.forageapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devjsr.forageapp.data.ForageableDao
import com.devjsr.forageapp.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForageableViewModel( private val forageableDao: ForageableDao): ViewModel() {



    fun addForageable(
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )
    }

    fun updateForageable(
        id: Long,
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            id = id,
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )

        viewModelScope.launch( Dispatchers.IO) {

        }
    }

    fun deleteForageable( forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun isvalidEntry( name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}