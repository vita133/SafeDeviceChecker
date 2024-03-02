package com.example.safedevicechecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var deviceDB: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deviceDB = FirebaseDatabase.getInstance().getReference("devices")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment())
                .commit()

            Handler(Looper.getMainLooper()).postDelayed({
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SearchFragment())
                    .commit() }, 3000)
        }
    }
}