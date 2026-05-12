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
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductWithQuantity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CartBody(
    visibleProducts: List<ProductWithQuantity>,
    totalProductCount: Int,
    currentPageIndex: Int,
    canNavigateToLeft: Boolean,
    canNavigateToRight: Boolean,
    onIncreaseProduct: (Product) -> Unit,
    onDecreaseProduct: (Uuid) -> Unit,
    innerPadding: PaddingValues,
    onDeleteProduct: (Uuid) -> Unit,
    onMoveToPreviousPage: () -> Unit,
    onMoveToNextPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.padding(innerPadding)) {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(visibleProducts) { productWithQuantity ->
                    CartItem(
                        productWithQuantity = productWithQuantity,
                        onIncrease = { onIncreaseProduct(productWithQuantity.product) },
                        onDecrease = { onDecreaseProduct(productWithQuantity.product.productId) },
                        onDelete = onDeleteProduct,
                    )
                }
            }
            if (totalProductCount > CART_PAGE_SIZE) {
                Pagination(
                    pageMoveToLeft = onMoveToPreviousPage,
                    pageMoveToLeftButtonEnabled = canNavigateToLeft,
                    currentPageIndex = currentPageIndex,
                    pageMoveToRight = onMoveToNextPage,
                    pageMoveToRightButtonEnabled = canNavigateToRight,
                )
            }
        }
    }
}
