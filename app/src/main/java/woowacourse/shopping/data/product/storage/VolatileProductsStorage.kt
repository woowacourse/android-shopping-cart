package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.entity.ProductEntity

object VolatileProductsStorage : ProductsStorage {
    override val products: List<ProductEntity>
        get() =
            listOf(
                ProductEntity(1, "럭키", 4000),
                ProductEntity(2, "아이다", 700),
                ProductEntity(3, "설백", 1_000),
                ProductEntity(4, "줌마", 1_000),
                ProductEntity(5, "잭슨", 20_000),
                ProductEntity(6, "곰도로스", 300),
                ProductEntity(7, "봉추", 3_800),
                ProductEntity(8, "비앙카", 36_000),
            )
}
