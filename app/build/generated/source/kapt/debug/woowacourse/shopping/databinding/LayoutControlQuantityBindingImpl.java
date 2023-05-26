package woowacourse.shopping.databinding;
import woowacourse.shopping.R;
import woowacourse.shopping.BR;
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutControlQuantityBindingImpl extends LayoutControlQuantityBinding implements woowacourse.shopping.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutControlQuantityBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LayoutControlQuantityBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvQuantityAmount.setTag(null);
        this.tvQuantityMinus.setTag(null);
        this.tvQuantityPlus.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new woowacourse.shopping.generated.callback.OnClickListener(this, 1);
        mCallback2 = new woowacourse.shopping.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.setQuantityClickListener == variableId) {
            setSetQuantityClickListener((woowacourse.shopping.presentation.ui.common.QuantityControlClickListener) variable);
        }
        else if (BR.productInCart == variableId) {
            setProductInCart((ProductInCartUiState) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSetQuantityClickListener(@Nullable woowacourse.shopping.presentation.ui.common.QuantityControlClickListener SetQuantityClickListener) {
        this.mSetQuantityClickListener = SetQuantityClickListener;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.setQuantityClickListener);
        super.requestRebind();
    }
    public void setProductInCart(@Nullable ProductInCartUiState ProductInCart) {
        this.mProductInCart = ProductInCart;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.productInCart);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        boolean productInCartDeleted = false;
        java.lang.String stringValueOfProductInCartQuantity = null;
        woowacourse.shopping.presentation.ui.common.QuantityControlClickListener setQuantityClickListener = mSetQuantityClickListener;
        int productInCartDeletedViewINVISIBLEViewVISIBLE = 0;
        ProductInCartUiState productInCart = mProductInCart;
        int productInCartQuantity = 0;

        if ((dirtyFlags & 0x6L) != 0) {



                if (productInCart != null) {
                    // read productInCart.deleted
                    productInCartDeleted = productInCart.isDeleted();
                    // read productInCart.quantity
                    productInCartQuantity = productInCart.getQuantity();
                }
            if((dirtyFlags & 0x6L) != 0) {
                if(productInCartDeleted) {
                        dirtyFlags |= 0x10L;
                }
                else {
                        dirtyFlags |= 0x8L;
                }
            }


                // read productInCart.deleted ? View.INVISIBLE : View.VISIBLE
                productInCartDeletedViewINVISIBLEViewVISIBLE = ((productInCartDeleted) ? (android.view.View.INVISIBLE) : (android.view.View.VISIBLE));
                // read String.valueOf(productInCart.quantity)
                stringValueOfProductInCartQuantity = java.lang.String.valueOf(productInCartQuantity);
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvQuantityAmount, stringValueOfProductInCartQuantity);
            this.tvQuantityAmount.setVisibility(productInCartDeletedViewINVISIBLEViewVISIBLE);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.tvQuantityMinus.setOnClickListener(mCallback1);
            this.tvQuantityPlus.setOnClickListener(mCallback2);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // setQuantityClickListener
                woowacourse.shopping.presentation.ui.common.QuantityControlClickListener setQuantityClickListener = mSetQuantityClickListener;
                // productInCart
                ProductInCartUiState productInCart = mProductInCart;
                // setQuantityClickListener != null
                boolean setQuantityClickListenerJavaLangObjectNull = false;



                setQuantityClickListenerJavaLangObjectNull = (setQuantityClickListener) != (null);
                if (setQuantityClickListenerJavaLangObjectNull) {




                    setQuantityClickListener.setClickEventOnOperatorButton(false, productInCart);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // setQuantityClickListener
                woowacourse.shopping.presentation.ui.common.QuantityControlClickListener setQuantityClickListener = mSetQuantityClickListener;
                // productInCart
                ProductInCartUiState productInCart = mProductInCart;
                // setQuantityClickListener != null
                boolean setQuantityClickListenerJavaLangObjectNull = false;



                setQuantityClickListenerJavaLangObjectNull = (setQuantityClickListener) != (null);
                if (setQuantityClickListenerJavaLangObjectNull) {




                    setQuantityClickListener.setClickEventOnOperatorButton(true, productInCart);
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): setQuantityClickListener
        flag 1 (0x2L): productInCart
        flag 2 (0x3L): null
        flag 3 (0x4L): productInCart.deleted ? View.INVISIBLE : View.VISIBLE
        flag 4 (0x5L): productInCart.deleted ? View.INVISIBLE : View.VISIBLE
    flag mapping end*/
    //end
}