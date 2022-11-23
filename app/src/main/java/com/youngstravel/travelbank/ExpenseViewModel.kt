package com.youngstravel.travelbank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youngstravel.travelbank.data.Expense
import com.youngstravel.travelbank.network.TravelsApi
import kotlinx.coroutines.launch

enum class TravelApiStatus { LOADING, ERROR, DONE }

class ExpenseViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<TravelApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<TravelApiStatus> = _status

    private val _expensesList = MutableLiveData<List<Expense>>()
    val expensesList: LiveData<List<Expense>> = _expensesList

    private val _totalExpense = MutableLiveData<Double>(0.0)
    val totalExpense: LiveData<Double> = _totalExpense

    init {
        getExpensesList()
    }

    private fun getExpensesList() {
        viewModelScope.launch {
            _status.value = TravelApiStatus.LOADING
            try {
                _expensesList.value = TravelsApi.retrofitService.getExpenses()
                _status.value = TravelApiStatus.DONE
                calculateTotalExpense()
            } catch (e: Exception) {
                _status.value = TravelApiStatus.ERROR
                _expensesList.value = listOf()
            }
        }
    }

    private fun calculateTotalExpense() {
        val fetchedExpensesList = expensesList.value
        var totalAmount: Double = 0.0
        fetchedExpensesList?.let {
            it.forEach { anExpense ->
                totalAmount += anExpense.amount
            }
        }
        _totalExpense.value = totalAmount
    }

    fun getAnExpenseObject(index: Int): Expense? {
        return expensesList.value?.get(index)
    }
}