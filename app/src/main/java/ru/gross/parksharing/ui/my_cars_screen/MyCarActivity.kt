package ru.gross.parksharing.ui.my_cars_screen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gross.parksharing.api.common.Common
import ru.gross.parksharing.api.request.AddCarRequest
import ru.gross.parksharing.api.request.DeleteCarRequest
import ru.gross.parksharing.api.response.MyCar
import ru.gross.parksharing.api.services.RetrofitServices
import ru.gross.parksharing.databinding.ActivityMyCarBinding
import ru.gross.parksharing.db.ParkDatabase
import ru.gross.parksharing.db.entity.User
import ru.gross.parksharing.api.request.MyCarsRequest
import ru.gross.parksharing.api.response.StatusCarResponse
import ru.gross.parksharing.utils.MyCarsAdapter

class MyCarActivity : AppCompatActivity() {


    private lateinit var mService: RetrofitServices
    private var _binding: ActivityMyCarBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: MyCarsAdapter

    private val _mycars = MutableLiveData<MutableList<MyCar>>().apply {
        value = mutableListOf()
    }

    val mycars: LiveData<MutableList<MyCar>> = _mycars

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mService = Common.retrofitService


        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.myCarsRv.layoutManager = layoutManager
        val sharedPref = getSharedPreferences("GET_PARK", Context.MODE_PRIVATE)
        val carnum: String = sharedPref.getString("CAR_NUM", "!!!")!!

        rvAdapter = MyCarsAdapter(mutableListOf(), carnum)
        binding.myCarsRv.adapter = rvAdapter


        binding.myCarsRv.addItemDecoration(
            DividerItemDecoration(
                this, // context
                RecyclerView.VERTICAL
            )
        )

        val swipeToDeleteCallback =
            object : SwipeToDeleteCallback(this, 0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    if (direction == ItemTouchHelper.RIGHT) {
                        sharedPref.edit().putString(
                            "CAR_NUM",
                            mycars.value!![viewHolder.adapterPosition].car_regplate
                        ).apply()
                        rvAdapter.curentCarNum =
                            mycars.value!![viewHolder.adapterPosition].car_regplate
                        rvAdapter.notifyDataSetChanged()
                    } else if (direction == ItemTouchHelper.LEFT) {
                        deleteCar(mycars.value!![viewHolder.adapterPosition].car_regplate)
                        rvAdapter.notifyDataSetChanged()
                    }

//                    rvAdapter.undoView(viewHolder.adapterPosition)
                }
            }

        //configure left swipe
        swipeToDeleteCallback.leftBG = ContextCompat.getColor(this, android.R.color.holo_red_dark)
        swipeToDeleteCallback.leftLabel = "Удалить"
//        swipeToDeleteCallback.leftIcon = AppCompatResources.getDrawable(this, android.R.drawable.btn_star_big_off)

        //configure right swipe
        swipeToDeleteCallback.rightBG =
            ContextCompat.getColor(this, android.R.color.holo_green_dark)
        swipeToDeleteCallback.rightLabel = "Выбрать"
//        swipeToDeleteCallback.rightIcon = AppCompatResources.getDrawable(this, android.R.drawable.ic_menu_delete)

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.myCarsRv)




        getMyCars()

        binding.addCarTv.setOnClickListener{
            if(binding.addContainer.visibility==View.GONE){
                binding.addContainer.visibility = View.VISIBLE
            }
            else{
                binding.addContainer.visibility = View.GONE
            }
        }
        binding.btnAdd.setOnClickListener {
            if (binding.editNameAuto.text.isNotEmpty() && binding.editNumberAuto.text.isNotEmpty()) {
                addCar(binding.editNumberAuto.text.toString(), binding.editNameAuto.text.toString())
                rvAdapter.notifyDataSetChanged()
            }
        }




        mycars.observe(this, {
            Log.e(javaClass.name, ">>>>> " + it)
            rvAdapter.carList = it
            rvAdapter.notifyDataSetChanged()
        })
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


    }

    fun getUser(): LiveData<User> {
        val database = ParkDatabase.getDatabase(this)
        return database.userDao().getUser()
    }

    private fun getMyCars() {
        getUser().observe(this, {
            if (it != null) {
                Log.e(this.localClassName, ">>>> $it")
                var user = it as User

                Log.e(javaClass.name, ">>>>> getMyCars")
                mService.getMyCars(MyCarsRequest(user.AppID, user.ID, user.Phone)).enqueue(object :
                    Callback<MutableList<MyCar>> {
                    override fun onFailure(call: Call<MutableList<MyCar>>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<MutableList<MyCar>>,
                        response: Response<MutableList<MyCar>>
                    ) {


                        val cars = response.body() as MutableList<MyCar>?
                        _mycars.value = cars


                    }
                })
            } else {
                Log.e(this.localClassName, ">>>> getuser is empty")
            }
        })

    }

    protected fun deleteCar(carnum: String) {

        getUser().observe(this@MyCarActivity, {
            if (it != null) {
                Log.e(this@MyCarActivity.localClassName, ">>>> $it")
                var user = it as User

                mService.deleteCar(DeleteCarRequest(user.AppID, user.ID, user.Phone, carnum))
                    .enqueue(object :
                        Callback<StatusCarResponse> {
                        override fun onFailure(call: Call<StatusCarResponse>, t: Throwable) {
                            Snackbar.make(
                                this@MyCarActivity.binding.root,
                                "Ошибка удаления",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("Action", null).show()
                        }

                        override fun onResponse(
                            call: Call<StatusCarResponse>,
                            response: Response<StatusCarResponse>
                        ) {
                            getMyCars()
                            Snackbar.make(
                                this@MyCarActivity.binding.root,
                                "Удалено",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("Action", null).show()
                        }
                    })
            } else {
                Log.e(this@MyCarActivity.localClassName, ">>>> getuser is empty")
            }
        })


    }


    protected fun addCar(carnum: String, note: String) {

        getUser().observe(this@MyCarActivity, {
            if (it != null) {
                Log.e(this@MyCarActivity.localClassName, ">>>> $it")
                var user = it as User

                mService.addCar(AddCarRequest(user.AppID, user.ID, user.Phone, carnum, note))
                    .enqueue(object :
                        Callback<StatusCarResponse> {
                        override fun onFailure(call: Call<StatusCarResponse>, t: Throwable) {
                            Snackbar.make(
                                this@MyCarActivity.binding.root,
                                "Ошибка добавления",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("Action", null).show()
                        }

                        override fun onResponse(
                            call: Call<StatusCarResponse>,
                            response: Response<StatusCarResponse>
                        ) {
                            getMyCars()
                            Snackbar.make(
                                this@MyCarActivity.binding.root,
                                "Добавлено",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("Action", null).show()
                        }
                    })
            } else {
                Log.e(this@MyCarActivity.localClassName, ">>>> getuser is empty")
            }
        })


    }


}