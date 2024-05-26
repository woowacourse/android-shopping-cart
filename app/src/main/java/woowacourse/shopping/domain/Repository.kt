package woowacourse.shopping.domain

interface Repository {
    fun findProductByPaging(
        offset: Int,
        pageSize: Int,
    ): Result<List<CartProduct>>

    fun findCartByPaging(
        offset: Int,
        pageSize: Int,
    ): Result<List<CartProduct>>

    fun findByLimit(
        limit: Int
    ): Result<List<RecentProduct>>

    fun findProductById(id: Long): Result<CartProduct?>

    fun saveCart(cart: Cart): Result<Long>

    fun saveRecent(recent: Recent): Result<Long>

    fun deleteCart(id: Long): Result<Long>
}
