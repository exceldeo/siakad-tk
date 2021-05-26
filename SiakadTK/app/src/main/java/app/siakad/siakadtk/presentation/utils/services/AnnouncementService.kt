package app.siakad.siakadtk.presentation.utils.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import app.siakad.siakadtk.domain.models.PenggunaModel
import app.siakad.siakadtk.domain.models.PengumumanModel
import app.siakad.siakadtk.domain.repositories.AnnouncementRepository
import app.siakad.siakadtk.domain.repositories.UserRepository
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.domain.utils.helpers.container.ModelState
import app.siakad.siakadtk.domain.utils.listeners.announcement.AnnouncementServiceListener
import app.siakad.siakadtk.infrastructure.viewmodels.screens.announcement.AnnouncementViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnnouncementService : Service(), AnnouncementServiceListener {

    private var serviceScope = CoroutineScope(Job() + Dispatchers.Main)
    private val announcementRepository = AnnouncementRepository()
    private val userRepository = UserRepository()

    private var idNotification = 0
    private val notifIdList = mutableSetOf<String>()
    private val notifList = arrayListOf<String>()

    private val CHANNEL_ID = "notif_channel_announcement"
    private val CHANNEL_NAME = "Announcement Notifiaction Channel"
    private val MAX_NOTIFICATION = 5
    private val GROUP_KEY_ANNOUNCE = "group_key_announce"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            announcementRepository.initGetAnnouncementListListener(this@AnnouncementService)
            userRepository.getUserById(this@AnnouncementService)
        }

        return START_STICKY
    }

    override fun onDestroy() {
        announcementRepository.removeListener()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun sendAnnouncementNotification(pengumuman: ModelContainer<PengumumanModel>) {
        if (pengumuman.status == ModelState.SUCCESS) {
            if (pengumuman.data != null) {
                val mNotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val mBuilder: NotificationCompat.Builder

                if (!notifIdList.contains(pengumuman.data?.pengumumanId!!)) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    val pendingIntent =
                        PendingIntent.getActivity(
                            this,
                            911,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )

                    idNotification++
                    notifIdList.add(pengumuman.data?.pengumumanId!!)
                    notifList.add(pengumuman.data?.judul!!)

                    if (idNotification <= MAX_NOTIFICATION) {
                        mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notification_siakad)
                            .setLargeIcon(
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.ic_notification_siakad
                                )
                            )
                            .setContentTitle(pengumuman.data?.judul)
                            .setContentText(pengumuman.data?.keterangan)
                            .setSubText(pengumuman.data?.tanggal)
                            .setGroup(GROUP_KEY_ANNOUNCE)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val channel = NotificationChannel(
                                CHANNEL_ID,
                                CHANNEL_NAME,
                                NotificationManager.IMPORTANCE_DEFAULT
                            )
                            mBuilder.setChannelId(CHANNEL_ID)
                            mNotificationManager.createNotificationChannel(channel)
                        }

                        val notification = mBuilder.build()
                        mNotificationManager.notify(idNotification, notification)
                    } else {
                        idNotification = 0
                        notifIdList.clear()
//                        val inboxStyle = NotificationCompat.InboxStyle()
//                            .addLine("Pengumuman baru: " + notifList[idNotification - 1])
//                            .addLine("Pengumuman baru: " + notifList[idNotification - 2])
//                            .setBigContentTitle("Terdapat $idNotification pengumuman")
//                            .setSummaryText("Siakad TK")
//                        mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//                            .setContentTitle("Terdapat $idNotification pengumuman")
//                            .setContentText("Siakad TK")
//                            .setSmallIcon(R.drawable.ic_notification_siakad)
//                            .setStyle(inboxStyle)
//                            .setContentIntent(pendingIntent)
//                            .setGroup(GROUP_KEY_ANNOUNCE)
//                            .setGroupSummary(true)
//                            .setAutoCancel(true)
                    }
                }
            }
        }
    }

    override fun setUser(pengguna: ModelContainer<PenggunaModel>) {
        if (pengguna.status == ModelState.SUCCESS) {
            if (pengguna.data != null) {
                serviceScope.launch {
                    announcementRepository.initGetAnnouncementListListenerByUserId(this@AnnouncementService)
                    announcementRepository.initGetAnnouncementListListenerByClass(
                        this@AnnouncementService,
                        pengguna.data?.detailPengguna?.kelasId!!
                    )
                }
            }
        }
    }
}