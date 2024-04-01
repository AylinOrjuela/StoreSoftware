package com.example.storesoftware.data.network

import com.example.storesoftware.data.response.ProductResponse
import com.example.storesoftware.data.response.StoreResponse
import com.example.storesoftware.data.response.TopProductsResponse
import com.example.storesoftware.domain.model.Product
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

    fun uploadNewStore(name: String, address: String) {
        val id = generateStoreId()
        val store = hashMapOf(
            "id" to id,
            "name" to name,
            "address" to address
        )
        firestore.collection(APP_COLLECTION).document(STORE_DOCUMENT).set(store)
    }

    private fun generateStoreId(): String {
        return Date().time.toString()
    }

}