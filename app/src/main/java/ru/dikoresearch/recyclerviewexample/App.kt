package ru.dikoresearch.recyclerviewexample

import android.app.Application
import ru.dikoresearch.recyclerviewexample.model.UsersService

class App: Application() {
    val usersService = UsersService()
}