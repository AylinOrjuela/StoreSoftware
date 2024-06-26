package com.example.storesoftware.data.network

import android.net.Uri
import com.example.storesoftware.data.response.BuyReceiptResponse
import com.example.storesoftware.data.response.ProductResponse
import com.example.storesoftware.data.response.SaleResponse
import com.example.storesoftware.data.response.StoreResponse
import com.example.storesoftware.data.response.TopProductsResponse
import com.example.storesoftware.data.response.UserResponse
import com.example.storesoftware.domain.model.BuyReceipt
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.domain.model.Sale
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
import java.text.SimpleDateFormat
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
        const val SALE_PATH = "sale"
        const val PURCHASE_RECEIPT_PATH = "Purchase"
    }

    suspend fun getAllProducts(): List<Product> {
        return firestore.collection(PRODUCTS_PATH).get().await().map { product ->
            product.toObject(ProductResponse::class.java).toDomain()
        }
    }
    suspend fun getProductById(ProductId: String): Product? {
        return firestore.collection(PRODUCTS_PATH).document(ProductId).get().await()
            .toObject(ProductResponse::class.java)?.toDomain()
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

    suspend fun checkUserPermissions(userId: String): Boolean {
        val user = getUserById(userId)
        val isAdmin = user?.code == "95876"
        return isAdmin
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
    fun deleteProduct(productId: String) {
        firestore.collection(PRODUCTS_PATH).document(productId).delete()
            .addOnSuccessListener {
                println("El producto ha sido eliminado exitosamente")
            }
            .addOnFailureListener { e ->
                println("Error al eliminar el producto: $e")
            }
    }
    fun updateProduct(product: Product) {
        val productUpdate = hashMapOf(
            "id" to product.id,
            "name" to product.name,
            "description" to product.description,
            "price" to product.price,
            "stock" to product.stock,
            "imageUrl" to product.imageUrl
        )

        firestore.collection(PRODUCTS_PATH).document(product.id).set(productUpdate)
            .addOnSuccessListener {
                println("Producto actualizado correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar el producto: $e")
            }
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

    fun uploadNewSale(
        name: String,
        description: String,
        amount: Int,
        totalPrice: String
    ) {
        val id = generateSaleId()
        val date = generatedate()

        val SALE = hashMapOf(
            "id" to id,
            "name" to name,
            "date" to date,
            "description" to description,
            "amount" to amount,
            "totalPrice" to totalPrice
        )

        firestore.collection(SALE_PATH).document(id).set(SALE)
    }
    suspend fun getSaleReceiptById(receiptId: String): Sale? {
        return firestore.collection(SALE_PATH).document(receiptId).get().await()
            .toObject(SaleResponse::class.java)?.toDomain()
    }

    suspend fun getListSaleReceipts(): List<Sale> {
        return firestore.collection(SALE_PATH).get().await().map { saleReceipt ->
            saleReceipt.toObject(SaleResponse::class.java).toDomain()
        }
    }

    fun updateSaleReceipt(receipt: Sale) {
        val receiptData = hashMapOf(
            "id" to receipt.id,
            "name" to receipt.name,
            "date" to receipt.date,
            "description" to receipt.description,
            "amount" to receipt.amount,
            "totalPrice" to receipt.totalPrice
        )
        firestore.collection(SALE_PATH).document(receipt.id).set(receiptData)
            .addOnSuccessListener {
                println("Recibo de Compra actualizado correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar el Recibo: $e")
            }
    }

    fun deleteSaleReceipt(receipt: String?) {
        if (receipt != null) {
            firestore.collection(SALE_PATH).document(receipt).delete()
                .addOnSuccessListener {
                    println("Recibo con ID ${receipt} eliminado correctamente")
                }
                .addOnFailureListener { e ->
                    println("Error al eliminar el recibo: $e")
                }
        }
    }

    private fun generateSaleId(): String {
        return Date().time.toString()
    }

    private fun generatedate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return dateFormat.format(date)
    }
    
    fun createPurchaseReceipt (receipt: BuyReceipt) {

        val receiptData = hashMapOf(
            "id" to receipt.idReceipt,
            "userId" to receipt.userId,
            "date" to receipt.date,
            "description" to receipt.description,
            "units" to receipt.units,
            "amount" to receipt.amount,
        )
        firestore.collection(PURCHASE_RECEIPT_PATH).document(receipt.idReceipt).set(receiptData)
    }

    suspend fun getListBuyReceipts(): List<BuyReceipt> {
        return firestore.collection(PURCHASE_RECEIPT_PATH).get().await().map { buyReceipt ->
            buyReceipt.toObject(BuyReceiptResponse::class.java).toDomain()
        }
    }

    suspend fun getBuyReceiptById(receiptId: String): BuyReceipt? {
        return firestore.collection(PURCHASE_RECEIPT_PATH).document(receiptId).get().await()
            .toObject(BuyReceiptResponse::class.java)?.toDomain()
    }

    fun updateBuyReceipt(receipt: BuyReceipt) {
        val receiptData = hashMapOf(
            "amount" to receipt.amount,
            "date" to receipt.date,
            "description" to receipt.description,
            "id" to receipt.idReceipt,
            "units" to receipt.units,
            "userId" to receipt.userId
        )
        firestore.collection(PURCHASE_RECEIPT_PATH).document(receipt.idReceipt).set(receiptData)
            .addOnSuccessListener {
                println("Recibo de Compra actualizado correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al actualizar el Recibo: $e")
            }
    }

    fun deleteBuyReceipt(receipt: BuyReceipt) {
        firestore.collection(PURCHASE_RECEIPT_PATH).document(receipt.idReceipt).delete()
            .addOnSuccessListener {
                println("Recibo con ID ${receipt.idReceipt} eliminado correctamente")
            }
            .addOnFailureListener { e ->
                println("Error al eliminar el recibo: $e")
            }
    }




}