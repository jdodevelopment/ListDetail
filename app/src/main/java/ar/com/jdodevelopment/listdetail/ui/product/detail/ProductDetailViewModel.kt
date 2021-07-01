package ar.com.jdodevelopment.listdetail.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ar.com.jdodevelopment.listdetail.data.consts.BundleConsts
import ar.com.jdodevelopment.listdetail.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
     savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _product = MutableLiveData<Product>(savedStateHandle.get(BundleConsts.PRODUCT))
    val product: LiveData<Product> get() = _product

}