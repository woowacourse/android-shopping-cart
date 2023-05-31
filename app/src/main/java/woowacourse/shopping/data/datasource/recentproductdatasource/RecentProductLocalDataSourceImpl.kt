package woowacourse.shopping.data.datasource.recentproductdatasource

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.domain.Product
import com.example.domain.RecentProducts
import woowacourse.shopping.data.db.recentproduct.RecentProductContract
import woowacourse.shopping.data.db.recentproduct.RecentProductDbHelper

class RecentProductLocalDataSourceImpl(
    context: Context,
) {
    private val db = RecentProductDbHelper(context).writableDatabase

    private fun getCursor(): Cursor {
        return db.query(
            RecentProductContract.TABLE_NAME,
            arrayOf(
                RecentProductContract.TABLE_COLUMN_ID,
                RecentProductContract.TABLE_COLUMN_IMAGE_URL,
                RecentProductContract.TABLE_COLUMN_NAME,
                RecentProductContract.TABLE_COLUMN_PRICE,
            ),
            "",
            arrayOf(),
            null,
            null,
            "",
        )
    }

    fun getRecentProducts(): RecentProducts {
        val cursor = getCursor()
        val recentProducts = RecentProducts()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_ID))
                val imageUrl = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_IMAGE_URL))
                val name = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_NAME))
                val price = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRICE))

                recentProducts.addProduct(Product(id, imageUrl, name, price))
            }
        }

        cursor.close()
        return recentProducts
    }

    fun getLastProduct(): Product? {
        val cursor = getCursor()

        if (cursor.moveToLast().not()) return null

        with(cursor) {
            val id = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_ID))
            val imageUrl = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_IMAGE_URL))
            val name = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_NAME))
            val price = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRICE))

            return Product(id, imageUrl, name, price)
        }
    }

    fun addColumn(product: Product) {
        findProductById(product.id)?.let {
            deleteColumn(it)
        }

        val values = ContentValues().apply {
            put(RecentProductContract.TABLE_COLUMN_ID, product.id)
            put(RecentProductContract.TABLE_COLUMN_IMAGE_URL, product.imageUrl)
            put(RecentProductContract.TABLE_COLUMN_NAME, product.name)
            put(RecentProductContract.TABLE_COLUMN_PRICE, product.price)
        }

        db.insert(RecentProductContract.TABLE_NAME, null, values)
    }

    fun findProductById(id: Int): Product? {
        val cursor = db.rawQuery(
            "SELECT * FROM ${RecentProductContract.TABLE_NAME} WHERE id = $id",
            null,
        )

        if (cursor.moveToFirst().not()) return null
        return getRecentProduct(cursor)
    }

    private fun getRecentProduct(cursor: Cursor): Product {
        with(cursor) {
            val productId = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_ID))
            val productImageUrl = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_IMAGE_URL))
            val productName = getString(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_NAME))
            val productPrice = getInt(getColumnIndexOrThrow(RecentProductContract.TABLE_COLUMN_PRICE))

            return Product(
                id = productId,
                imageUrl = productImageUrl,
                name = productName,
                price = productPrice,
            )
        }
    }

    fun deleteColumn(product: Product) {
        db.delete(
            RecentProductContract.TABLE_NAME,
            RecentProductContract.TABLE_COLUMN_ID + "=" + product.id,
            null,
        )
    }
}
