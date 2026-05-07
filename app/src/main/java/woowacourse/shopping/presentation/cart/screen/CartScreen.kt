package woowacourse.shopping.presentation.cart.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.cart.component.CartContent
import woowacourse.shopping.presentation.cart.component.CartScaffold
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    cart: Cart,
    currentPage: Int,
    hasMoreItems: Boolean,
    onPreviousPageClick: () -> Unit,
    onNextPageClick: () -> Unit,
    hasPreviousPage: Boolean,
    hasNextPage: Boolean,
    onDelete: (Uuid) -> Unit,
    onQuantityIncrease: (Product) -> Unit,
    onQuantityDecrease: (Uuid) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CartScaffold(
        onBack = onBack,
        modifier = modifier,
    ) {
        CartContent(
            cart = cart,
            currentPage = currentPage,
            hasMoreItems = hasMoreItems,
            onPreviousPageClick = onPreviousPageClick,
            onNextPageClick = onNextPageClick,
            hasPreviousPage = hasPreviousPage,
            hasNextPage = hasNextPage,
            onDelete = onDelete,
            onQuantityIncrease = onQuantityIncrease,
            onQuantityDecrease = onQuantityDecrease,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        cart = Cart(),
        currentPage = 1,
        hasMoreItems = false,
        onPreviousPageClick = {},
        onNextPageClick = {},
        hasPreviousPage = false,
        hasNextPage = false,
        onDelete = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
        onBack = {},
    )
}
