package woowacourse.shopping.data.carts.repository

sealed class CartError {
    object DatabaseError : CartError()

    object GeneralError : CartError()
}
