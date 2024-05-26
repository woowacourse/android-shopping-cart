package woowacourse.shopping.presentation.ui.shopping

enum class ShoppingError(
    val message: String,
) {
    AllProductsLoaded("상품을 모두 불러왔습니다."),
    ProductItemsNotFound("상품을 찾을 수 없습니다."),
    RecentProductItemsNotFound("최근 본 상품을 불러올 수 없습니다."),
    CartItemsNotFound("장바구니를 불러올 수 없습니다."),
    CartItemsNotModified("상품 수량을 변경할 수 없습니다."),
}
