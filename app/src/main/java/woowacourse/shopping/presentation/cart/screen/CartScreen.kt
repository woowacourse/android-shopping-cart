package woowacourse.shopping.presentation.cart.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.cart.CartStateHolder
import woowacourse.shopping.presentation.cart.component.CartContent
import woowacourse.shopping.presentation.cart.component.CartScaffold
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    stateHolder: CartStateHolder,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CartScaffold(
        onBack = onBack,
        modifier = modifier,
    ) {
        CartContent(
            cart = stateHolder.cart,
            currentPage = stateHolder.currentPage,
            hasMoreItems = stateHolder.hasMoreItems,
            onPreviousPageClick = { stateHolder.goToPreviousPage() },
            onNextPageClick = { stateHolder.goToNextPage() },
            hasPreviousPage = stateHolder.hasPreviousPage,
            hasNextPage = stateHolder.hasNextPage,
            onDelete = { productId -> stateHolder.deleteProduct(productId) },
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        stateHolder =
            CartStateHolder(
                cartRepository = AppContainer.cartRepository,
                initialPageIndex = 1,
                onPageIndexChanged = {},
            ),
        onBack = {},
    )
}
