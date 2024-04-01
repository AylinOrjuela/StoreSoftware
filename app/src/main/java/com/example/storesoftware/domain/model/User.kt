package com.example.storesoftware.domain.model

class User private constructor() {
    var firstName: String = "Admin Admin"
    var lastName: String = "Add Add"
    var username: String = "Admin123"
    var password: String = "Admin123##"

    companion object {
        val instance: User by lazy { User() }
    }

}

/*
Ejemplo de como usar el objeto Singleton
fun main(){
    val example = User.instance
    val example2 = User.instance

    example.firstName = "Administrador"

    print(example2.firstName)
    //Este print seguira arrojando Administrador
}
 */