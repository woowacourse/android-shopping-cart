package woowacourse.shopping.data.shoppingCart

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_COLUMN_PRODUCT_ID
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_COLUMN_QUANTITY
import woowacourse.shopping.data.WoowaShoppingContract.ShoppingCart.TABLE_NAME
import woowacourse.shopping.data.WoowaShoppingDbHelper

class ShoppingCartDao(context: Context) : ShoppingCartDataSource {
    private val shoppingDb by lazy { WoowaShoppingDbHelper(context).readableDatabase }

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

    override fun deleteProductInShoppingCart(id: Long): Boolean {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        val deletedRows = shoppingDb.delete(TABLE_NAME, selection, selectionArgs)
        if (deletedRows == 0) return false
        return true
    }

    private fun readProductInCart(cursor: Cursor): ProductInCartEntity {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val productId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRODUCT_ID))
        val productQuantity =
            cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_QUANTITY))
        return ProductInCartEntity(id, productId, productQuantity)
    }
}
