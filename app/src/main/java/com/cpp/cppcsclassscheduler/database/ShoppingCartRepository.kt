package com.cpp.cppcsclassscheduler.database

import android.content.Context
import androidx.room.Room
import com.cpp.cppcsclassscheduler.database.CsClass
import com.cpp.cppcsclassscheduler.database.ShoppingCartDatabase
import kotlinx.coroutines.flow.Flow


private const val DATABASE_NAME = "class-shopping-cart.db"

class ShoppingCartRepository private constructor(context: Context) {

    private val database: ShoppingCartDatabase = Room.databaseBuilder(
        context.applicationContext,
        ShoppingCartDatabase::class.java,
        DATABASE_NAME
    )
        .build()
    private val cartDao = database.cartDao()

    fun getCart(): Flow<List<CsClass>> = cartDao.getCart()

    suspend fun queryClassInCart(courseId: Int, section: Int): CsClass? = cartDao.queryClassInCart(courseId, section)

    suspend fun addClassToCart(item: CsClass) = cartDao.addClassToCart(item)

    suspend fun deleteClassFromCart(item: CsClass) = cartDao.deleteClassFromCart(item)

    companion object {
        private var INSTANCE: ShoppingCartRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ShoppingCartRepository(context)
            }
        }

        fun get(): ShoppingCartRepository {
            return INSTANCE ?:
            throw IllegalStateException("Shopping Cart Repository must be initialized")
        }
    }
}