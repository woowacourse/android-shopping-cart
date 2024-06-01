package woowacourse.shopping.view.detail

sealed interface ProductDetailState {
    data class Success(val updatedProductId: Long, val updatedValue: Int) : ProductDetailState

    data object Fail : ProductDetailState
}
