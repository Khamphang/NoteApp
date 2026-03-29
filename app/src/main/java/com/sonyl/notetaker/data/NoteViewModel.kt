package com.sonyl.notetaker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: NoteRepository = EncryptedNoteRepository(app)

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            val q = _searchQuery.value
            _notes.value = if (q.isBlank()) repo.getNotes()
                           else repo.searchNotes(q)
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch {
            repo.addNote(text)
            refresh()
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            repo.deleteNote(id)
            refresh()
        }
    }

    fun setSearchQuery(q: String) {
        _searchQuery.value = q
        refresh()
    }

    fun exportJson(): String = repo.exportJson()
}
