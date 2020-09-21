package com.selvakumarsm.flipper.orders.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.selvakumarsm.flipper.auth.usecase.GetLoggedInUserIdUseCase
import com.selvakumarsm.flipper.orders.data.repositoryimpl.ItemsRepositoryImpl
import com.selvakumarsm.flipper.orders.domain.model.Address
import com.selvakumarsm.flipper.orders.domain.model.Item
import com.selvakumarsm.flipper.orders.domain.model.PaymentMethod
import com.selvakumarsm.flipper.orders.domain.usecase.GetItemsInCartUseCase
import kotlinx.coroutines.flow.collectLatest

class OrdersViewModel : ViewModel() {
    companion object {
        private const val TAG = "OrdersViewModel"
    }

    private val itemsInCartUseCase = GetItemsInCartUseCase(ItemsRepositoryImpl(), GetLoggedInUserIdUseCase())

    private val _savedItemsInCart = liveData<List<Item>> {
        Log.d(TAG, "Calling usecase to fetch items from cart: ")
        itemsInCartUseCase.invoke().collectLatest {
            Log.d(TAG, "Items in cart received in viewmodel: ${it.size}")
            emit(it)
        }
    }
    val savedItemsLiveData: LiveData<List<Item>> = _savedItemsInCart

    private val _savedAddress = liveData<List<Address>> {  }
    val savedAddressLiveData: LiveData<List<Address>> = _savedAddress

    private val _savedCards = liveData<List<PaymentMethod.Card>> {  }
    val savedCardsLiveData: LiveData<List<PaymentMethod.Card>> = _savedCards

    override fun onCleared() {
        super.onCleared()
    }
}