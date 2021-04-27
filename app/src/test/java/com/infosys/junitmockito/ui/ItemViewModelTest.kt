package com.infosys.junitmockito.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.infosys.junitmockito.modelnew.ItemRow
import com.infosys.junitmockito.repositories.ItemRepository
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class ItemViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Rule
    @JvmField
    var initRule:MockitoRule = MockitoJUnit.rule()
    private val application = Mockito.mock(Application::class.java)

    private var itemRepository:ItemRepository = Mockito.mock(ItemRepository::class.java)
    private var itemViewModel:ItemViewModel? = null
    private lateinit var itemList: MutableList<ItemRow>

    @Before
    suspend fun setUp() {
        var requestMap = HashMap<String, String>()
        requestMap.put("auth","")
        itemList = ArrayList()
        itemList.add(ItemRow("Title1", "123",""))
        itemList.add(ItemRow("Title2","456",""))

        Mockito.doReturn(itemList).`when`(itemRepository).getItemList(requestMap)
        itemViewModel = ItemViewModel(itemRepository,application)
    }
    @Test
    @Throws(InterruptedException::class)
    suspend fun isItemListEmptyorNot(){
        var requestMap = HashMap<String, String>()
        requestMap.put("auth","")
        itemList = ArrayList()
//        itemList.add(ItemRow("Title1", "123",""))
//        itemList.add(ItemRow("Title2","456",""))

        Mockito.doReturn(itemList).`when`(itemRepository).getItemList(requestMap)
        itemViewModel = ItemViewModel(itemRepository,application)
    }
}