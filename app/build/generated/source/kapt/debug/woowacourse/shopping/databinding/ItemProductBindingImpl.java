package woowacourse.shopping.databinding;
import woowacourse.shopping.R;
import woowacourse.shopping.BR;
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemProductBindingImpl extends ItemProductBinding implements woowacourse.shopping.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(7);
        sIncludes.setIncludes(1, 
            new String[] {"layout_control_quantity"},
            new int[] {6},
            new int[] {woowacourse.shopping.R.layout.layout_control_quantity});
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView1;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemProductBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private ItemProductBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[2]
            , (android.widget.ImageView) bindings[3]
            , (woowacourse.shopping.databinding.LayoutControlQuantityBinding) bindings[6]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            );
        this.fabItemProductQuantity.setTag(null);
        this.ivItemProductImage.setTag(null);
        setContainedBinding(this.layoutQuantity);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.tvItemProductName.setTag(null);
        this.tvItemProductPrice.setTag(null);
        setRootTag(root);
        // listeners
        mCallback6 = new woowacourse.shopping.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
        }
        layoutQuantity.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (layoutQuantity.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.shoppingCart == variableId) {
            setShoppingCart((ProductInCartUiState) variable);
        }
        else if (BR.setClickListener == variableId) {
            setSetClickListener((woowacourse.shopping.presentation.ui.home.HomeSetClickListener) variable);
        }
        else if (BR.product == variableId) {
            setProduct((woowacourse.shopping.domain.model.Product) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setShoppingCart(@Nullable ProductInCartUiState ShoppingCart) {
        this.mShoppingCart = ShoppingCart;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.shoppingCart);
        super.requestRebind();
    }
    public void setSetClickListener(@Nullable woowacourse.shopping.presentation.ui.home.HomeSetClickListener SetClickListener) {
        this.mSetClickListener = SetClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.setClickListener);
        super.requestRebind();
    }
    public void setProduct(@Nullable woowacourse.shopping.domain.model.Product Product) {
        this.mProduct = Product;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.product);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        layoutQuantity.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLayoutQuantity((woowacourse.shopping.databinding.LayoutControlQuantityBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLayoutQuantity(woowacourse.shopping.databinding.LayoutControlQuantityBinding LayoutQuantity, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        ProductInCartUiState shoppingCart = mShoppingCart;
        java.lang.String tvItemProductPriceAndroidStringPriceFormatProductPrice = null;
        woowacourse.shopping.presentation.ui.home.HomeSetClickListener setClickListener = mSetClickListener;
        java.lang.String productName = null;
        java.lang.String productItemImage = null;
        int shoppingCartContainedViewVISIBLEViewINVISIBLE = 0;
        int shoppingCartContainedViewINVISIBLEViewVISIBLE = 0;
        woowacourse.shopping.domain.model.Product product = mProduct;
        int productPrice = 0;
        boolean shoppingCartContained = false;

        if ((dirtyFlags & 0x12L) != 0) {



                if (shoppingCart != null) {
                    // read shoppingCart.contained
                    shoppingCartContained = shoppingCart.isContained();
                }
            if((dirtyFlags & 0x12L) != 0) {
                if(shoppingCartContained) {
                        dirtyFlags |= 0x40L;
                        dirtyFlags |= 0x100L;
                }
                else {
                        dirtyFlags |= 0x20L;
                        dirtyFlags |= 0x80L;
                }
            }


                // read shoppingCart.contained ? View.VISIBLE : View.INVISIBLE
                shoppingCartContainedViewVISIBLEViewINVISIBLE = ((shoppingCartContained) ? (android.view.View.VISIBLE) : (android.view.View.INVISIBLE));
                // read shoppingCart.contained ? View.INVISIBLE : View.VISIBLE
                shoppingCartContainedViewINVISIBLEViewVISIBLE = ((shoppingCartContained) ? (android.view.View.INVISIBLE) : (android.view.View.VISIBLE));
        }
        if ((dirtyFlags & 0x14L) != 0) {
        }
        if ((dirtyFlags & 0x18L) != 0) {



                if (product != null) {
                    // read product.name
                    productName = product.getName();
                    // read product.itemImage
                    productItemImage = product.getItemImage();
                    // read product.price
                    productPrice = product.getPrice();
                }


                // read @android:string/price_format
                tvItemProductPriceAndroidStringPriceFormatProductPrice = tvItemProductPrice.getResources().getString(R.string.price_format, productPrice);
        }
        // batch finished
        if ((dirtyFlags & 0x12L) != 0) {
            // api target 1

            this.fabItemProductQuantity.setVisibility(shoppingCartContainedViewINVISIBLEViewVISIBLE);
            this.layoutQuantity.getRoot().setVisibility(shoppingCartContainedViewVISIBLEViewINVISIBLE);
            this.layoutQuantity.setProductInCart(shoppingCart);
        }
        if ((dirtyFlags & 0x18L) != 0) {
            // api target 1

            woowacourse.shopping.util.BindingAdapter.setGlideImage(this.ivItemProductImage, productItemImage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvItemProductName, productName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvItemProductPrice, tvItemProductPriceAndroidStringPriceFormatProductPrice);
        }
        if ((dirtyFlags & 0x14L) != 0) {
            // api target 1

            this.layoutQuantity.setSetQuantityClickListener(setClickListener);
        }
        if ((dirtyFlags & 0x10L) != 0) {
            // api target 1

            this.mboundView0.setOnClickListener(mCallback6);
        }
        executeBindingsOn(layoutQuantity);
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // setClickListener != null
        boolean setClickListenerJavaLangObjectNull = false;
        // product
        woowacourse.shopping.domain.model.Product product = mProduct;
        // setClickListener
        woowacourse.shopping.presentation.ui.home.HomeSetClickListener setClickListener = mSetClickListener;



        setClickListenerJavaLangObjectNull = (setClickListener) != (null);
        if (setClickListenerJavaLangObjectNull) {



            setClickListener.setClickEventOnProduct(product);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): layoutQuantity
        flag 1 (0x2L): shoppingCart
        flag 2 (0x3L): setClickListener
        flag 3 (0x4L): product
        flag 4 (0x5L): null
        flag 5 (0x6L): shoppingCart.contained ? View.VISIBLE : View.INVISIBLE
        flag 6 (0x7L): shoppingCart.contained ? View.VISIBLE : View.INVISIBLE
        flag 7 (0x8L): shoppingCart.contained ? View.INVISIBLE : View.VISIBLE
        flag 8 (0x9L): shoppingCart.contained ? View.INVISIBLE : View.VISIBLE
    flag mapping end*/
    //end
}