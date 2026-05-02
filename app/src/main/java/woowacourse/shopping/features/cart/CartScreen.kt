package woowacourse.shopping.features.cart

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import woowacourse.shopping.features.constant.Format.formatPrice
import woowacourse.shopping.features.constant.ShoppingColor.APP_BAR_COLOR
import woowacourse.shopping.features.constant.ShoppingColor.CART_PAGE_BUTTON_ACTIVE_COLOR
import woowacourse.shopping.features.constant.ShoppingColor.CART_PAGE_BUTTON_INACTIVE_COLOR
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.data.cart.CartRepositoryMockImpl

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel,
) {
    val cartUiState by viewModel.uiState.collectAsState()
    val activity = LocalActivity.current

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        CartTopAppBar(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            onClick = {
                activity?.finish()
            },
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 4.dp),
        ) {
            items(cartUiState.cartItems) { cartItem ->
                CartItemCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    cartItem = cartItem,
                    onRemoveClick = {
                        viewModel.removeCartItem(cartItem)
                    },
                )
            }
        }

        if (cartUiState.totalPages > 1) {
            PageNavigator(
                currentPage = cartUiState.currentPage,
                hasPrevious = cartUiState.hasPrevious,
                hasNext = cartUiState.hasNext,
                onPreviousClick = { viewModel.goToPreviousPage() },
                onNextClick = { viewModel.goToNextPage() },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopAppBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Cart",
                fontSize = 20.sp,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로 가기",
                    tint = Color.White,
                )
            }
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
private fun CartItemCard(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    onRemoveClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Color(0xFFD0D0D0)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = cartItem.product.name.value,
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A4A4A),
                )
                IconButton(
                    onClick = onRemoveClick,
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        imageVector = Default.Close,
                        contentDescription = "삭제",
                        tint = Color(0xFFB0B0B0),
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
            ) {
                ProductImage(
                    imageUrl = cartItem.product.imageUrl.value,
                    modifier = Modifier.size(width = 72.dp, height = 64.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = formatPrice(cartItem.product.price.value),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
        }
    }
}

@Composable
private fun PageNavigator(
    currentPage: Int,
    hasPrevious: Boolean,
    hasNext: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Button(
            onClick = onPreviousClick,
            enabled = hasPrevious,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(CART_PAGE_BUTTON_ACTIVE_COLOR),
                    disabledContainerColor = Color(CART_PAGE_BUTTON_INACTIVE_COLOR),
                ),
        ) {
            Text(
                text = "<",
                color = Color.White,
            )
        }
        Text(
            text = "${currentPage + 1}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
        Button(
            onClick = onNextClick,
            enabled = hasNext,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(CART_PAGE_BUTTON_ACTIVE_COLOR),
                    disabledContainerColor = Color(CART_PAGE_BUTTON_INACTIVE_COLOR),
                ),
        ) {
            Text(
                text = ">",
                color = Color.White,
            )
        }
    }
}

@Composable
private fun ProductImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "상품 이미지",
        modifier =
            modifier
                .background(Color(0xFFF1F1F1))
                .border(1.dp, Color(0xFFE4E4E4)),
    )
}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    val viewModel = CartViewModel(CartRepositoryMockImpl)

    CartScreen(
        viewModel = viewModel,
    )
}
