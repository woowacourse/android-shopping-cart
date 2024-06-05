package woowacourse.shopping.presentation.ui

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Recent
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.Repository
import kotlin.math.min

val cartProducts =
    List(51) { id ->
        CartProduct(
            productId = id.toLong(),
            name = "$id",
            imgUrl = "https://image.utoimage.com/preview/cp872722/2022/12/202212008462_500.jpg",
            price = 10000,
            quantity = 1,
        )
    }

val cartProduct: CartProduct = cartProducts.first()

val recentProducts =
    List(10) { id ->
        RecentProduct(
            productId = id.toLong(),
            name = "$id",
            imgUrl = "www.naver.com",
            price = 10000,
            createdAt = System.currentTimeMillis(),
        )
    }

val recentProduct: RecentProduct = recentProducts.first()

val fakeRepository =
    object : Repository {
        val carts = mutableListOf<Cart>()

        override fun findProductByPaging(
            offset: Int,
            pageSize: Int,
        ): Result<List<CartProduct>> = Result.success(cartProducts)

        override fun findProductByPagingWithMock(
            offset: Int,
            pageSize: Int,
        ): Result<List<CartProduct>> = Result.success(cartProducts)

        override fun findCartByPaging(
            offset: Int,
            pageSize: Int,
        ): Result<List<CartProduct>> =
            Result.success(
                carts.map {
                    CartProduct(
                        productId = it.productId,
                        name = "${it.productId}",
                        imgUrl = "www.naver.com",
                        price = 10000,
                        quantity = it.quantity,
                    )
                }.subList(offset * pageSize, min(offset * pageSize + pageSize, carts.size)),
            )

        override fun findByLimit(limit: Int): Result<List<RecentProduct>> = Result.success(recentProducts)

        override fun findOne(): Result<RecentProduct?> = Result.success(recentProduct)

        override fun findProductById(id: Long): Result<CartProduct?> =
            Result.success(
                cartProduct,
            )

        override fun saveCart(cart: Cart): Result<Long> =
            runCatching {
                carts.add(cart)
                cart.productId
            }

        override fun saveRecent(recent: Recent): Result<Long> = Result.success(1)

        override fun deleteCart(id: Long): Result<Long> = Result.success(1)

        override fun getMaxCartCount(): Result<Int> =
            runCatching {
                carts.size
            }
    }
