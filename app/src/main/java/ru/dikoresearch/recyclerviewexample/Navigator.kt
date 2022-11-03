package ru.dikoresearch.recyclerviewexample

import ru.dikoresearch.recyclerviewexample.model.User

interface Navigator {

    fun showDetails(user: User)

    fun goBack()

    fun toast(messageRes: Int)

}