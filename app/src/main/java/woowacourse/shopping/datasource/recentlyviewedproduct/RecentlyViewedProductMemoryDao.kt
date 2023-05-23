package woowacourse.shopping.datasource.recentlyviewedproduct

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.ProductRepository
import java.time.LocalDateTime

class RecentlyViewedProductMemoryDao(private val db: SQLiteDatabase, private val productRepository: ProductRepository) : RecentlyViewedProductDataSource {
    override fun save(recentlyViewedProduct: RecentlyViewedProduct) {
        deleteRecentlyViewedProductIfSameProductExists(recentlyViewedProduct)

        val value = ContentValues().apply {
            put(
                ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID,
                recentlyViewedProduct.product.id
            )
            put(
                ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_VIEWED_TIME,
                recentlyViewedProduct.viewedTime.toString()
            )
        }
        val id = db.insert(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, null, value)
        recentlyViewedProduct.id = id
    }

    private fun deleteRecentlyViewedProductIfSameProductExists(recentlyViewedProduct: RecentlyViewedProduct) {
        val selection = "${ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(recentlyViewedProduct.product.id.toString())
        db.delete(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun findFirst10OrderByViewedTimeDesc(): List<RecentlyViewedProduct> {
        val recentlyViewedProducts = mutableListOf<RecentlyViewedProduct>()
        val limit = 10
        val cursor = db.rawQuery(
            """
            SELECT * FROM ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME}
            ORDER BY ${ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_VIEWED_TIME} DESC
            LIMIT $limit
        """.trimIndent(), null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val productId =
                cursor.getLong(cursor.getColumnIndexOrThrow(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID))
            val viewedTime =
                cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_VIEWED_TIME))
            val recentlyViewedProduct = RecentlyViewedProduct(
                productRepository.findById(productId)
                    ?: throw IllegalArgumentException("참조 무결성 제약조건 위반"),
                LocalDateTime.parse(viewedTime)
            ).apply { this.id = id }
            recentlyViewedProducts.add(recentlyViewedProduct)
        }

        cursor.close()
        return recentlyViewedProducts
    }
}