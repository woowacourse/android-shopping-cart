package woowacourse.shopping.database.cart

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract.CartEntry
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.repository.ProductRepository
import java.time.LocalDateTime

class CartItemRepositoryImpl(
    private val db: SQLiteDatabase,
    private val productRepository: ProductRepository,
) : CartItemRepository {
    override fun save(cartItem: CartItem) {
        val cursor = db.query(
            CartEntry.TABLE_NAME,
            arrayOf(BaseColumns._ID),
            "${BaseColumns._ID} = ?",
            arrayOf(cartItem.id.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToNext()) {
            cursor.close()
            return
        }

        val value = ContentValues().apply {
            put(BaseColumns._ID, cartItem.id)
            put(CartEntry.COLUMN_NAME_PRODUCT_ID, cartItem.product.id)
            put(CartEntry.COLUMN_NAME_ADDED_TIME, cartItem.addedTime.toString())
            put(CartEntry.COLUMN_NAME_COUNT, cartItem.count)
        }
        val id = db.insert(CartEntry.TABLE_NAME, null, value)
        cartItem.id = id
        cursor.close()
    }

    override fun findAllOrderByAddedTime(limit: Int, offset: Int): List<CartItem> {
        val cartItems = mutableListOf<CartItem>()

        val cursor = db.rawQuery("""
            SELECT * FROM ${CartEntry.TABLE_NAME} 
            ORDER BY ${CartEntry.COLUMN_NAME_ADDED_TIME} 
            LIMIT $limit OFFSET $offset
        """.trimIndent(), null
        )
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val productId =
                cursor.getLong(cursor.getColumnIndexOrThrow(CartEntry.COLUMN_NAME_PRODUCT_ID))
            val addedTime =
                cursor.getString(cursor.getColumnIndexOrThrow(CartEntry.COLUMN_NAME_ADDED_TIME))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(CartEntry.COLUMN_NAME_COUNT))
            val cartItem = CartItem(
                productRepository.findById(productId) ?: throw IllegalArgumentException("참조 무결성 제약조건 위반"),
                LocalDateTime.parse(addedTime),
                count
            ).apply { this.id = id }
            cartItems.add(cartItem)
        }
        cursor.close()
        return cartItems
    }

    override fun countAll(): Int {
        val cursor = db.query(
            CartEntry.TABLE_NAME,
            arrayOf("COUNT(*)"),
            null,
            null,
            null,
            null,
            null
        )
        val count = cursor.getLong(0)
        cursor.close()
        return count.toInt()
    }

    override fun deleteById(cartItemId: Long) {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(cartItemId.toString())
        db.delete(CartEntry.TABLE_NAME, selection, selectionArgs)
    }
}