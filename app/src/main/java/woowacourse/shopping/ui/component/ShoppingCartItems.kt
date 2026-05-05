@file:Suppress("FunctionName")

package woowacourse.shopping.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import woowacourse.shopping.R
import woowacourse.shopping.model.ShoppingCartItem
import java.text.DecimalFormat

@Composable
fun ShoppingCartItems(
    shoppingCartItem: ShoppingCartItem,
    onRemoveShoppingItemClick: (ShoppingCartItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(4.dp),
                ).border(
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp),
                ).padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                shoppingCartItem.product.getTitle(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Image(
                painter = painterResource(R.drawable.remove_icon),
                contentDescription = stringResource(R.string.remove_item_description),
                modifier =
                    Modifier
                        .size(16.dp)
                        .clickable { onRemoveShoppingItemClick(shoppingCartItem) },
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            AsyncImage(
                model = shoppingCartItem.product.imageUrl,
                contentDescription = stringResource(R.string.product_image_description),
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .width(136.dp)
                        .height(72.dp)
                        .padding(bottom = 8.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
            )
            Text(
                text = DecimalFormat(stringResource(R.string.price_format_pattern)).format(shoppingCartItem.product.getPrice()),
            )
        }
    }
}
