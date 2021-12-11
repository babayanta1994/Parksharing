package ru.gross.parksharing.ui.general_screen.my_booking

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gross.parksharing.api.common.Common
import ru.gross.parksharing.api.request.GetBookingRequest
import ru.gross.parksharing.api.request.MyCarsRequest
import ru.gross.parksharing.api.response.MyBooking
import ru.gross.parksharing.api.response.MyCar
import ru.gross.parksharing.api.services.RetrofitServices
import ru.gross.parksharing.databinding.ActivityMyCarBinding
import ru.gross.parksharing.databinding.FragmentMyBookingBinding
import ru.gross.parksharing.db.ParkDatabase
import ru.gross.parksharing.db.entity.User
import ru.gross.parksharing.utils.MyBookingAdapter
import ru.gross.parksharing.utils.MyCarsAdapter

class MyBookingFragment : Fragment() {

    private lateinit var myBookingViewModel: MyBookingViewModel
    private var _binding: FragmentMyBookingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var mService: RetrofitServices
    private lateinit var rvAdapter: MyBookingAdapter

    private val _mybookings = MutableLiveData<MutableList<MyBooking>>().apply {
        value = mutableListOf()
    }

    val mybookings: LiveData<MutableList<MyBooking>> = _mybookings


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myBookingViewModel =
            ViewModelProvider(this).get(MyBookingViewModel::class.java)

        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        (activity as GeneralActivity).isCarFront.value = false



        mService = Common.retrofitService


        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.myBookingRv.layoutManager = layoutManager
        rvAdapter = MyBookingAdapter(mutableListOf())
        binding.myBookingRv.adapter = rvAdapter

        getMyBooking()


        mybookings.observe(viewLifecycleOwner, {
            Log.e(javaClass.name, ">>>>> " + it)
            rvAdapter.bookingList = it
            rvAdapter.notifyDataSetChanged()
        })

        binding.closemodal.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        return root
    }




    fun getUser(): LiveData<User> {
        val database = ParkDatabase.getDatabase(requireContext())
        return database.userDao().getUser()
    }

    private fun getMyBooking() {
        getUser().observe(this, {
            if (it != null) {
                Log.e(this.tag, ">>>> $it")
                var user = it as User

                Log.e(javaClass.name, ">>>>> getMyCars")
                mService.getCurrentBookings(GetBookingRequest(user.AppID, user.ID, user.Phone)).enqueue(object :
                    Callback<MutableList<MyBooking>> {
                    override fun onFailure(call: Call<MutableList<MyBooking>>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<MutableList<MyBooking>>,
                        response: Response<MutableList<MyBooking>>
                    ) {
                        val bookings = response.body() as MutableList<MyBooking>?
                        _mybookings.value = bookings
                    }
                })
            } else {
                Log.e(this.tag, ">>>> getuser is empty")
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}