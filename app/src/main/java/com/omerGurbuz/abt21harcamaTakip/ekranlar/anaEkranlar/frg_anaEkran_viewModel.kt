package com.omerGurbuz.abt21harcamaTakip.ekranlar.anaEkranlar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omerGurbuz.abt21harcamaTakip.api.CurrencyAPIService
import com.omerGurbuz.abt21harcamaTakip.api.kurDegerleriModeli
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class frg_anaEkran_viewModel : ViewModel() {
    private val service = CurrencyAPIService()
    private val compositeDisposable = CompositeDisposable()

    val errorMessage = MutableLiveData<Boolean>()
    val loadingMessage = MutableLiveData<Boolean>()
    val data = MutableLiveData<kurDegerleriModeli>()

    fun getData(){
        loadingMessage.value = true
        compositeDisposable.add(service.loadData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<kurDegerleriModeli>(){
                override fun onSuccess(t: kurDegerleriModeli) {
                    data.value = t
                    errorMessage.value = false
                    loadingMessage.value = false
                }
                override fun onError(e: Throwable) {
                    errorMessage.value = true
                    loadingMessage.value = false
                }
            }))
    }
}