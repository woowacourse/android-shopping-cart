package woowacourse.shopping.data.database.dao.basket

import android.annotation.SuppressLint
import android.content.ContentValues
import android.provider.BaseColumns
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.database.contract.BasketContract
import woowacourse.shopping.data.database.contract.ProductContract
import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.data.model.DataProduct

class BasketDaoImpl(private val database: ShoppingDatabase) : BasketDao {
    @SuppressLint("Range")
    override fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataBasketProduct> {
        val products = mutableListOf<DataBasketProduct>()
        val start = if (isNext) lastId else (lastId - size)
        database.writableDatabase.use { db ->
            val cursor =
                db.rawQuery(GET_PARTIALLY_QUERY, arrayOf(start.toString(), size.toString()))
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val productId: Int =
                    cursor.getInt(cursor.getColumnIndex("${ProductContract.TABLE_NAME}${BaseColumns._ID}"))
                val name: String =
                    cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME))
                val price: DataPrice =
                    DataPrice(cursor.getInt(cursor.getColumnIndex(ProductContract.COLUMN_PRICE)))
                val imageUrl: String =
                    cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL))
                products.add(DataBasketProduct(id, DataProduct(productId, name, price, imageUrl)))
            }
            cursor.close()
        }
        return products
    }

    override fun add(basketProduct: DataBasketProduct) {
        val contentValues = ContentValues().apply {
            put(ProductContract.TABLE_NAME + BaseColumns._ID, basketProduct.product.id)
        }

        database.writableDatabase.use { db ->
            db.insert(BasketContract.TABLE_NAME, null, contentValues)
        }
    }

    override fun remove(basketProduct: DataBasketProduct) {
        database.writableDatabase.use { db ->
            db.delete(
                BasketContract.TABLE_NAME,
                "${BaseColumns._ID} = ?",
                arrayOf(basketProduct.id.toString())
            )
        }
    }

    companion object {
        private val GET_PARTIALLY_QUERY = """
            SELECT * FROM ${BasketContract.TABLE_NAME}
            INNER JOIN ${ProductContract.TABLE_NAME} ON ${BasketContract.TABLE_NAME}.${ProductContract.TABLE_NAME}${BaseColumns._ID} = ${ProductContract.TABLE_NAME}.${BaseColumns._ID}
            WHERE ${BasketContract.TABLE_NAME}.${BaseColumns._ID} > ?
            ORDER BY ${BasketContract.TABLE_NAME}.${BaseColumns._ID} LIMIT ?        
        """.trimIndent()
    }
}
