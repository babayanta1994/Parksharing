package ru.gross.parksharing.ui.general_screen.datemodal

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.gross.parksharing.ui.general_screen.GeneralActivity
import ru.gross.parksharing.utils.CalendarAdapter
import ru.gross.parksharing.databinding.FragmentDateBinding
import java.util.*

import android.widget.AdapterView.OnItemClickListener

import androidx.navigation.Navigation
import ru.gross.parksharing.R
import ru.gross.parksharing.components.DurationPicker
import android.view.GestureDetector
import android.view.MotionEvent

import android.view.GestureDetector.SimpleOnGestureListener
import androidx.navigation.fragment.findNavController
import java.lang.Exception
import java.lang.Math.abs
import java.text.SimpleDateFormat


class DateFragment : Fragment() {

    private lateinit var dateModel: DateModel
    private var _binding: FragmentDateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dateModel =
            ViewModelProvider(this).get(DateModel::class.java)

        _binding = FragmentDateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as GeneralActivity).isCarFront.value = false


        //Calendar
        calendarItemSetClick()
        val swipeDetector = GestureDetector(requireContext(), GestureListener())
        binding.calendarGrid.setOnTouchListener { v, event ->
            swipeDetector.onTouchEvent(event) && binding.calendarGrid.onTouchEvent(event)
        }

        if (dateModel.calendarAdapter == null) {
            dateModel.calendarAdapter = CalendarAdapter(requireContext())
            dateModel.calendarAdapter!!.pos_a = Date()
        }
        binding.calendarGrid.setAdapter(dateModel.calendarAdapter)
        dateModel.calendarAdapter!!.title.observe(viewLifecycleOwner, {
            binding.monthCurrent.text = it
        })
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>("month")
            ?.observe(viewLifecycleOwner) { result ->
                Log.e(this.tag, "Month>>>>>>>>>>> " + result)
                dateModel.calendarAdapter?.selectMonth(result)
            }


        //select_begin_time
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        dateModel.dateBegin.observe(viewLifecycleOwner, {
            val format = SimpleDateFormat("EE, d MMMM\nHH:mm")
            binding.beginDate.text = format.format(it.time)
        })



        mTimePicker = TimePickerDialog(
            requireContext(),
            { view, hourOfDay, minute ->
                binding.tvBeginTime.text =
                    String.format(
                        "%s : %s",
                        if (hourOfDay > 9) hourOfDay else "0" + hourOfDay,
                        if (minute > 9) minute else "0" + minute
                    )
            }, hour, minute, true
        )


        binding.tvBeginTime.text = String.format(
            "%s : %s",
            if (hour > 9) hour else "0" + hour,
            if (minute > 9) minute else "0" + minute
        )


        binding.selectMonth.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_monthmodal)
        }
        binding.closemodal.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.selectBeginDate.setOnClickListener {
            mTimePicker.show()
        }
        binding.duration.setOnClickListener {
            var durationPicker = DurationPicker(requireContext())
            durationPicker.setPositiveButton(
                "ОК"
            ) { _, _ ->
                binding.tvDuration.text =
                    durationPicker.picker.displayedValues[durationPicker.picker.value - 1]
                durationPicker.picker.removeAllViews()
            }
            durationPicker.show()
        }
        return root
    }


    fun calendarItemSetClick() {
        binding.calendarGrid.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->
                if (v.isEnabled) {
                    dateModel.calendarAdapter?.clickOnDate(position, v)
                    binding.tvDuration.text = dateModel.calendarAdapter?.duration
                    binding.duration.isClickable = dateModel.calendarAdapter?.durationClickable!!
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun onNextMonth() {
        dateModel.calendarAdapter?.nextMonth()
        binding.calendarSwitcher.setInAnimation(activity, R.anim.in_from_right)
        binding.calendarSwitcher.setOutAnimation(activity, R.anim.out_to_left)
        binding.calendarSwitcher.showNext()
    }

    fun onPreviousMonth() {
        dateModel.calendarAdapter?.prevMonth()
        binding.calendarSwitcher.setInAnimation(activity, R.anim.in_from_left)
        binding.calendarSwitcher.setOutAnimation(activity, R.anim.out_to_right)
        binding.calendarSwitcher.showPrevious()
    }


    inner class GestureListener : SimpleOnGestureListener() {
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

                    if (abs(diffX) > abs(diffY)) {
                        if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
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