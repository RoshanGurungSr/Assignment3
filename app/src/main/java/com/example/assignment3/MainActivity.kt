package com.example.assignment3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkNotificationPermission()

        val inputAmount: EditText = findViewById(R.id.mainTransferAmount)
        val transferButton: Button = findViewById(R.id.btnTransfer)

        transferButton.setOnClickListener{
            val amount = inputAmount.text.toString()
            transferMoney(amount)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.share -> Toast.makeText(this, "No Transfer History",
                Toast.LENGTH_LONG).show()

            R.id.about_us -> aboutUs()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue with your work
            Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            // Permission is denied. Notify the user or disable notifications
            Toast.makeText(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun transferMoney(amount: String){
        val intent = Intent(this, TransferActivity::class.java)
        intent.putExtra("TransferAmount", amount)
        startActivity(intent)
    }

    private fun aboutUs() {
        val intent = Intent(this, AboutUs::class.java)
        startActivity(intent)
    }
}