package com.example.assignment3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class TransferActivity: AppCompatActivity () {
    private var notificationId: Int = 123
    private val channelId = "App_Channel.testNotification"
    private val description = "Trying to test different notification types"
    private var transferAmount: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        createNotificationChannel()

        transferAmount = intent.getStringExtra("TransferAmount")

        val display: String = "Transfer Amount Initiated: $$transferAmount"
        val amountView: TextView = findViewById(R.id.editTransferAmount)
        amountView.text = display

        val transferButton: Button = findViewById(R.id.btnConfirm)

        transferButton.setOnClickListener{
            val tAmount: String = transferAmount.toString()
            Toast.makeText(this, "Transferred Money Successfully", Toast.LENGTH_SHORT).show()
            basicNotification(tAmount)
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "lakehead_transfer_notification"
            val descriptionText = description
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun basicNotification(amount: String) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Money Transfer Successful")
            .setContentText("$$amount deducted from your Account")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

}