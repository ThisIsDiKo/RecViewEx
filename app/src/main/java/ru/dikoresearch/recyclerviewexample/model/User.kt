package ru.dikoresearch.recyclerviewexample.model

data class User(
    val id: Long,
    val photo: String,
    val name: String,
    var company: String
)
data class UserDetails(
    val user: User,
    val details: String
)