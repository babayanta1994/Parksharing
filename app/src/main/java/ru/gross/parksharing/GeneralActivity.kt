package ru.gross.parksharing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.runtime.Runtime
import com.yandex.runtime.ui_view.ViewProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gross.parksharing.api.common.Common
import ru.gross.parksharing.api.response.ParkDetail
import ru.gross.parksharing.api.response.ParkPlace
import ru.gross.parksharing.api.services.RetrofitServices
import ru.gross.parksharing.components.ClusterPlace
import ru.gross.parksharing.components.Placemark
import ru.gross.parksharing.databinding.ActivityGeneralBinding
import ru.gross.parksharing.databinding.NavHeaderGeneralBinding
import ru.gross.parksharing.request.CarparksAvailableRequest

class GeneralActivity : AppCompatActivity(), ClusterListener, ClusterTapListener,
    GeoObjectTapListener, InputListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityGeneralBinding

    private lateinit var mService: RetrofitServices
    private var clusterizedCollection: ClusterizedPlacemarkCollection? = null
    private var tapedPlacemark: PlacemarkMapObject? = null
    lateinit var navController: NavController
    var point = Point(59.1, 30.1)
    var isVisibleLinear: ObservableField<Boolean> = ObservableField(true)

    var isCarFront = MutableLiveData<Boolean>().apply { value=true }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.appBarGeneral.content.mapview.onStart()
    }

    override fun onStop() {
        binding.appBarGeneral.content.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }





    fun initNavDrawer() {

        setSupportActionBar(binding.appBarGeneral.toolbar)

        val headerView = binding.navView.getHeaderView(0)
        val headerBinding = NavHeaderGeneralBinding.bind(headerView)
        headerBinding.btnClose.setOnClickListener {
            binding.drawerLayout.closeDrawer(binding.navView)
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_general)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_map,
                R.id.nav_profile,
                R.id.nav_my_cars,
                R.id.nav_pay,
                R.id.nav_my_booking,
                R.id.nav_promocodes,
                R.id.nav_howit
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        headerBinding.parkmapIcon.setOnClickListener {
            if (!navController.popBackStack()) {
                navController.navigate(R.id.nav_map)
            }
            binding.drawerLayout.closeDrawer(binding.navView)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        isCarFront.observe(this, Observer {
            if(it){
                binding.appBarGeneral.content.carParking.elevation = 8f
            }else{
                binding.appBarGeneral.content.carParking.elevation = 0f
            }
        })
        mService = Common.retrofitService
        binding = ActivityGeneralBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavDrawer()

        binding.appBarGeneral.content.mapview.map.move(
            CameraPosition(point, 8F, 0F, 0F),
            Animation(Animation.Type.SMOOTH, 3f),
            null
        )
        binding.appBarGeneral.content.mapview.map.logo.setAlignment(
            Alignment(
                HorizontalAlignment.LEFT,
                VerticalAlignment.BOTTOM
            )
        )
        clusterizedCollection =
            binding.appBarGeneral.content.mapview.map.mapObjects.addClusterizedPlacemarkCollection(
                this
            )

        getAllParks(
            CarparksAvailableRequest(
                Phone = "+79999999999",
                AppID = "A1B1-C2D2-E3F3-G4H4-I5J5",
                ID = "K1L1-M2N2-O3P3-Q4R4-S5T5",
                lat = 99.999999,
                lon = 99.999999,
                `when` = "2021-09-30T23:59:59Z"
            )
        )

        binding.appBarGeneral.content.mapview.map.mapObjects.addTapListener(mapObjectTapListener)
        binding.appBarGeneral.content.mapview.map.addTapListener(this)
        binding.appBarGeneral.content.mapview.map.addInputListener(this)

        binding.appBarGeneral.content.carParking.setOnClickListener { view ->
            Snackbar.make(view, "carParking action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.appBarGeneral.content.myLocation.setOnClickListener { view ->
            Snackbar.make(view, "myLocation action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.general, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_general)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun unselectPlacemark() {
        if (tapedPlacemark != null) {
            val parkPlace = tapedPlacemark?.userData as ParkPlace
            tapedPlacemark?.setView(
                ViewProvider(
                    Placemark(
                        this,
                        "${parkPlace.num_vacant} мест",
                        false
                    )
                )
            )
        }
    }

    private fun selectPlacemark() {
        if (tapedPlacemark != null) {
            val parkPlace = tapedPlacemark?.userData as ParkPlace
            tapedPlacemark?.setView(
                ViewProvider(
                    Placemark(
                        this,
                        "${parkPlace.num_vacant} мест",
                        true
                    )
                )
            )
        }
    }




    private val mapObjectTapListener =
        MapObjectTapListener { mapObject, _ ->
            unselectPlacemark()
            if (mapObject is PlacemarkMapObject) {
                tapedPlacemark = mapObject
                val dataa = tapedPlacemark!!.userData as ParkPlace
                selectPlacemark()
                val bundle = Bundle()
                dataa.id?.let { bundle.putInt("carpark_id", it) }
                navController.popBackStack(R.id.nav_detail,true)
                navController.navigate(R.id.nav_detail, bundle)

            }
            true
        }


    override fun onClusterAdded(cluster: Cluster) {
        cluster.appearance.setView(
            ViewProvider(
                ClusterPlace(
                    this,
                    cluster.size.toString()
                )
            )
        )
        cluster.addClusterTapListener(this)
    }

    override fun onClusterTap(cluster: Cluster): Boolean {
        return true
    }


    private fun getAllParks(carparksAvailableRequest: CarparksAvailableRequest) {
        mService.getCarparksAvailable(carparksAvailableRequest).enqueue(object :
            Callback<MutableList<ParkPlace>> {
            override fun onFailure(call: Call<MutableList<ParkPlace>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MutableList<ParkPlace>>,
                response: Response<MutableList<ParkPlace>>
            ) {
                if (clusterizedCollection != null) {
                    val parks = response.body() as MutableList<ParkPlace>
                    if(parks.size>0){

                    }
                    for (park in parks) {
                        if (park.lat != null && park.lon != null) {
                            clusterizedCollection!!
                                .addPlacemark(
                                    Point(park.lat!!, park.lon!!),
                                    ViewProvider(
                                        Placemark(
                                            this@GeneralActivity,
                                            "${park.num_vacant} мест"
                                        )
                                    )
                                ).userData = park
                        }
                    }
                    clusterizedCollection!!.clusterPlacemarks(60.0, 15)
                }
            }
        })
    }


    override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {
        val selectionMetadata: GeoObjectSelectionMetadata = geoObjectTapEvent
            .geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)

        binding.appBarGeneral.content.mapview.map.selectGeoObject(
            selectionMetadata.id,
            selectionMetadata.layerId
        )

        unselectPlacemark()
        navController.popBackStack(R.id.nav_map,true)
        navController.navigate(R.id.nav_map)

        return true
    }

    override fun onMapTap(p0: Map, p1: Point) {
        binding.appBarGeneral.content.mapview.map.deselectGeoObject()
        unselectPlacemark()

//        if (!navController.popBackStack()) {

            navController.popBackStack(R.id.nav_map,true)
            navController.navigate(R.id.nav_map)
//        }
    }

    override fun onMapLongTap(p0: Map, p1: Point) {

    }


}