package com.sonyl.notetaker.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EncryptedNoteRepository(context: Context) : NoteRepository {

    private val gson = Gson()

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "secure_notes",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun loadNotes(): MutableList<Note> {
        val json = prefs.getString(KEY_NOTES, "[]") ?: "[]"
        val type = object : TypeToken<MutableList<Note>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveNotes(notes: List<Note>) {
        prefs.edit().putString(KEY_NOTES, gson.toJson(notes)).apply()
    }

    override fun getNotes(): List<Note> =
        loadNotes().sortedBy { it.created }

    override fun addNote(text: String): Note {
        val notes = loadNotes()
        val note = Note(text = text)
        notes.add(note)
        saveNotes(notes)
        return note
    }

    override fun deleteNote(id: String): Boolean {
        val notes = loadNotes()
        val removed = notes.removeIf { it.id == id }
        if (removed) saveNotes(notes)
        return removed
    }

    override fun searchNotes(query: String): List<Note> {
        val q = query.lowercase()
        return loadNotes()
            .filter { q in it.text.lowercase() }
            .sortedBy { it.created }
    }

    override fun exportJson(): String =
        gson.toJson(loadNotes())

    companion object {
        private const val KEY_NOTES = "notes_list"
    }
}
