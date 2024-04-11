package com.example.storesoftware.data.network

import android.net.Uri
import com.example.storesoftware.data.response.ProductResponse
import com.example.storesoftware.data.response.StoreResponse
import com.example.storesoftware.data.response.TopProductsResponse
import com.example.storesoftware.data.response.UserResponse
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.domain.model.Store
import com.example.storesoftware.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storageMetadata
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseDataBaseService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    companion object {
        const val APP_COLLECTION = "App"
        const val STORE_DOCUMENT = "Store"
        const val USER_COLLECTION = "Users"
        const val PRODUCTS_PATH = "products"
        const val MANAGEMENT_PATH = "management"
        const val TOP_PRODUCT_DOCUMENT = "top_products"
    }

    suspend fun getAllProducts(): List<Product> {
        return firestore.collection(PRODUCTS_PATH).get().await().map { product ->
            product.toObject(ProductResponse::class.java).toDomain()
        }
    }

    suspend fun getLastProduct(): Product? {
        return firestore.collection(PRODUCTS_PATH).orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get().await().firstOrNull()?.toObject(ProductResponse::class.java)?.toDomain()
    }

    suspend fun getTopProducts(): List<String> {
        //Esto es un document Snapshot
        return firestore.collection(MANAGEMENT_PATH).document(TOP_PRODUCT_DOCUMENT).get().await()
            .toObject(TopProductsResponse::class.java)?.ids ?: emptyList()
    }

    suspend fun uploadAndDownloadImage(uri: Uri): String {
        return suspendCancellableCoroutine<String> { suspendCancellable ->
            val reference = storage.reference.child("download/${uri.lastPathSegment}")
            reference.putFile(uri, createMetaData()).addOnSuccessListener {
                downloadImage(it, suspendCancellable)
            }.addOnFailureListener {
                suspendCancellable.resume("")
            }
        }
    }

    private fun downloadImage(
        uploadTask: UploadTask.TaskSnapshot,
        suspendCancellable: CancellableContinuation<String>
    ) {
        uploadTask.storage.downloadUrl.addOnSuccessListener {
            suspendCancellable.resume(it.toString())
        }.addOnFailureListener {
            suspendCancellable.resume("")
        }
    }

    private fun createMetaData(): StorageMetadata {
        val metadata = storageMetadata {
            contentType = "image/jpg"
            setCustomMetadata("date", Date().time.toString())
        }
        return metadata
    }

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

    fun setNewUser(
        firstName: String,
        lastName: String,
        cc: String,
        username: String,
        password: String,
        code: String
    ) {
        val id = generateStoreId()

        val user = hashMapOf(
            "id" to id,
            "firstName" to firstName,
            "lastName" to lastName,
            "cc" to cc,
            "username" to username,
            "password" to password,
            "code" to code
        )
        firestore.collection(USER_COLLECTION).document(id).set(user)
    }

    fun findUserByCredentials(username: String, password: String, callback: (String) -> Unit) {
        firestore.collection(USER_COLLECTION).whereEqualTo("username", username).get()
            .continueWith { task ->
                val querySnapshot = task.result

                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val userDoc = querySnapshot.documents[0]
                    val storedPassword = userDoc.getString("password")

                    if (storedPassword == password) {
                        callback(userDoc.id)
                    } else {
                        callback("")
                    }
                } else {
                    callback("")
                }

            }
    }

    suspend fun getUserById(userId: String): User? {
        return firestore.collection(USER_COLLECTION).document(userId).get().await()
            .toObject(UserResponse::class.java)?.toDomain()
    }

    suspend fun uploadNewProduct(
        name: String,
        description: String,
        price: String,
        stock: Int,
        imageUrl: String
    ): Boolean {
        val id = generateProductId()
        val product = hashMapOf(
            "id" to id,
            "name" to name,
            "description" to description,
            "price" to price,
            "stock" to stock,
            "imageUrl" to imageUrl
        )
        return suspendCancellableCoroutine{ cancellableCorutine ->
            firestore.collection(PRODUCTS_PATH).document(id).set(product).addOnCompleteListener {
            cancellableCorutine.resume(true)
        }.addOnFailureListener {
            cancellableCorutine.resume(false)
        } }

    }

    private fun generateProductId(): String {
        return Date().time.toString()
    }
    
    fun updateUser(user: User) {
        val userUpdate = hashMapOf(
            "cc" to user.cc,
            "code" to user.code,
            "firstName" to user.firstName,
            "id" to user.id,
            "lastName" to user.lastName,
            "password" to user.password,
            "username" to user.username
        )
        firestore.collection(USER_COLLECTION).document(user.id).set(userUpdate)
            .addOnSuccessListener {
                println("Usuario actualizado correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar el usuario: $e")
            }
    }

    fun deleteUser(userId: String) {
        firestore.collection(USER_COLLECTION).document(userId).delete()
            .addOnSuccessListener {
                println("El Usuario ha sido eliminado exitosamente")
            }
            .addOnFailureListener { e ->
                println("Error al eliminar el usuario: $e")
            }
    }

}