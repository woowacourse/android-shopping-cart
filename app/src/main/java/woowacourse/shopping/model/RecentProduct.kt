package woowacourse.shopping.model

data class RecentProduct(
    val productId: ProductId,
    val viewedAtMillis: Long,
) {
    init {
        require(viewedAtMillis >= 0) { "최근 본 상품 조회 시각은 0 이상이어야 합니다." }
    }
}
