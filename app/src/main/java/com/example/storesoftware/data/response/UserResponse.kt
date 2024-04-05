package com.example.storesoftware.data.response

import com.example.storesoftware.domain.model.User

data class UserResponse(
    var cc: String = "",
    var code: String = "",
    var firstName: String = "",
    var id: String = "",
    var lastName: String = "",
    var password: String = "",
    var username: String = ""
) {
    fun toDomain(): User {
        return User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            cc = cc,
            username = username,
            password = password,
            code = code
        )
    }
}