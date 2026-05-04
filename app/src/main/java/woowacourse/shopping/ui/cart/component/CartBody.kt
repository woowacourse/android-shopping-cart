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
import woowacourse.shopping.repository.CartRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
@Composable
fun CartBody(
    innerPadding: PaddingValues,
    cartItems: List<ProductAndCount>,
    currentPageIndex: Int,
    lastPageIndex: Int,
    onMoveToPreviousPage: () -> Unit,
    onMoveToNextPage: () -> Unit,
    onDeleteProduct: (Uuid) -> Unit,
    modifier: Modifier,
) {
    val visibleProducts = cartItems.toPage(
        PageRequest(
            index = currentPageIndex,
            size = CART_PAGE_SIZE
        )
    )
    Box(modifier = modifier.padding(innerPadding)) {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(visibleProducts.items) { productAndCount ->
                    cartItem(
                        productAndCount = productAndCount,
                        onDelete = onDeleteProduct
                    )
                }
            }
            if (cartItems.size > 5) {
                pagination(
                    pageMoveToLeft = { onMoveToPreviousPage() },
                    pageMoveToLeftButtonEnabled = currentPageIndex > 0,
                    currentPageIndex = currentPageIndex,
                    pageMoveToRight = { onMoveToNextPage() },
                    pageMoveToRightButtonEnabled = currentPageIndex < lastPageIndex,
                )
            }
        }
    }
}
