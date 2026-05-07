package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import woowacourse.shopping.domain.CART_PAGE_SIZE
import woowacourse.shopping.domain.PageRequest
import woowacourse.shopping.domain.ProductAndCount
import woowacourse.shopping.domain.toPage
import woowacourse.shopping.ui.cart.state.CartState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CartBody(
    state: CartState,
    innerPadding: PaddingValues,
    cartItems: List<ProductAndCount>,
    onDeleteProduct: (Uuid) -> Unit,
    modifier: Modifier,
) {
    Box(modifier = modifier.padding(innerPadding)) {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(state.visibleProducts(cartItems)) { productAndCount ->
                    CartItem(
                        productAndCount = productAndCount,
                        onDelete = onDeleteProduct,
                    )
                }
            }
            if (cartItems.size > CART_PAGE_SIZE) {
                Pagination(
                    pageMoveToLeft = { state.canNavigateToLeft() },
                    pageMoveToLeftButtonEnabled = state.canNavigateToLeft(),
                    currentPageIndex = state.currentPageIndex,
                    pageMoveToRight = { state.canNavigateToRight(cartItems) },
                    pageMoveToRightButtonEnabled = state.currentPageIndex < state.lastPageIndex(cartItems),
                )
            }
        }
    }
}
