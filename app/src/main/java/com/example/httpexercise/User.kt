package com.example.httpexercise

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val avatar: Avatar,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
)

data class Avatar(
    val thumbnail: String,
    val photo: String
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
)

data class Geo(
    val lat: String,
    val lng: String
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)
