package woowacourse.shopping.presentation.cart.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.shopping.component.QuantitySelector
import woowacourse.shopping.presentation.theme.topAppBarColor
import woowacourse.shopping.util.intFormatter
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun CartProductItem(
    cartItem: CartItem,
    onDelete: (Int) -> Unit,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        shape = RectangleShape,
        modifier = modifier.padding(18.dp),
        border =
            BorderStroke(
                width = 1.dp,
                color = topAppBarColor,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 18.dp),
        ) {
            CartProductHeader(
                product = cartItem.product,
                onDelete = onDelete,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(20.dp))
            CartProductInfo(
                product = cartItem.product,
                quantity = cartItem.quantity,
                onIncrease = onQuantityIncrease,
                onDecrease = onQuantityDecrease,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun CartProductHeader(
    product: Product,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = product.productName,
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            color = topAppBarColor,
        )
        Image(
            painter = painterResource(id = R.drawable.x_icon),
            contentDescription = "xButton",
            modifier =
                Modifier
                    .size(16.dp)
                    .clickable {
                        onDelete(product.productId)
                    },
            colorFilter = ColorFilter.tint(topAppBarColor),
        )
    }
}

@Composable
private fun CartProductInfo(
    product: Product,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(72.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.productName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(136.dp, 72.dp),
        )
        CartProductPriceAndQuantity(
            product = product,
            quantity = quantity,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun CartProductPriceAndQuantity(
    product: Product,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        QuantitySelector(
            quantity = quantity,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
        )
        Box {
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = "${intFormatter(product.price.value * quantity)}원",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = topAppBarColor,
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun CartProductItemPreview() {
    CartProductItem(
        cartItem =
            CartItem(
                Product(
                    productId = 1,
                    imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                    productName = "[든든] 동원 스위트콘",
                    price = Price(99800),
                ),
                quantity = 1,
            ),
        onDelete = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
    )
}
