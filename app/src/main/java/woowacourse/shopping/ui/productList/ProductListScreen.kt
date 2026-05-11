package woowacourse.shopping.ui.productList

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.constant.Format.formatPrice
import woowacourse.shopping.constant.ShoppingColor.APP_BAR_COLOR
import woowacourse.shopping.data.preview.FakeProductRepository
import woowacourse.shopping.data.preview.FakeRecentProductRepository
import woowacourse.shopping.data.repository.cart.MockCartRepository
import woowacourse.shopping.domain.product.Product
import androidx.compose.foundation.lazy.grid.items as lazyGridItems
import androidx.compose.foundation.lazy.items as lazyRowItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel,
    onCartClick: () -> Unit = {},
    onProductClick: (Product) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartCount = (uiState as? ProductListUiState.Success)?.totalCartCount ?: 0

    Column(modifier = modifier) {
        ProductListTopAppBar(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            cartCount = cartCount,
            onClick = onCartClick,
        )

        when (val state = uiState) {
            is ProductListUiState.Loading -> {
                LoadingContent(modifier = Modifier.fillMaxSize())
            }

            is ProductListUiState.Success -> {
                ProductListContent(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                    visibleProducts = state.products,
                    recentProducts = state.recentProducts,
                    quantitiesByProductId = state.quantitiesByProductId,
                    canLoadMore = state.canLoadMore,
                    isLoadingMore = state.isLoadingMore,
                    onProductClick = onProductClick,
                    onAddClick = { product -> viewModel.addProduct(product) },
                    onIncrease = { productId -> viewModel.increase(productId) },
                    onDecrease = { productId -> viewModel.decrease(productId) },
                    onMoreClick = { viewModel.moreProducts() },
                )
            }

            is ProductListUiState.Error -> {
                ErrorContent(
                    modifier = Modifier.fillMaxSize(),
                    message = state.message,
                )
            }
        }
    }
}

@Composable
private fun ProductListContent(
    visibleProducts: List<Product>,
    recentProducts: List<Product>,
    quantitiesByProductId: Map<String, Int>,
    canLoadMore: Boolean,
    isLoadingMore: Boolean,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit = {},
    onAddClick: (Product) -> Unit = {},
    onIncrease: (String) -> Unit = {},
    onDecrease: (String) -> Unit = {},
    onMoreClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        if (recentProducts.isNotEmpty()) {
            RecentProductsSection(
                recentProducts = recentProducts,
                onProductClick = onProductClick,
            )
        }
        ProductCardGrid(
            visibleProducts = visibleProducts,
            quantitiesByProductId = quantitiesByProductId,
            canLoadMore = canLoadMore,
            isLoadingMore = isLoadingMore,
            modifier = Modifier.weight(1f),
            onProductClick = onProductClick,
            onAddClick = onAddClick,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            onMoreClick = onMoreClick,
        )
    }
}

@Composable
private fun RecentProductsSection(
    recentProducts: List<Product>,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(bottom = 20.dp)) {
        Text(
            text = "최근 본 상품",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        LazyRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            lazyRowItems(
                items = recentProducts,
                key = { product -> product.id },
            ) { product ->
                RecentProductCard(
                    product = product,
                    onClick = { onProductClick(product) },
                )
            }
        }
    }
}

@Composable
private fun RecentProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(96.dp)
                .clickable(onClick = onClick),
    ) {
        AsyncImage(
            model = product.imageUrl.value,
            contentDescription = product.name.value,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(Color(0xFFF4F4F4)),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = product.name.value,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun CartBadgeIcon(
    cartCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "장바구니 아이콘",
            )
        }
        if (cartCount > 0) {
            Box(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 6.dp, end = 4.dp)
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1ABC9C)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "$cartCount",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            color = Color.Gray,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListTopAppBar(
    cartCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Shopping",
                fontSize = 20.sp,
            )
        },
        actions = {
            CartBadgeIcon(
                cartCount = cartCount,
                onClick = onClick,
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color(APP_BAR_COLOR),
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
            ),
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}

@Composable
private fun ProductCardGrid(
    visibleProducts: List<Product>,
    quantitiesByProductId: Map<String, Int>,
    canLoadMore: Boolean,
    isLoadingMore: Boolean,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit = {},
    onAddClick: (Product) -> Unit = {},
    onIncrease: (String) -> Unit = {},
    onDecrease: (String) -> Unit = {},
    onMoreClick: () -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        lazyGridItems(
            items = visibleProducts,
            key = { item -> item.id },
        ) { item ->
            ProductCard(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = item.imageUrl.value,
                productName = item.name.value,
                price = item.price.value,
                quantity = quantitiesByProductId[item.id] ?: 0,
                onClick = { onProductClick(item) },
                onAddClick = { onAddClick(item) },
                onIncrease = { onIncrease(item.id) },
                onDecrease = { onDecrease(item.id) },
            )
        }
        if (isLoadingMore) {
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (canLoadMore) {
            item(span = { GridItemSpan(2) }) {
                MoreButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 20.dp)
                            .background(
                                color = Color(0xFF555555),
                                shape = RoundedCornerShape(size = 45.dp),
                            ),
                ) {
                    onMoreClick()
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    productName: String,
    price: Int,
    imageUrl: String,
    quantity: Int,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "상품 이미지",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(154.dp),
                contentScale = ContentScale.Crop,
            )
            ProductCardQuantityControl(
                quantity = quantity,
                onAddClick = onAddClick,
                onIncrease = onIncrease,
                onDecrease = onDecrease,
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
            )
        }
        ProductInfoColumn(
            modifier = Modifier.padding(start = 6.dp, end = 9.dp, top = 8.dp, bottom = 12.dp),
            productName = productName,
            price = price,
        )
    }
}

@Composable
private fun ProductInfoColumn(
    productName: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            productName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            formatPrice(price),
            fontSize = 16.sp,
            color = Color.Gray,
        )
    }
}

@Composable
private fun MoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text("더보기")
    }
}

@Composable
private fun ProductCardQuantityControl(
    quantity: Int,
    onAddClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (quantity == 0) {
        AddCircleButton(onClick = onAddClick, modifier = modifier)
    } else {
        InlineStepper(
            quantity = quantity,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            modifier = modifier,
        )
    }
}

@Composable
private fun AddCircleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFFB0B0B0))
                .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "+",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
private fun InlineStepper(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .height(32.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperSign(symbol = "-", onClick = onDecrease)
        Box(
            modifier =
                Modifier
                    .width(28.dp)
                    .height(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "$quantity",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
            )
        }
        StepperSign(symbol = "+", onClick = onIncrease)
    }
}

@Composable
private fun StepperSign(
    symbol: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(width = 28.dp, height = 32.dp)
                .clickable { onClick() }
                .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = symbol,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    ProductListScreen(
        viewModel =
            ProductListViewModel(
                productRepository = FakeProductRepository(),
                cartRepository = MockCartRepository(),
                recentProductRepository = FakeRecentProductRepository(),
            ),
    )
}
