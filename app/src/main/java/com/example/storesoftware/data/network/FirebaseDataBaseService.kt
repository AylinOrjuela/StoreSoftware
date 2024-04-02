package com.example.storesoftware.data.network

import com.example.storesoftware.data.response.StoreResponse
import com.example.storesoftware.domain.model.Store
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class FirebaseDataBaseService @Inject constructor(private val firestore: FirebaseFirestore) {

    companion object {
        const val APP_COLLECTION = "App"
        const val STORE_DOCUMENT = "Store"
    }

//    suspend fun getAllProducts(): List<Product> {
//        return firestore.collection(PRODUCTS_PATH).get().await().map { product ->
//            product.toObject(ProductResponse::class.java).toDomain()
//        }
//    }
//
//    suspend fun getLastProduct(): Product? {
//        return firestore.collection(PRODUCTS_PATH).orderBy("id", Query.Direction.DESCENDING)
//            .limit(1)
//            .get().await().firstOrNull()?.toObject(ProductResponse::class.java)?.toDomain()
//    }
//
//    suspend fun getTopProducts(): List<String> {
//        //Esto es un document Snapshot
//        return firestore.collection(MANAGEMENT_PATH).document(TOP_PRODUCT_DOCUMENT).get().await()
//            .toObject(TopProductsResponse::class.java)?.ids ?: emptyList()
//    }

    suspend fun getStoreData(): Store? {
        return firestore.collection(APP_COLLECTION).orderBy("id", Query.Direction.DESCENDING)
            .limit(1).get().await().firstOrNull()?.toObject(StoreResponse::class.java)?.toDomain()
    }
    //Funcion para registrar tienda
    fun uploadNewStore(name: String, address: String) {
        val id = generateStoreId()
        val store = hashMapOf(
            "id" to id,
            "name" to name,
            "address" to address
        )
        firestore.collection(APP_COLLECTION).document(STORE_DOCUMENT).set(store)
    }

    // Función para actualizar la tienda
    fun updateStore(id: String, name: String, address: String) {
        val store = hashMapOf(
            "id" to id,
            "name" to name,
            "address" to address
        )
        firestore.collection(APP_COLLECTION).document(STORE_DOCUMENT).set(store)
            .addOnSuccessListener {
                println("Tienda actualizada correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar la tienda: $e")
            }
    }

    // Función para consultar la tienda por ID
    fun getStoreById(storeId: String) {
        firestore.collection(APP_COLLECTION).document(STORE_DOCUMENT).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val id = document.getString("id")
                    val name = document.getString("name")
                    val address = document.getString("address")
                    println("ID: $id, Nombre: $name, Dirección: $address")
                } else {
                    println("No se encontró la tienda con el ID proporcionado")
                }
            }
            .addOnFailureListener { e ->
                println("Error al obtener la tienda: $e")
            }
    }

    //Funcion para Eliminar tienda
    fun deleteStore(storeId: String) {
        firestore.collection(APP_COLLECTION).document(storeId).delete()
            .addOnSuccessListener {
                println("Tienda con ID $storeId eliminada correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al eliminar la tienda: $e")
            }
    }

    private fun generateStoreId(): String {
        return Date().time.toString()
    }

}