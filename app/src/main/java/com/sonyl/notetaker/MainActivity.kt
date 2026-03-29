package com.sonyl.notetaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.fragment.app.FragmentActivity
import com.sonyl.notetaker.data.NoteViewModel
import com.sonyl.notetaker.ui.*
import com.sonyl.notetaker.ui.theme.NoteAppTheme

class MainActivity : FragmentActivity() {

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                NoteApp(
                    viewModel = viewModel,
                    onExport = { exportNotes() },
                    onBiometricRequest = { onSuccess, onError ->
                        BiometricHelper.authenticate(this, onSuccess, onError)
                    },
                    canUseBiometric = BiometricHelper.canAuthenticate(this)
                )
            }
        }
    }

    private fun exportNotes() {
        val json = viewModel.exportJson()
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_TEXT, json)
            putExtra(Intent.EXTRA_SUBJECT, "Notes Export")
        }
        startActivity(Intent.createChooser(intent, "Export Notes"))
    }
}

@Composable
fun NoteApp(
    viewModel: NoteViewModel,
    onExport: () -> Unit,
    onBiometricRequest: (onSuccess: () -> Unit, onError: (String) -> Unit) -> Unit,
    canUseBiometric: Boolean
) {
    var isUnlocked by remember { mutableStateOf(!canUseBiometric) }
    var showAddNote by remember { mutableStateOf(false) }
    var biometricError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(canUseBiometric) {
        if (canUseBiometric) {
            onBiometricRequest(
                { isUnlocked = true },
                { err -> biometricError = err }
            )
        }
    }

    when {
        !isUnlocked -> BiometricLockScreen(
            onUnlockClick = {
                biometricError = null
                onBiometricRequest(
                    { isUnlocked = true },
                    { err -> biometricError = err }
                )
            },
            errorMessage = biometricError
        )
        showAddNote -> AddNoteScreen(
            viewModel = viewModel,
            onBack = { showAddNote = false }
        )
        else -> NoteListScreen(
            viewModel = viewModel,
            onAddNote = { showAddNote = true }
        )
    }
}
