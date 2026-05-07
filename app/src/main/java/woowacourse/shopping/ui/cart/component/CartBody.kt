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
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CartBody(
    viewModel: CartViewModel,
    innerPadding: PaddingValues,
    onDeleteProduct: (Uuid) -> Unit,
    modifier: Modifier,
) {
    Box(modifier = modifier.padding(innerPadding)) {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(viewModel.visibleProducts()) { productWithQuantity ->
                    CartItem(
                        productWithQuantity = productWithQuantity,
                        onIncrease = { viewModel.addProduct(product = productWithQuantity.product) },
                        onDecrease = { viewModel.decraseProduct(productId = productWithQuantity.product.productId) },
                        onDelete = onDeleteProduct,
                    )
                }
            }
            if (viewModel.getCartProducts().size > CART_PAGE_SIZE) {
                Pagination(
                    pageMoveToLeft = { viewModel.moveToPreviousPage() },
                    pageMoveToLeftButtonEnabled = viewModel.canNavigateToLeft(),
                    currentPageIndex = viewModel.currentPageIndex,
                    pageMoveToRight = { viewModel.moveToNextPage() },
                    pageMoveToRightButtonEnabled = viewModel.canNavigateToRight(),
                )
            }
        }
    }
}
