package woowacourse.shopping.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import woowacourse.shopping.CatalogViewModel
import woowacourse.shopping.R
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.component.frame.CommonFrame
import woowacourse.shopping.ui.component.item.RecentlyViewedSection
import woowacourse.shopping.ui.component.item.ShoppingItem
import woowacourse.shopping.ui.stateholder.CatalogItemUiState
import java.util.UUID

@Composable
fun MainScreen(
    viewModel: CatalogViewModel,
    onItemClick: (UUID) -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val products by viewModel.products.collectAsState()
    val recentProducts by viewModel.recentProducts.collectAsState()
    val cart by viewModel.cart.collectAsState()

    CatalogScreen(
        catalog = products.map { product ->
            CatalogItemUiState(
                product,
                cart.cartProducts.findSameProduct(product.productId)?.amount ?: 0
            )
        },
        cartTotalAmount = cart.getTotalQuantity(),
        recentProducts = recentProducts,
        onItemClick = onItemClick,
        onCartClick = onCartClick,
        onIncrease = { id ->
            val product = products.find { it.productId == id }
            if (product != null) viewModel.addProductToCart(product)
        },
        onDecrease = { id -> viewModel.decreaseProductInCart(id) },
        onLoadClick = { viewModel.loadProducts() },
        modifier = modifier,
    )
}

@Composable
fun CatalogScreen(
    catalog: List<CatalogItemUiState>,
    recentProducts: List<Product>,
    cartTotalAmount: Int,
    onItemClick: (UUID) -> Unit,
    onCartClick: () -> Unit,
    onIncrease: (UUID) -> Unit,
    onDecrease: (UUID) -> Unit,
    onLoadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonFrame(
        headerContent = {
            CatalogHeader(
                onCartClick = onCartClick,
                cartTotalAmount = cartTotalAmount,
            )
        },
        bodyContent = {
            CatalogBody(
                catalog = catalog,
                recentProducts = recentProducts,
                onItemClick = onItemClick,
                onLoadClick = onLoadClick,
                onDecrease = onDecrease,
                onIncrease = onIncrease,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun CatalogHeader(
    onCartClick: () -> Unit,
    cartTotalAmount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = "Shopping",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier,
        )
        {
            Icon(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = "장바구니 아이콘",
                tint = Color.White,
                modifier =
                    Modifier
                        .size(24.dp)
                        .clickable(onClick = onCartClick),
            )
            if (cartTotalAmount != 0) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(
                            color = Color(0xFF04C09E),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cartTotalAmount.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CatalogBody(
    catalog: List<CatalogItemUiState>,
    recentProducts: List<Product>,
    onItemClick: (UUID) -> Unit,
    onIncrease: (UUID) -> Unit,
    onDecrease: (UUID) -> Unit,
    onLoadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                RecentlyViewedSection(
                    recentProducts = recentProducts,
                    onItemClick = onItemClick
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color(0xFFF0F0F0))
                )
            }
        }

        items(
            count = catalog.size,
            key = { index -> catalog[index].product.productId },
        ) { index ->
            val itemUiState = catalog[index]
            ShoppingItem(
                product = itemUiState.product,
                onClick = onItemClick,
                onIncrease = onIncrease,
                onDecrease = onDecrease,
                quantity = itemUiState.quantity,
            )
        }

        item(
            span = { GridItemSpan(maxLineSpan) },
        ) {
            LoadBtn(onLoadClick)
        }
    }
}

@Composable
private fun LoadBtn(
    onLoad: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(R.drawable.ic_add),
        contentDescription = "더보기 버튼",
        tint = Color.White,
        modifier =
            modifier
                .padding(25.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.LightGray)
                .clickable(onClick = onLoad),
    )
}

@Preview(showBackground = true)
@Composable
private fun CatalogScreenPreview() {
    CatalogScreen(
        emptyList(), emptyList(),0, {}, {}, {}, {}, {})
}
