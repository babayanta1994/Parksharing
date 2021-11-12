package ru.gross.parksharing.ui.promocodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gross.parksharing.databinding.FragmentPromocodesBinding

class PromocodesFragment : Fragment() {

    private lateinit var promocodesViewModel: PromocodesViewModel
    private var _binding: FragmentPromocodesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        promocodesViewModel =
            ViewModelProvider(this).get(PromocodesViewModel::class.java)

        _binding = FragmentPromocodesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        promocodesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}