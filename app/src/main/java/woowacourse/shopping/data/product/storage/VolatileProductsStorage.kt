package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.entity.CartItemEntity

object VolatileProductsStorage : ProductsStorage {
    private val products: List<CartItemEntity> =
        listOf(
            CartItemEntity(id = 1, name = "럭키", price = 4000, quantity = 0),
            CartItemEntity(id = 2, name = "아이다", price = 700, quantity = 0),
            CartItemEntity(id = 3, name = "설백", price = 1_000, quantity = 0),
            CartItemEntity(id = 4, name = "줌마", price = 1_000, quantity = 0),
            CartItemEntity(id = 5, name = "잭슨", price = 20_000, quantity = 0),
            CartItemEntity(id = 6, name = "곰도로스", price = 300, quantity = 0),
            CartItemEntity(id = 7, name = "봉추", price = 3_800, quantity = 0),
            CartItemEntity(id = 8, name = "비앙카", price = 36_000, quantity = 0),
            CartItemEntity(id = 9, name = "비앙카1", price = 36_000, quantity = 0),
            CartItemEntity(id = 10, name = "비앙카2", price = 36_000, quantity = 0),
            CartItemEntity(id = 11, name = "비앙카3", price = 36_000, quantity = 0),
            CartItemEntity(id = 12, name = "비앙카4", price = 36_000, quantity = 0),
            CartItemEntity(id = 13, name = "비앙카5", price = 36_000, quantity = 0),
            CartItemEntity(id = 14, name = "비앙카6", price = 36_000, quantity = 0),
            CartItemEntity(id = 15, name = "비앙카7", price = 36_000, quantity = 0),
            CartItemEntity(id = 16, name = "비앙카8", price = 36_000, quantity = 0),
            CartItemEntity(id = 17, name = "비앙카9", price = 36_000, quantity = 0),
            CartItemEntity(id = 18, name = "비앙카10", price = 36_000, quantity = 0),
            CartItemEntity(id = 19, name = "비앙카11", price = 36_000, quantity = 0),
            CartItemEntity(id = 20, name = "비앙카12", price = 36_000, quantity = 0),
            CartItemEntity(id = 21, name = "비앙카13", price = 36_000, quantity = 0),
            CartItemEntity(id = 22, name = "비앙카14", price = 36_000, quantity = 0),
            CartItemEntity(id = 23, name = "비앙카15", price = 36_000, quantity = 0),
            CartItemEntity(id = 24, name = "비앙카16", price = 36_000, quantity = 0),
            CartItemEntity(id = 25, name = "비앙카17", price = 36_000, quantity = 0),
        )

    override fun load(): List<CartItemEntity> = products
}
