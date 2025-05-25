package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity

class CartDataSourceImpl(
    private val dao: CartDao,
) : CartDataSource {
    override fun getCartProductCount(): Result<Int> =
        try {
            Result.success(dao.getAllProductCount())
        } catch (e: Exception) {
            Result.failure(Exception("장바구니 상품 개수 조회에 실패했습니다: $e"))
        }

    override fun getTotalQuantity(): Result<Int?> =
        try {
            Result.success(dao.getTotalQuantity())
        } catch (e: Exception) {
            Result.failure(Exception("장바구니 전체 상품 개수 조회에 실패했습니다: $e"))
        }

    override fun getQuantityById(productId: Long): Result<Int> =
        try {
            Result.success(dao.getQuantityById(productId))
        } catch (e: Exception) {
            Result.failure(Exception("장바구니의 수량 조회에 실패했습니다: $e"))
        }

    override fun getPagedCartProducts(
        limit: Int,
        page: Int,
    ): Result<List<CartEntity>> {
        val offset = limit * page
        return try {
            Result.success(dao.getPagedProducts(limit, offset))
        } catch (e: Exception) {
            Result.failure(Exception("현재 페이지 장바구니 상품 조회에 실패했습니다: $e"))
        }
    }

    override fun existsByProductId(productId: Long): Result<Boolean> =
        try {
            Result.success(dao.existsByProductId(productId))
        } catch (e: Exception) {
            Result.failure(Exception("장바구니 상품 조회에 실패했습니다: $e"))
        }

    override fun increaseQuantity(
        productId: Long,
        quantity: Int,
    ): Result<Unit> =
        try {
            Result.success(dao.increaseQuantity(productId, quantity))
        } catch (e: Exception) {
            Result.failure(Exception("장바구니 수량 증가에 실패했습니다: $e"))
        }

    override fun decreaseQuantity(productId: Long): Result<Unit> =
        try {
            Result.success(dao.decreaseQuantity(productId))
        } catch (e: Exception) {
            Result.failure(Exception("장바구니 수량 감소에 실패했습니다: $e"))
        }

    override fun insertProduct(cartEntity: CartEntity): Result<Unit> =
        try {
            Result.success(dao.insertProduct(cartEntity))
        } catch (e: Exception) {
            Result.failure(Exception("장바구니 추가에 실패했습니다: $e"))
        }

    override fun deleteProductById(productId: Long): Result<Unit> =
        try {
            Result.success(dao.deleteProductById(productId))
        } catch (e: Exception) {
            Result.failure(Exception("장바구니에서 상품 삭제에 실패했습니다: $e"))
        }
}
