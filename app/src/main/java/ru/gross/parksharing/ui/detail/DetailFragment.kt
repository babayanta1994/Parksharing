package ru.gross.parksharing.ui.detail

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
import coil.load
import ru.gross.parksharing.GeneralActivity
import ru.gross.parksharing.R
import ru.gross.parksharing.api.response.Gate
import ru.gross.parksharing.databinding.FragmentDetailBinding
import java.lang.Exception

class DetailFragment : Fragment() {

    private lateinit var detailModel: DetailModel
    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailModel =
            ViewModelProvider(this).get(DetailModel::class.java)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as GeneralActivity).isCarFront.value=false
        println("carpark_id " + arguments?.getInt("carpark_id", 0))
        detailModel.carpark_id = arguments?.getInt("carpark_id", 0)
        detailModel.getDetailCarpark()
        detailModel.parkDetail.observe(viewLifecycleOwner, Observer {
            binding.tvAddress.text = it.address
            binding.tvParkname.text = it.name
            Log.e("DetailFragment",">>>>> ${it.photoUrl}")
            binding.imageView.load(it.photoUrl){
                error(R.drawable.image_parking)
            }
            if (it.fee.isNotEmpty()) {
                try {
                    binding.title1.text = it.fee[0].name
                    binding.title2.text = it.fee[1].name
                    binding.title3.text = it.fee[2].name
                    binding.price1.text = "${it.fee[0].price} р"
                    binding.price2.text = "${it.fee[1].price} р"
                    binding.price3.text = "${it.fee[2].price} р"
                } catch (e: Exception) {
                }
            }


        })
        binding.bookingBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("parkDetail",detailModel.parkDetail.value)
            Navigation.findNavController(it).navigate(R.id.nav_booking,bundle)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}