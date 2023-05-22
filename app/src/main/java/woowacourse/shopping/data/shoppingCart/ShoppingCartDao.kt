package woowacourse.shopping.data.shoppingCart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_COLUMN_PRODUCT_ID
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_COLUMN_QUANTITY
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_NAME
import woowacourse.shopping.data.WoowaShoppingDbHelper
import woowacourse.shopping.domain.util.Error
import woowacourse.shopping.domain.util.WoowaResult

class ShoppingCartDao(context: Context) : ShoppingCartDataSource {
    private val shoppingDb by lazy { WoowaShoppingDbHelper(context).readableDatabase }

    override fun getAllEntities(): List<ProductInCartEntity> {
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $TABLE_COLUMN_PRODUCT_ID ASC"
        val cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<ProductInCartEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readProductInCart(cursor))
        }
        return itemContainer
    }

    override fun getProductsInShoppingCart(unit: Int, pageNumber: Int): List<ProductInCartEntity> {
        val offset = unit * (pageNumber - 1)
        val query = "SELECT * FROM $TABLE_NAME LIMIT $unit OFFSET $offset"
        val cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<ProductInCartEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readProductInCart(cursor))
        }
        return itemContainer
    }

    override fun deleteProductInShoppingCart(productId: Long): Boolean {
        val selection = "$TABLE_COLUMN_PRODUCT_ID = ?"
        val selectionArgs = arrayOf("$productId")
        val deletedRows = shoppingDb.delete(TABLE_NAME, selection, selectionArgs)
        if (deletedRows == 0) return false
        return true
    }

    override fun addProductInShoppingCart(productId: Long, quantity: Int): Long {
        val data = ContentValues()
        data.put(TABLE_COLUMN_PRODUCT_ID, productId)
        data.put(TABLE_COLUMN_QUANTITY, quantity)
        return shoppingDb.insert(TABLE_NAME, null, data)
    }

    override fun getShoppingCartSize(): Int {
        val query = "SELECT COUNT(*) FROM $TABLE_NAME"
        val cursor = shoppingDb.rawQuery(query, null)
        var shoppingCartSize = 0
        if (cursor.moveToFirst()) {
            shoppingCartSize = cursor.getInt(0)
        }
        cursor.close()
        return shoppingCartSize
    }

    override fun getTotalQuantity(): Int {
        val TOTAL_COUNT = "totalCount"
        val query = "SELECT SUM($TABLE_COLUMN_QUANTITY) AS $TOTAL_COUNT FROM $TABLE_NAME"
        val cursor = shoppingDb.rawQuery(query, null)
        var totalCount = 0
        if (cursor.moveToFirst()) {
            totalCount = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_COUNT))
        }
        cursor.close()
        return totalCount
    }

    override fun updateProductQuantity(productId: Long, updatedQuantity: Int): WoowaResult<Int> {
        val value = ContentValues()
        value.put(TABLE_COLUMN_QUANTITY, updatedQuantity)
        val whereClause = "$TABLE_COLUMN_PRODUCT_ID = ?"
        val whereArgs = arrayOf("$productId")
        val result: Int = shoppingDb.update(TABLE_NAME, value, whereClause, whereArgs)
        return when (result) {
            0 -> WoowaResult.SUCCESS(addProductInShoppingCart(productId, updatedQuantity).toInt())
            1 -> WoowaResult.SUCCESS(1)
            else -> WoowaResult.FAIL(Error.DataBaseError)
        }
    }

    override fun increaseProductQuantity(productId: Long, plusQuantity: Int): WoowaResult<Int> {
        val productQuantity = (getProductInShoppingCart(productId)?.quantity ?: 0) + plusQuantity
        return updateProductQuantity(productId, productQuantity)
    }

    private fun getProductInShoppingCart(productId: Long): ProductInCartEntity? {
        val query: String = "SELECT * FROM $TABLE_NAME WHERE $TABLE_COLUMN_PRODUCT_ID = $productId"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        if (!cursor.moveToFirst()) return null
        return readProductInCart(cursor)
    }

    private fun readProductInCart(cursor: Cursor): ProductInCartEntity {
        val productId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRODUCT_ID))
        val productQuantity =
            cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_QUANTITY))
        return ProductInCartEntity(productId, productQuantity)
    }
}
