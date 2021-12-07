package ru.gross.parksharing.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import ru.gross.parksharing.R
import ru.gross.parksharing.db.ParkDatabase
import ru.gross.parksharing.ui.general_screen.GeneralActivity
import ru.gross.parksharing.ui.login_screen.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Handler(Looper.myLooper()!!).postDelayed({
            val database = ParkDatabase.getDatabase(this)
            database.userDao().getUser().observe(this, {
                if (it != null) {
                    Log.e(this.localClassName, ">>>> $it")
                    val intent = Intent(this, GeneralActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            })

            finish()
        }, 1500) // 1500 is the delayed time in milliseconds.
    }
}