package ar.com.jdodevelopment.listdetail.ui.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.jdodevelopment.listdetail.data.api.ProductsApi
import ar.com.jdodevelopment.listdetail.data.model.Product
import ar.com.jdodevelopment.listdetail.data.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val api: ProductsApi
) : ViewModel() {

    private val _listResource = MutableLiveData<Resource<List<Product>>>()
    val listResource: LiveData<Resource<List<Product>>> get() = _listResource

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _listResource.value = (Resource.Loading())
            try {
                val response = api.getList()
                if (response.isSuccessful) {
                    val list = response.body()!!
                    _listResource.value = Resource.Success(list)
                    return@launch
                }
                val message = getErrorMessage(response)
                _listResource.value = (Resource.Error(message))
            } catch (t: Throwable) {
                t.printStackTrace()
                _listResource.value = (Resource.Error(getErrorMessage()))
            }
        }
    }

    private fun getErrorMessage(response: Response<*>? = null): String {
        // TODO return specific error message
        return "Se ha producido un error"
    }

}