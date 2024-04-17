package com.example.storesoftware.data.response

import com.example.storesoftware.domain.model.BuyReceipt

data class BuyReceiptResponse (
    var idReceipt:String = "",
    var userId:String = "",
    var date:String = "",
    var description:String = "",
    var units: String = "",
    var amount:String = ""
){
    fun toDomain():BuyReceipt{
        return BuyReceipt(
            idReceipt = idReceipt,
            userId = userId,
            date = date,
            description = description,
            units = units,
            amount = amount
        )
    }
}