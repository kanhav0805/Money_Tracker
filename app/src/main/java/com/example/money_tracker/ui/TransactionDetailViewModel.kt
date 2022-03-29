package com.example.money_tracker.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.money_tracker.Transaction
import com.example.money_tracker.data.TransactionDetailRepository
import kotlinx.coroutines.launch

class TransactionDetailViewModel(application: Application):AndroidViewModel(application)
{    //creating an instance of repository
     private val repository: TransactionDetailRepository = TransactionDetailRepository(application)
    //creating a mutable live data for id
    private val _transactionId=MutableLiveData<Long>()
    val transactionId:LiveData<Long>
        get() = _transactionId
    //getting transaction from the repository
    fun setId(id:Long){
        if(_transactionId.value!=id)
        {
            _transactionId.value=id
        }
    }
    val transact:LiveData<Transaction> = Transformations.switchMap(_transactionId) { id->
        repository.getTransaction(id)}

    fun saveTransaction(transaction: Transaction)
    {
        viewModelScope.launch {
            if(_transactionId.value==0L)
            {
               _transactionId.value= repository.insert(transaction)
            }
            else
            {
                repository.update(transaction)
            }
        }
    }
   fun deleteTransaction(){
       viewModelScope.launch {
           transact.value?.let{repository.delete(it)}
       }
   }

}