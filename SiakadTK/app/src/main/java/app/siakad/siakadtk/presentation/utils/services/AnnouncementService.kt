package app.siakad.siakadtk.presentation.utils.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.repositories.AnnouncementRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.listeners.announcement.AnnouncementServiceListener
import app.siakad.siakadtk.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementService : Service(), AnnouncementServiceListener {

    private var serviceScope = CoroutineScope(Job() + Dispatchers.Main)
    private val announcementRepository = AnnouncementRepository()
//    private val preferences = PreferenceManager.getDefaultSharedPreferences(this)

    companion object {
        const val SP_NAME = "Announcement_Service_SP"
        const val SP_NOTIF_KEY = "Notification_States"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            announcementRepository.initServiceChildEventListener(this@AnnouncementService)
        }

        return START_STICKY
    }

    override fun onDestroy() {
        announcementRepository.removeListener()
//        if (preferences.getBoolean(SP_NOTIF_KEY, false)) {
//            val editor = preferences.edit()
//            editor.putBoolean(SP_NOTIF_KEY, false)
//            editor.apply()
//        }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun sendAnnouncementNotification(pengumuman: ModelContainer<PengumumanModel>) {
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, "channel_nyoba_nyoba")
            .setSmallIcon(R.drawable.ic_baseline_notifications_48)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_notifications_48))
            .setContentTitle(pengumuman.data?.judul)
            .setContentText(pengumuman.data?.keterangan)
            .setSubText(pengumuman.data?.tanggal)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("channel_nyoba_nyoba", "Siakad TK Channel", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Siakad TK Channel"
            mBuilder.setChannelId("channel_nyoba_nyoba")
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        mNotificationManager.notify(1, notification)
    }
}