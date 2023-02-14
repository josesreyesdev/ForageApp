package com.devjsr.forageapp.ui.viewmodel

import androidx.lifecycle.*
import com.devjsr.forageapp.data.ForageableDao
import com.devjsr.forageapp.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForageableViewModel( private val forageableDao: ForageableDao): ViewModel() {

    val allForageables : LiveData<List<Forageable>> = forageableDao.getForageables().asLiveData()

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
        viewModelScope.launch {
            forageableDao.insertForageable(forageable)
        }
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
            forageableDao.updateForageable(forageable)
        }
    }

    fun deleteForageable( forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.deleteForageable(forageable)
        }
    }

    fun isvalidEntry( name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }

    fun retrievedForageable(id: Long): LiveData<Forageable> = forageableDao.getForageable(id).asLiveData()
}

class ForageableViewModelFactory(private val forageableDao: ForageableDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //return super.create(modelClass)
        if (modelClass.isAssignableFrom(ForageableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForageableViewModel(forageableDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Exception")
    }
}