package com.infosys.junitmockito.repositories

import android.app.Application
import com.google.gson.JsonObject
import com.infosys.junitmockito.modelnew.ItemRow
import com.infosys.junitmockito.network.MyApi
import com.infosys.junitmockito.network.SafeApiRequest
import com.infosys.junitmockito.util.OperationCallback

import com.orhanobut.logger.Logger

class ItemRepository(private val api: MyApi, private val application: Application,private val museumDataSource: MuseumDataSource) : SafeApiRequest() {


  suspend fun getItemList(requestMap: HashMap<String, String>): JsonObject{
      Logger.e(requestMap.toString())
      return apiRequest{api.getItems(requestMap)}
  }

    public fun getData():String{
        return "hello"
    }

    fun fetchMuseums(callback: OperationCallback<ItemRow>) {
        museumDataSource.retrieveMuseums(callback)
    }
}