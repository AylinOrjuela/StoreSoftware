package com.example.storesoftware.domain.model

data class Employee(
    var firstName:String = "",
    var lastName:String = "",
    var cc:String = "",
    var username:String = "",
    var password:String = "",
    var deleteItems:Boolean = false,
    var updateItems:Boolean = false,
    var consultItems:Boolean = true
)
