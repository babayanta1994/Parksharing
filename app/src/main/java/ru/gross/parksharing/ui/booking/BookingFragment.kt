package ru.gross.parksharing.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
import ru.gross.parksharing.GeneralActivity
import ru.gross.parksharing.R
import ru.gross.parksharing.api.response.ParkDetail
import ru.gross.parksharing.databinding.FragmentBookingBinding
import java.lang.Exception

class BookingFragment : Fragment() {

    private lateinit var bookingModel: BookingModel
    private var _binding: FragmentBookingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookingModel =
            ViewModelProvider(this).get(BookingModel::class.java)

        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as GeneralActivity).isCarFront.value=false

        var parkDetail: ParkDetail? = arguments?.getSerializable("parkDetail") as ParkDetail
        if(parkDetail!=null){
            println("parkDetail " + parkDetail)

            val imageList = ArrayList<SlideModel>()
            for (gates in parkDetail.gates){
                imageList.add(SlideModel(gates.photo_url, gates.label,
                    ScaleTypes.CENTER_CROP))
            }
            binding.imageSlider.setImageList(imageList)
        }
        binding.dateLayouts.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_datemodal)

        }

        binding.reserveBtn.setOnClickListener {
            Snackbar.make(it, "reserveBtn action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }







//        binding.bookingBtn.setOnClickListener {
////            Navigation.findNavController(it).navigate(R.id.);
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}