package ru.gross.parksharing.ui.login_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import ru.gross.parksharing.databinding.ActivityLoginBinding
import ru.gross.parksharing.db.ParkDatabase
import ru.gross.parksharing.ui.waitotp_screen.WaitOtpActivity


class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.licenceAgreementText.movementMethod = LinkMovementMethod.getInstance()

        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("+7 ([000])[000]-[00]-[00]#[900]")

        val listener = MaskedTextChangedListener(
            "+7 ([000])[000]-[00]-[00]",
            affineFormats,
            AffinityCalculationStrategy.PREFIX,
            true,
            binding.phoneNumber,
            null,
            null
        )

        binding.phoneNumber.addTextChangedListener(listener)
        binding.phoneNumber.onFocusChangeListener = listener

        binding.reserveBtn.setOnClickListener {
            if (binding.licenceAgreement.isChecked) {
                var filtered = " -()"
                var phone = binding.phoneNumber.text.toString().filterNot { filtered.indexOf(it) >=0 }
                val intent = Intent(this, WaitOtpActivity::class.java)
                intent.putExtra("PHONE_NUMBER",phone)
                startActivity(intent)
                finish()
            }
        }

    }
}