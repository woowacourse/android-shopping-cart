package woowacourse.shopping.presentation.ui

enum class Error(
    val message: String,
) {
    AllProductsLoaded("아이템을 모두 불러왔습니다"),
    ProductNotFound("아이템을 찾을 수 없습니다"),
}
