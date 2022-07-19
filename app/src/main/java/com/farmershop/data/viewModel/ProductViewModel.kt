package com.farmershop.data.viewModel

import com.farmershop.data.`interface`.BaseInterface
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.farmershop.data.repository.ProductRepository

class ProductViewModel (application: Application) : AndroidViewModel(application) {
    lateinit var baseInterface: BaseInterface
    var mContext=application.applicationContext

}