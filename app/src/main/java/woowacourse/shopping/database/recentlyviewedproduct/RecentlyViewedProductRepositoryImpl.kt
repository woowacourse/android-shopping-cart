package woowacourse.shopping.database.recentlyviewedproduct

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.database.ProductDBHelper
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository

class RecentlyViewedProductRepositoryImpl(
    context: Context,
    private val productRepository: ProductRepository,
) : RecentlyViewedProductRepository {
    private val db = ProductDBHelper(context).writableDatabase

    override fun findAll(): List<RecentlyViewedProduct> {
        val products = mutableListOf<RecentlyViewedProduct>()
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME}",
            null,
        )

        while (cursor.moveToNext()) {
            val id =
                cursor.getLong(cursor.getColumnIndexOrThrow(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID))
            productRepository.findById(id)?.let {
                products.add(RecentlyViewedProduct(it.id, it.imageUrl, it.name, it.price))
            }
        }

        cursor.close()
        return products
    }

    override fun findLast(): RecentlyViewedProduct? {
        var product: RecentlyViewedProduct? = null
        val cursor: Cursor = db.rawQuery(
            """
                SELECT * FROM ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME} 
                ORDER BY ${BaseColumns._ID} DESC 
                LIMIT 1;
            """.trimIndent(),
            null,
        )

        while (cursor.moveToNext()) {
            val id =
                cursor.getLong(cursor.getColumnIndexOrThrow(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID))
            productRepository.findById(id)?.let {
                product = RecentlyViewedProduct(it.id, it.imageUrl, it.name, it.price)
            }
        }

        cursor.close()
        return product
    }

    override fun save(product: RecentlyViewedProduct) {
        deleteExistingItem(product)
        insertItem(product)
    }

    private fun deleteExistingItem(product: RecentlyViewedProduct) {
        val selection = "${ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(product.id.toString())
        db.delete(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, selection, selectionArgs)
    }

    private fun insertItem(product: RecentlyViewedProduct) {
        val value = ContentValues().apply {
            put(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID, product.id)
        }
        db.insert(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, null, value)
    }
}
