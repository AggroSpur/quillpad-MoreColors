package org.qosp.notes.ui.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.qosp.notes.BuildConfig

class ReminderReceiver : BroadcastReceiver(), KoinComponent {
    val reminderManager: ReminderManager by inject()

    override fun onReceive(context: Context, intent: Intent?) = runBlocking {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> reminderManager.rescheduleAll()
            REMINDER_HAS_FIRED -> {
                val reminderId = intent.extras?.getLong("reminderId") ?: return@runBlocking
                val noteId = intent.extras?.getLong("noteId") ?: return@runBlocking
                reminderManager.sendNotification(reminderId, noteId)
            }
        }
    }

    companion object {
        const val REMINDER_HAS_FIRED = "${BuildConfig.APPLICATION_ID}.REMINDER_HAS_FIRED"
    }
}
