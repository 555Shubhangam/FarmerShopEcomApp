package com.farmershop.data.viewModel

import com.farmershop.data.`interface`.BaseInterface
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProductViewModel (application: Application) : AndroidViewModel(application) {
    lateinit var baseInterface: BaseInterface
    var mContext=application.applicationContext

}