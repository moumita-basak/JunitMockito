package com.infosys.junitmockito.repositories

import android.app.Application
import com.google.gson.JsonObject
import com.infosys.junitmockito.network.MyApi
import com.infosys.junitmockito.network.SafeApiRequest

import com.orhanobut.logger.Logger

class ItemRepository(private val api: MyApi, private val application: Application) : SafeApiRequest() {

  suspend fun getItemList(requestMap: HashMap<String, String>): JsonObject{
      Logger.e(requestMap.toString())
      return apiRequest{api.getItems(requestMap)}
  }
}