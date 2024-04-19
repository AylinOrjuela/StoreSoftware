package com.example.storesoftware.domain.model

data class BuyReceipt (
    var idReceipt:String,
    var userId:String,
    var date:String,
    var description:String,
    var units: String,
    var amount:String
)