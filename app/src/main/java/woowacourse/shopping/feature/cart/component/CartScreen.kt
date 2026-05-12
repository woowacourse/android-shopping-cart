package woowacourse.shopping.feature.cart.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.feature.cart.CartEvent
import woowacourse.shopping.feature.cart.CartViewModel
import woowacourse.shopping.feature.common.state.ProductUiModel
import woowacourse.shopping.feature.productlist.LoadingIndicator

@Composable
fun CartScreen(
    onCloseClick: () -> Unit,
    activityFinish: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.Factory),
) {
    LaunchedEffect(Unit) {
        viewModel.initialLoading()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CartEvent.FatalError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    activityFinish()
                }
            }
        }
    }

    Scaffold(
        containerColor = Color.White,
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CartAppBar(onCloseClick = onCloseClick)
        },
    ) { innerPadding ->
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
            ) {
                CartItemList(
                    uiState.paginatedCartContents,
                    modifier = Modifier.weight(1f),
                    onDelete = viewModel::deleteCartItem,
                    onIncrease = { viewModel.increase(it) },
                    onDecrease = { viewModel.decrease(it) },
                )
                Spacer(modifier = Modifier.height(40.dp))
                PageNavigator(
                    page = uiState.page,
                    onLeftClick = viewModel::moveToPreviousPage,
                    onRightClick = viewModel::moveToNextPage,
                    canMoveToPreviousPage = !viewModel.isStartPage(),
                    canMoveToNextPage = !viewModel.isEndPage(),
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
            if (uiState.isLoading) {
                LoadingIndicator()
            }
        }
    }
}

@Composable
private fun PageNavigator(
    page: Int,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
    canMoveToPreviousPage: Boolean,
    canMoveToNextPage: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .height(height = 42.dp)
            .clip(RoundedCornerShape(4.dp)),
    ) {
        PageButton(
            text = "<",
            onClick = onLeftClick,
            isEnable = canMoveToPreviousPage,
        )

        Text(
            text = page.toString(),
            fontSize = 22.sp,
        )

        PageButton(
            text = ">",
            onClick = onRightClick,
            isEnable = canMoveToNextPage,
        )
    }
}

@Composable
private fun PageButton(
    text: String,
    onClick: () -> Unit,
    isEnable: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = isEnable,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff04c09e),
            contentColor = Color.White,
            disabledContainerColor = Color(0xffaaaaaa),
            disabledContentColor = Color.White,
        ),
        modifier = modifier.size(height = 42.dp, width = 42.dp),
        contentPadding = PaddingValues(10.dp),
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
        )
    }
}

@Composable
private fun CartItemList(
    cartContents: List<ProductUiModel>,
    onDelete: (String) -> Unit,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(
            key = { it.id },
            items = cartContents,
        ) {
            CartItem(
                imageUrl = it.imageUrl,
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                onDelete = {
                    onDelete(it.id)
                },
                onIncrease = {
                    onIncrease(it.id)
                },
                onDecrease = {
                    onDecrease(it.id)
                },
            )
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    CartScreen(
        onCloseClick = {},
        activityFinish = {},
    )
}

@Preview
@Composable
private fun PageNavigatorPreview() {
    PageNavigator(
        page = 1,
        onLeftClick = {},
        onRightClick = {},
        canMoveToPreviousPage = false,
        canMoveToNextPage = true,
    )
}

@Preview
@Composable
private fun PageButtonPreview() {
    PageButton(
        text = ">",
        onClick = {},
        isEnable = true,
    )
}

@Preview
@Composable
private fun CartItemListPreview() {
    CartItemList(
        cartContents = listOf(
            ProductUiModel(
                id = "1",
                name = "더미 상품 1",
                price = 10000,
                imageUrl = "",
                quantity = 1,
            ),
            ProductUiModel(
                id = "2",
                name = "더미 상품 2",
                price = 20000,
                imageUrl = "",
                quantity = 3,
            ),
        ),
        onDelete = {},
        onIncrease = {},
        onDecrease = {},
    )
}
