package mainPackage.viewModel

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class CustomViewModelStoreOwner : ViewModelStoreOwner {
    private val mViewModelStore = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }
}
