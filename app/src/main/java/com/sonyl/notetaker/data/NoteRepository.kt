package com.sonyl.notetaker.data

interface NoteRepository {
    fun getNotes(): List<Note>
    fun addNote(text: String): Note
    fun deleteNote(id: String): Boolean
    fun searchNotes(query: String): List<Note>
    fun exportJson(): String
}
