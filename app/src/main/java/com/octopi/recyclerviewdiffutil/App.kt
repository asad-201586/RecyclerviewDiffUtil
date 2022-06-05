package com.octopi.recyclerviewdiffutil

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        iniLoggers()
    }

    private fun iniLoggers() {

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(0)
            .methodOffset(5)
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree(){
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format("Class:%s: Line: %s, Method: %s",super.createStackElementTag(element),element.lineNumber,element.methodName)
            }

            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }
        })

        Timber.i("inside App!")
    }
}