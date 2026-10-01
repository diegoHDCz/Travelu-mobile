package br.com.travelu

import android.app.Application
import br.com.travelu.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class TraveluApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TraveluApplication)
        }
    }
}
