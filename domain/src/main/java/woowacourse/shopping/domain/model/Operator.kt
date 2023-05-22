package woowacourse.shopping.domain.model

enum class Operator {
    INCREASE {
        override fun operate(productInCart: ProductInCart): ProductInCart {
            return productInCart.increase()
        }
    },
    DECREASE {
        override fun operate(productInCart: ProductInCart): ProductInCart {
            return productInCart.decrease()
        }
    }, ;

    abstract fun operate(productInCart: ProductInCart): ProductInCart
}
