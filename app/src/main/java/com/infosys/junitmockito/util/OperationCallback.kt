package com.infosys.junitmockito.util

interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}