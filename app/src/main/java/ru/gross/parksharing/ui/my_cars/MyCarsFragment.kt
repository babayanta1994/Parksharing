package ru.gross.parksharing.ui.my_cars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gross.parksharing.databinding.FragmentMyCarsBinding

class MyCarsFragment : Fragment() {

    private lateinit var myCarsViewModel: MyCarsViewModel
    private var _binding: FragmentMyCarsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myCarsViewModel =
            ViewModelProvider(this).get(MyCarsViewModel::class.java)

        _binding = FragmentMyCarsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        myCarsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}