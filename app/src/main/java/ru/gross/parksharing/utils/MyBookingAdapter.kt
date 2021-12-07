package ru.gross.parksharing.utils

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import ru.gross.parksharing.api.response.MyBooking
import ru.gross.parksharing.api.response.MyCar
import ru.gross.parksharing.databinding.BookingItemBinding
import ru.gross.parksharing.databinding.CarItemBinding





class MyBookingAdapter(
    var carList: List<MyBooking>,
) : RecyclerView.Adapter<MyBookingAdapter.ViewHolder>() {
    var handler: Handler? = Handler(Looper.myLooper()!!)
    inner class ViewHolder(val binding: BookingItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(carList[position]) {
//                binding.tvAddress.text = this.

            }
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }


}

