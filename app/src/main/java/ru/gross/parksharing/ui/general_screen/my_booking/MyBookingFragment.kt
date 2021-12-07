package ru.gross.parksharing.ui.general_screen.my_booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import ru.gross.parksharing.R
import ru.gross.parksharing.databinding.FragmentMyBookingBinding
import ru.gross.parksharing.ui.general_screen.GeneralActivity

class MyBookingFragment : Fragment() {

    private lateinit var myBookingViewModel: MyBookingViewModel
    private var _binding: FragmentMyBookingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myBookingViewModel =
            ViewModelProvider(this).get(MyBookingViewModel::class.java)

        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        (activity as GeneralActivity).isCarFront.value = false

        binding.closemodal.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }







        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}