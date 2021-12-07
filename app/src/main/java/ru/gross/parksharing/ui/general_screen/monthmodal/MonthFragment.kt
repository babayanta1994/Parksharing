package ru.gross.parksharing.ui.general_screen.monthmodal

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.gross.parksharing.ui.general_screen.GeneralActivity
import java.util.*

import android.widget.AdapterView.OnItemClickListener

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import ru.gross.parksharing.R
import ru.gross.parksharing.databinding.FragmentMonthBinding
import ru.gross.parksharing.utils.MonthAdapter
import java.lang.Exception


class MonthFragment : Fragment() {

    private lateinit var monthModel: MonthModel
    private var _binding: FragmentMonthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        val REQUEST_KEY = "month"
    }



    private var monthAdapter: MonthAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        monthModel =
            ViewModelProvider(this).get(MonthModel::class.java)

        _binding = FragmentMonthBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as GeneralActivity).isCarFront.value = false


        //calendar
        monthItemSetClick()
        val swipeDetector = GestureDetector(requireContext(), GestureListener())
        binding.calendarGrid.setOnTouchListener { v, event ->
            swipeDetector.onTouchEvent(event) && binding.calendarGrid.onTouchEvent(event)
        }

        monthAdapter = MonthAdapter(requireContext())
        binding.calendarGrid.setAdapter(monthAdapter)
        monthAdapter!!.title.observe(viewLifecycleOwner,{
            binding.yearCurrent.text = it
        })





        binding.selectDays.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
            Navigation.findNavController(it).navigate(R.id.nav_datemodal)
        }
        binding.closemodal.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }


//        binding.bookingBtn.setOnClickListener {
////            Navigation.findNavController(it).navigate(R.id.);
//        }
        return root
    }

    fun monthItemSetClick() {
        binding.calendarGrid.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->
                if (v.isEnabled) {
                    val d:Long = monthAdapter?.clickOnDate(position, v)!!
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("month", d)
                    findNavController().popBackStack()
                }
            }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun onNextMonth() {
        monthAdapter?.nextYear()
        binding.calendarSwitcher.setInAnimation(activity, R.anim.in_from_right)
        binding.calendarSwitcher.setOutAnimation(activity, R.anim.out_to_left)
        binding.calendarSwitcher.showNext()
    }

    fun onPreviousMonth() {
        monthAdapter?.prevYear()
        binding.calendarSwitcher.setInAnimation(activity, R.anim.in_from_left)
        binding.calendarSwitcher.setOutAnimation(activity, R.anim.out_to_right)
        binding.calendarSwitcher.showPrevious()
    }



    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {

                val diffY = e1?.y?.let { e2?.y?.minus(it) }
                val diffX = e1?.x?.let { e2?.x?.minus(it) }

                if (diffX != null && diffY != null) {

                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onPreviousMonth()
                            } else {
                                onNextMonth()
                            }
                            result = true
                        }
                    }
                } else {
                    result = false

                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }


    }

    fun Fragment.getNavigationResult(key: String = "result") =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

    fun Fragment.setNavigationResult(result: String, key: String = "result") {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }
}