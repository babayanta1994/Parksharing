package ru.gross.parksharing.ui.waitotp_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gross.parksharing.databinding.ActivityWaitOtpBinding
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ru.gross.parksharing.db.ParkDatabase
import ru.gross.parksharing.ui.general_screen.GeneralActivity


class WaitOtpActivity : AppCompatActivity() {

    private var _binding: ActivityWaitOtpBinding? = null
    private val binding get() = _binding!!

    private lateinit var waitOtpModel: WaitOtpModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waitOtpModel =
            ViewModelProvider(this).get(WaitOtpModel::class.java)

        _binding = ActivityWaitOtpBinding.inflate(layoutInflater)
        if (waitOtpModel.phone == null) {
            waitOtpModel.phone = intent.extras?.getString("PHONE_NUMBER")
        }
        if (waitOtpModel.uniqueID == null) {
            waitOtpModel.updateUUID(this)
        }


        val view = binding.root
        setContentView(view)


        Log.e(this.localClassName, ">>>>> phone ${waitOtpModel.phone}")
        Log.e(this.localClassName, ">>>>> uniqueID ${waitOtpModel.uniqueID}")


        waitOtpModel._sec.observe(this, {
            binding.tvSeconds.setText(if (it > 9) "0:$it" else "0:0$it")
        })
        waitOtpModel.llwait.observe(this, {
            binding.llwait.visibility = it
        })
        waitOtpModel.llretry.observe(this, {
            binding.llretry.visibility = it
        })


        waitOtpModel.isProgress.observe(this, {
            if (it) {
                binding.llwaiting.visibility = View.VISIBLE
                binding.llmain.visibility = View.GONE
            } else {
                binding.llwaiting.visibility = View.GONE
                binding.llmain.visibility = View.VISIBLE
            }
        })

        waitOtpModel.isConfirmed.observe(this, {
            if (it) {
                waitOtpModel.getUser().observe(this, { user ->
                    Log.e(this.localClassName, ">>>> $it")
                    if (user != null) {
                        val database = ParkDatabase.getDatabase(this)
                        database.userDao().getUser().observe(this, {
                            val intent = Intent(this, GeneralActivity::class.java)
                            startActivity(intent)
                            finish()
                        })
                    }
                })
            }
        })


        binding.tvRetry.setOnClickListener {
            waitOtpModel.getLoginInfo()
        }

        waitOtpModel.getLoginInfo()

        binding.confirmBtn.setOnClickListener {
            if (binding.otpCode.text.toString().isNotEmpty()) {
                waitOtpModel.confirmReg(binding.otpCode.text.toString().toInt())
            }
        }


    }


}