package com.infosys.junitmockito.ui

interface AuthListener {
    fun onBegin()
    fun onSuccess(value:String)
    fun onFailure(value: String)
    fun validationFail(value: String?, field: String?)
}