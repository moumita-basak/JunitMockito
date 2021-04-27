package com.infosys.junitmockito.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.infosys.junitmockito.modelnew.ItemRow
import com.infosys.junitmockito.modelnew.Items
import com.infosys.junitmockito.repositories.ItemRepository
import com.infosys.junitmockito.util.ApiException
import com.infosys.junitmockito.util.Coroutines
import com.infosys.junitmockito.util.NoInternetException
import com.infosys.junitmockito.util.OperationCallback

import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.lang.Exception

class ItemViewModel(private val repository: ItemRepository, application: Application) :
    AndroidViewModel(application) {
    var authListener: AuthListener? = null
    var applicationcontext: Application
    var itemPojo: MutableLiveData<Items> = MutableLiveData()
    var itemLisPojo: MutableLiveData<ItemRow> = MutableLiveData()

    init {
        this.applicationcontext = application
    }
    private val _museums = MutableLiveData<List<ItemRow>>().apply { value = emptyList() }
    val museums: LiveData<List<ItemRow>> = _museums

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    /*
    If you require that the data be loaded only once, you can consider calling the method
    "loadMuseums()" on constructor. Also, if you rotate the screen, the service will not be called.
    init {
        //loadMuseums()
    }
     */

    fun loadMuseums() {
        _isViewLoading.value = true
        repository.fetchMuseums(object : OperationCallback<ItemRow> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

            override fun onSuccess(data: List<ItemRow>?) {
                _isViewLoading.value = false
                if (data.isNullOrEmpty()) {
                    _isEmptyList.value = true

                } else {
                    _museums.value = data
                }
            }
        })
    }

    fun getItemNewList() {
        _isViewLoading.value = true

        var requestMap = HashMap<String, String>()
        requestMap.put("auth","")
        authListener?.onBegin()
        Coroutines.main {
            try {
                val itemResponse = repository.getItemList(requestMap)
                Logger.e(itemResponse.toString())
                itemResponse.let {
                    _isViewLoading.value = false

                    var jObj = JSONObject(it.toString())
                    var data = ""
                    var gson = Gson()
                        itemPojo.value = gson.fromJson(jObj.toString(), Items::class.java)

                }

            }

        catch (e: ApiException) {

                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

    
}