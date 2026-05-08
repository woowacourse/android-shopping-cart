package woowacourse.shopping.presentation.cart.screen

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import woowacourse.shopping.app.AppContainer
import woowacourse.shopping.presentation.cart.CartUiEvent
import woowacourse.shopping.presentation.cart.CartViewModel
import woowacourse.shopping.presentation.cart.component.CartContent
import woowacourse.shopping.presentation.cart.component.CartScaffold
import woowacourse.shopping.presentation.cart.component.DeleteProductDialog
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CartUiEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var deleteProductId by remember { mutableStateOf<Uuid?>(null) }

    CartScaffold(
        onBack = onBack,
        modifier = modifier,
    ) {
        CartContent(
            cart = uiState.cart,
            currentPage = uiState.currentPage,
            hasMoreItems = viewModel.hasMoreItems,
            onPreviousPageClick = viewModel::goToPreviousPage,
            onNextPageClick = viewModel::goToNextPage,
            hasPreviousPage = viewModel.hasPreviousPage,
            hasNextPage = viewModel.hasNextPage,
            onDelete = { productId ->
                deleteProductId = productId
            },
            onQuantityIncrease = viewModel::increaseQuantity,
            onQuantityDecrease = { productId, quantity ->
                if (quantity == 1) {
                    deleteProductId = productId
                } else {
                    viewModel.decreaseQuantity(productId)
                }
            },
        )
    }

    if (deleteProductId != null) {
        DeleteProductDialog(
            onDismissRequest = { deleteProductId = null },
            onConfirm = {
                deleteProductId?.let(viewModel::deleteProduct)
                deleteProductId = null
            },
            onDismiss = { deleteProductId = null },
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        viewModel = CartViewModel(AppContainer.cartRepository),
        onBack = {},
    )
}
