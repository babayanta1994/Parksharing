package ru.gross.parksharing.ui.my_booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gross.parksharing.databinding.FragmentMyBookingBinding

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

        val textView: TextView = binding.textGallery
        myBookingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}