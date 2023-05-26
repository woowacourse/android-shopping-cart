package woowacourse.shopping.databinding;
import woowacourse.shopping.R;
import woowacourse.shopping.BR;
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemShoppingCartProductBindingImpl extends ItemShoppingCartProductBinding implements woowacourse.shopping.generated.callback.OnCheckedChangeListener.Listener, woowacourse.shopping.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(8);
        sIncludes.setIncludes(1, 
            new String[] {"layout_control_quantity"},
            new int[] {7},
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
    private final android.widget.CompoundButton.OnCheckedChangeListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemShoppingCartProductBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private ItemShoppingCartProductBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.CheckBox) bindings[5]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[4]
            , (woowacourse.shopping.databinding.LayoutControlQuantityBinding) bindings[7]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[6]
            );
        this.cbCartProduct.setTag(null);
        this.ivCartProductDelete.setTag(null);
        this.ivCartProductPhoto.setTag(null);
        setContainedBinding(this.layoutQuantity);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.tvCartProductName.setTag(null);
        this.tvCartProductPrice.setTag(null);
        setRootTag(root);
        // listeners
        mCallback5 = new woowacourse.shopping.generated.callback.OnCheckedChangeListener(this, 3);
        mCallback3 = new woowacourse.shopping.generated.callback.OnClickListener(this, 1);
        mCallback4 = new woowacourse.shopping.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
            setSetClickListener((woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener) variable);
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
    public void setSetClickListener(@Nullable woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener SetClickListener) {
        this.mSetClickListener = SetClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.setClickListener);
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
        java.lang.String shoppingCartProductItemImage = null;
        ProductInCartUiState shoppingCart = mShoppingCart;
        java.lang.String tvCartProductPriceAndroidStringPriceFormatShoppingCartGetTotalPriceOfProduct = null;
        int shoppingCartGetTotalPriceOfProduct = 0;
        woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener setClickListener = mSetClickListener;
        boolean shoppingCartChecked = false;
        java.lang.String shoppingCartProductName = null;
        woowacourse.shopping.domain.model.Product shoppingCartProduct = null;

        if ((dirtyFlags & 0xaL) != 0) {



                if (shoppingCart != null) {
                    // read shoppingCart.getTotalPriceOfProduct()
                    shoppingCartGetTotalPriceOfProduct = shoppingCart.getTotalPriceOfProduct();
                    // read shoppingCart.checked
                    shoppingCartChecked = shoppingCart.isChecked();
                    // read shoppingCart.product
                    shoppingCartProduct = shoppingCart.getProduct();
                }


                // read @android:string/price_format
                tvCartProductPriceAndroidStringPriceFormatShoppingCartGetTotalPriceOfProduct = tvCartProductPrice.getResources().getString(R.string.price_format, shoppingCartGetTotalPriceOfProduct);
                if (shoppingCartProduct != null) {
                    // read shoppingCart.product.itemImage
                    shoppingCartProductItemImage = shoppingCartProduct.getItemImage();
                    // read shoppingCart.product.name
                    shoppingCartProductName = shoppingCartProduct.getName();
                }
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.cbCartProduct, shoppingCartChecked);
            woowacourse.shopping.util.BindingAdapter.setGlideImage(this.ivCartProductPhoto, shoppingCartProductItemImage);
            this.layoutQuantity.setProductInCart(shoppingCart);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCartProductName, shoppingCartProductName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCartProductPrice, tvCartProductPriceAndroidStringPriceFormatShoppingCartGetTotalPriceOfProduct);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setListeners(this.cbCartProduct, mCallback5, (androidx.databinding.InverseBindingListener)null);
            this.ivCartProductDelete.setOnClickListener(mCallback4);
            this.mboundView1.setOnClickListener(mCallback3);
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.layoutQuantity.setSetQuantityClickListener(setClickListener);
        }
        executeBindingsOn(layoutQuantity);
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnCheckedChanged(int sourceId , android.widget.CompoundButton callbackArg_0, boolean callbackArg_1) {
        // localize variables for thread safety
        // setClickListener != null
        boolean setClickListenerJavaLangObjectNull = false;
        // shoppingCart
        ProductInCartUiState shoppingCart = mShoppingCart;
        // setClickListener
        woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener setClickListener = mSetClickListener;



        setClickListenerJavaLangObjectNull = (setClickListener) != (null);
        if (setClickListenerJavaLangObjectNull) {




            setClickListener.setClickEventOnCheckBox(callbackArg_1, shoppingCart);
        }
    }
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // setClickListener != null
                boolean setClickListenerJavaLangObjectNull = false;
                // shoppingCart
                ProductInCartUiState shoppingCart = mShoppingCart;
                // setClickListener
                woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener setClickListener = mSetClickListener;



                setClickListenerJavaLangObjectNull = (setClickListener) != (null);
                if (setClickListenerJavaLangObjectNull) {



                    setClickListener.setClickEventOnItem(shoppingCart);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // setClickListener != null
                boolean setClickListenerJavaLangObjectNull = false;
                // shoppingCart
                ProductInCartUiState shoppingCart = mShoppingCart;
                // setClickListener
                woowacourse.shopping.presentation.ui.shoppingCart.ShoppingCartSetClickListener setClickListener = mSetClickListener;



                setClickListenerJavaLangObjectNull = (setClickListener) != (null);
                if (setClickListenerJavaLangObjectNull) {



                    setClickListener.setClickEventOnDeleteButton(shoppingCart);
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): layoutQuantity
        flag 1 (0x2L): shoppingCart
        flag 2 (0x3L): setClickListener
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}