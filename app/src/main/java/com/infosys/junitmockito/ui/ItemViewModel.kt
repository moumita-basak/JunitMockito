package com.infosys.junitmockito.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.infosys.junitmockito.modelnew.Items
import com.infosys.junitmockito.repositories.ItemRepository
import com.infosys.junitmockito.util.ApiException
import com.infosys.junitmockito.util.Coroutines
import com.infosys.junitmockito.util.NoInternetException

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

    init {
        this.applicationcontext = application
    }
    fun getItemNewList() {
        var requestMap = HashMap<String, String>()
        requestMap.put("auth","")
        authListener?.onBegin()
        Coroutines.main {
            try {
                val itemResponse = repository.getItemList(requestMap)
                Logger.e(itemResponse.toString())
                itemResponse.let {
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