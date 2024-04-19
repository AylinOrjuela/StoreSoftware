package com.example.storesoftware.domain.model

data class Sale (
    var id:String,
    var name:String,
    var date:String,
    var description:String,
    var amount: Int,
    var totalPrice:String
)
