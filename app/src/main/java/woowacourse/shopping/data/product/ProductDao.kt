package woowacourse.shopping.data.product

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.data.WoowaShoppingContract.Product.TABLE_COLUMN_ITEM_IMAGE
import woowacourse.shopping.data.WoowaShoppingContract.Product.TABLE_COLUMN_NAME
import woowacourse.shopping.data.WoowaShoppingContract.Product.TABLE_COLUMN_PRICE
import woowacourse.shopping.data.WoowaShoppingContract.Product.TABLE_NAME
import woowacourse.shopping.data.WoowaShoppingDbHelper

class ProductDao(context: Context) : ProductDataSource {
    private val shoppingDb by lazy { WoowaShoppingDbHelper(context).readableDatabase }

    override fun getProductEntity(id: Long): ProductEntity? {
        val query: String = "SELECT * FROM $TABLE_NAME WHERE ${BaseColumns._ID} = $id"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        if (!cursor.moveToFirst()) return null
        return readProduct(cursor)
    }

    override fun getProductEntities(unit: Int, lastId: Long): List<ProductEntity> {
        val query: String =
            "SELECT * FROM $TABLE_NAME WHERE ${BaseColumns._ID} > $lastId ORDER BY ${BaseColumns._ID} LIMIT $unit"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        val itemContainer = mutableListOf<ProductEntity>()
        while (cursor.moveToNext()) {
            itemContainer.add(readProduct(cursor))
        }
        return itemContainer
    }

    override fun addProductEntity(
        name: String,
        price: Int,
        itemImage: String,
    ): Long {
        val data = ContentValues()
        data.put(TABLE_COLUMN_NAME, name)
        data.put(TABLE_COLUMN_PRICE, price)
        data.put(TABLE_COLUMN_ITEM_IMAGE, itemImage)
        return shoppingDb.insert(TABLE_NAME, null, data)
    }

    override fun isLastProductEntity(id: Long): Boolean {
        val query = "SELECT * FROM $TABLE_NAME WHERE ${BaseColumns._ID} > $id"
        val cursor: Cursor = shoppingDb.rawQuery(query, null)
        val isLast = !cursor.moveToFirst()
        cursor.close()
        return isLast
    }

    private fun readProduct(cursor: Cursor): ProductEntity {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME))
        val itemImage = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ITEM_IMAGE))
        val price = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PRICE))
        return ProductEntity(id, name, itemImage, price)
    }
}
