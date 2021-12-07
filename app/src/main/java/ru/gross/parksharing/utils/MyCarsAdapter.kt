package ru.gross.parksharing.utils

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import ru.gross.parksharing.api.response.MyCar
import ru.gross.parksharing.databinding.CarItemBinding





class MyCarsAdapter(
    var carList: List<MyCar>,
    var curentCarNum: String,
) : RecyclerView.Adapter<MyCarsAdapter.ViewHolder>() {
    var handler: Handler? = Handler(Looper.myLooper()!!)
    inner class ViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(carList[position]) {
                binding.nameCar.text = this.note
                binding.numberCar.text = this.car_regplate
                if (curentCarNum == this.car_regplate) {
                    binding.iconInfo.visibility = View.VISIBLE
                } else {
                    binding.iconInfo.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }
//    fun undoView(position: Int) {
//        handler?.postDelayed({
//            notifyItemChanged(position)
//        }, 500)
//    }


}

