package com.infosys.junitmockito.repositories

import com.infosys.junitmockito.modelnew.ItemRow
import com.infosys.junitmockito.util.OperationCallback

interface MuseumDataSource {

    fun retrieveMuseums(callback: OperationCallback<ItemRow>)
    fun cancel()
}