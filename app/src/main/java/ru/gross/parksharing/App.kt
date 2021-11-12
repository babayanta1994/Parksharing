package ru.gross.parksharing

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    private val MAPKIT_API_KEY = "bf69c126-6543-4499-a8ed-984f18818ab8"
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(MAPKIT_API_KEY)

        
    }

}