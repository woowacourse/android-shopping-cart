package woowacourse.shopping.data.local.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.model.CartProductEntity

class CartDao(context: Context) {
    private val db = CartHelper(context).writableDatabase

    // TODO("가격 업데이트")
    // TODO("전체 선택")
    // TODO("이전 화면 돌아갈 시 데이터") // 4시까지
    // TODO("PRODUCTDETAIL") // 5시까지
    fun insertProduct(productId: Long, productCount: Int) {
        val value = ContentValues().apply {
            put(CartContract.Cart.PRODUCT_ID, productId)
            put(CartContract.Cart.PRODUCT_COUNT, productCount)
        }
        val id = db.insertWithOnConflict(
            CartContract.Cart.TABLE_NAME,
            null,
            value,
            SQLiteDatabase.CONFLICT_IGNORE
        )
        updateProductCount(id, value, productId)
    }

    private fun updateProductCount(
        id: Long,
        value: ContentValues,
        productId: Long
    ) {
        if (id == -1L) {
            db.update(
                CartContract.Cart.TABLE_NAME,
                value,
                "${CartContract.Cart.PRODUCT_ID} = ?",
                arrayOf(productId.toString())
            )
        }
    }

    fun updateCartSelected(productId: Long, isSelected: String) {
        val value = ContentValues().apply {
            put(CartContract.Cart.IS_SELECTED, isSelected)
        }
        db.update(
            CartContract.Cart.TABLE_NAME,
            value,
            "${CartContract.Cart.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    fun deleteAllProduct(productId: Long) {
        db.delete(
            CartContract.Cart.TABLE_NAME,
            "${CartContract.Cart.PRODUCT_ID} = ?",
            arrayOf(productId.toString())
        )
    }

    fun getItems(startPosition: Int, cartItemCount: Int): List<CartProductEntity> {
        val result = mutableListOf<CartProductEntity>()
        val cursor = getCursor(startPosition, cartItemCount)
        with(cursor) {
            while (moveToNext()) {
                val productId = getLong(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_ID))
                val productCount = getInt(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_COUNT))
                val isSelected = getString(getColumnIndexOrThrow(CartContract.Cart.IS_SELECTED))
                result.add(
                    CartProductEntity(
                        productId,
                        productCount,
                        isSelected == "y"
                    )
                )
            }
        }
        cursor.close()

        return result.toList()
    }

    private fun getCursor(startPosition: Int, cartItemCount: Int): Cursor {
        return db.rawQuery(CartContract.getCartSql(startPosition, cartItemCount), null)
    }

    fun getDataForTotalPrice(): List<CartProductEntity> {
        val result = mutableListOf<CartProductEntity>()
        val cursor = getTotalPriceCursor()
        with(cursor) {
            while (moveToNext()) {
                val productId = getLong(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_ID))
                val productCount = getInt(getColumnIndexOrThrow(CartContract.Cart.PRODUCT_COUNT))
                result.add(
                    CartProductEntity(
                        productId,
                        productCount,
                        true
                    )
                )
            }
        }
        cursor.close()

        return result.toList()
    }

    private fun getTotalPriceCursor(): Cursor {
        return db.rawQuery(CartContract.getTotalPriceSql(), null)
    }

    fun getItemsWithProductCount(productId: Long): Int? {
        val result = mutableListOf<Int>()

        val cursor = getCartProductCount(productId)
        while (cursor.moveToNext()) {
            result.add(cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.Cart.PRODUCT_COUNT)))
        }

        cursor.close()
        return result.firstOrNull()
    }

    private fun getCartProductCount(productId: Long): Cursor {
        return db.rawQuery(CartContract.getProductCount(productId), null)
    }
}
