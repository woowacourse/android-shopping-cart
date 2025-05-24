package woowacourse.shopping.view.main.state

enum class CartSavingState {
    SAVED,
    NOT_SAVED,
    ;

    companion object {
        fun of(value: Boolean) = if (value) SAVED else NOT_SAVED
    }
}
