package ru.gross.parksharing.utils

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import ru.gross.parksharing.api.response.MyBooking
import ru.gross.parksharing.databinding.BookingItemBinding
import java.text.SimpleDateFormat


class MyBookingAdapter(
    var bookingList: List<MyBooking>,
) : RecyclerView.Adapter<MyBookingAdapter.ViewHolder>() {
    var handler: Handler? = Handler(Looper.myLooper()!!)

    inner class ViewHolder(val binding: BookingItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    fun dateFromTo(from: String): String {
        val fromtime = SimpleDateFormat("yyyy-MM-ddTHH:mmZ").parse(from).time
        return SimpleDateFormat("EE, d MMMM\nHH:mm").format(fromtime)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(bookingList[position]) {
                binding.tvAddress.text = "${this.addr}; ${this.carpark_name}"
                binding.tvBeginEnd.text = "${dateFromTo(this.datetime_from)} - ${dateFromTo(this.datetime_till)}"
                binding.tvTariff.text = "${this.feeplan} тариф"
            }
        }
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }


}

