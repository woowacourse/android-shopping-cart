package woowacourse.shopping.database.recentlyviewedproduct

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository

class RecentlyViewedProductRepositoryImpl(
    context: Context,
    private val productRepository: ProductRepository,
) : RecentlyViewedProductRepository {
    private val db = RecentlyViewedProductDbHelper(context).writableDatabase

    override fun findAll(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "Select * from ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME}",
            null,
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            productRepository.findById(id)?.let { products.add(it) }
        }

        cursor.close()
        return products
    }

    override fun save(product: Product) {
        // 중복된 상품이 있을 때 삭제하고
        val selection = "${ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(product.id.toString())
        db.delete(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, selection, selectionArgs)

        // 상품 추가
        val value = ContentValues().apply {
            put(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID, product.id)
        }
        db.insert(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, null, value)

        // 10개를 넘어가면 오래된 상품 삭제
        val cursor: Cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME}",
            null,
        )
        cursor.moveToNext()
        cursor.getInt(0).takeIf { it > 10 }?.run {
            val selection1 =
                "${ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID} = ?"
            val cursor1: Cursor = db.rawQuery(
                "SELECT * FROM ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME} LIMIT 1",
                null,
            )
            val firstProductId = cursor1.getInt(0)
            val selectionArgs1 = arrayOf(firstProductId.toString())
            db.delete(
                ProductContract.RecentlyViewedProductEntry.TABLE_NAME,
                selection1,
                selectionArgs1,
            )
        }

        cursor.close()
    }
}
