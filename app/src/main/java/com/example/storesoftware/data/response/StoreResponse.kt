package com.example.storesoftware.data.response

import com.example.storesoftware.domain.model.Store

data class StoreResponse(
    var id:String = "",
    var address:String = "",
    var name:String = ""
){
    fun toDomain():Store{
        return Store(
            name = name,
            address = address
        )
    }
}
