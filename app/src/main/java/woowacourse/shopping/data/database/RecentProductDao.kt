package woowacourse.shopping.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.model.RecentProductEntity
import java.time.LocalDateTime

class RecentProductDao(context: Context) {
    private val db = RecentProductHelper(context).writableDatabase

    fun insertRecentProduct(productId: Long) {
        val value = ContentValues().apply {
            put(RecentProductContract.RecentProduct.PRODUCT_ID, productId)
            put(RecentProductContract.RecentProduct.CREATE_DATE, LocalDateTime.now().toString())
        }
        if (checkRecentProduct(productId)) {
            db.update(
                RecentProductContract.RecentProduct.TABLE_NAME,
                value,
                "${RecentProductContract.RecentProduct.PRODUCT_ID} = ? ",
                arrayOf(productId.toString())
            )
            return
        }
        db.insert(RecentProductContract.RecentProduct.TABLE_NAME, null, value)
    }

    fun deleteNotToday(today: String) {
        val sql =
            "DELETE FROM ${RecentProductContract.RecentProduct.TABLE_NAME} WHERE ${RecentProductContract.RecentProduct.CREATE_DATE} NOT LIKE '$today%'"

        db.execSQL(sql)
    }

    private fun checkRecentProduct(selectProductId: Long): Boolean {
        val recentProduct = selectRecentProductByProductId(selectProductId)
        if (recentProduct.isEmpty()) return false
        return true
    }

    private fun selectRecentProductByProductId(selectProductId: Long): List<RecentProductEntity> {
        val result = mutableListOf<RecentProductEntity>()
        val cursor = getCursorByProductId(selectProductId, 1)

        with(cursor) {
            while (moveToNext()) {
                val recentProductId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val productId =
                    getLong(getColumnIndexOrThrow(RecentProductContract.RecentProduct.PRODUCT_ID))
                result.add(RecentProductEntity(recentProductId, productId))
            }
        }
        cursor.close()

        return result
    }

    fun getAll(limit: Int): List<RecentProductEntity> {
        val result = mutableListOf<RecentProductEntity>()
        val cursor = getCursor(limit)
        with(cursor) {
            while (moveToNext()) {
                val recentProductId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val productId =
                    getLong(getColumnIndexOrThrow(RecentProductContract.RecentProduct.PRODUCT_ID))
                result.add(RecentProductEntity(recentProductId, productId))
            }
        }
        cursor.close()
        if (result.isEmpty()) result.add(getErrorData())

        return result.toList()
    }

    private fun getCursor(limit: Int): Cursor {
        return db.query(
            RecentProductContract.RecentProduct.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            RecentProductContract.RecentProduct.CREATE_DATE + " DESC",
            limit.toString()
        )
    }

    private fun getCursorByProductId(productId: Long, limit: Int): Cursor {
        return db.query(
            RecentProductContract.RecentProduct.TABLE_NAME,
            null,
            "${RecentProductContract.RecentProduct.PRODUCT_ID} = ?",
            arrayOf(productId.toString()),
            null,
            null,
            null,
            limit.toString()
        )
    }

    private fun getErrorData() = RecentProductEntity(
        id = -1L,
        productId = -1L
    )
}
