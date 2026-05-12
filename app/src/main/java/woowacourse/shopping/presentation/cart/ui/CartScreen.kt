package woowacourse.shopping.presentation.cart.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.R
import woowacourse.shopping.presentation.cart.model.CartUiState
import woowacourse.shopping.presentation.cart.ui.components.CartContent
import woowacourse.shopping.presentation.cart.ui.components.CartPageSection
import woowacourse.shopping.presentation.common.ShoppingAppBar

@Composable
fun CartScreen(
    uiState: CartUiState,
    onBack: () -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    onDeleteItem: (Long) -> Unit,
    onIncrease: (Long) -> Unit,
    onDecrease: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            ShoppingAppBar(
                contents = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable { onBack() },
                    )
                    Spacer(modifier = Modifier.width(21.dp))
                    Text(
                        text = "Cart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                    )
                },
            )
        },
        bottomBar = {
            if (uiState.isShowPageSection) {
                CartPageSection(
                    page = uiState.page + 1,
                    onNext = { onNextPage() },
                    onPrevious = { onPreviousPage() },
                    isCanMoveNext = uiState.isCanMoveNext,
                )
            }
        },
        modifier = modifier.statusBarsPadding(),
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            if (uiState.isLoading) CircularProgressIndicator()
            CartContent(
                onDeleteItem = { onDeleteItem(it) },
                cartItems = uiState.currentCartItems.toImmutableList(),
                onIncrease = { onIncrease(it) },
                onDecrease = { onDecrease(it) },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        uiState = CartUiState(),
        onBack = {},
        onDecrease = {},
        onIncrease = {},
        onDeleteItem = {},
        onNextPage = {},
        onPreviousPage = {},
    )
}
