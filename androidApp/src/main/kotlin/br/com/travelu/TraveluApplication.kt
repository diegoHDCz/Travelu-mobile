package br.com.travelu

import android.app.Application
import br.com.travelu.core.di.initKoin
import br.com.travelu.core.network.AppConfig
import org.koin.android.ext.koin.androidContext

class TraveluApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppConfig.init(BuildConfig.API_BASE_URL)
        initKoin {
            androidContext(this@TraveluApplication)
        }
    }
}
