package woowacourse.shopping.ui.cart.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.repository.cart.InMemoryCartRepository
import woowacourse.shopping.ui.cart.component.CartBody
import woowacourse.shopping.ui.cart.component.CartTopAppBar
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            CartTopAppBar(
                onClick = { onClose() },
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->
        CartBody(
            visibleProducts = viewModel.visibleProducts(),
            totalProductCount = viewModel.cartProducts.size,
            currentPageIndex = viewModel.currentPageIndex,
            canNavigateToLeft = viewModel.canNavigateToLeft(),
            canNavigateToRight = viewModel.canNavigateToRight(),
            onIncreaseProduct = viewModel::addProduct,
            onDecreaseProduct = viewModel::decraseProduct,
            innerPadding = innerPadding,
            onDeleteProduct = viewModel::deleteProduct,
            onMoveToPreviousPage = viewModel::moveToPreviousPage,
            onMoveToNextPage = viewModel::moveToNextPage,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    val viewModel =
        remember {
            CartViewModel(
                cartRepository = InMemoryCartRepository(),
            )
        }

    CartScreen(
        viewModel = viewModel,
        onClose = {},
    )
}
