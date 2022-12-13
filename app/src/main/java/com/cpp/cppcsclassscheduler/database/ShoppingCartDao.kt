package com.cpp.cppcsclassscheduler.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cpp.cppcsclassscheduler.CsClass
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM csClasses")
    fun getCart(): Flow<List<CsClass>>

    @Query("SELECT * FROM csClasses WHERE courseId = :courseId AND section = :section")
    suspend fun queryClassInCart(courseId: Int, section: Int): CsClass?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addClassToCart(item: CsClass)

    @Delete
    suspend fun deleteClassFromCart(item: CsClass)
}