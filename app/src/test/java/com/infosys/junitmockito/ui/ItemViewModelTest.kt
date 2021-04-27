package com.infosys.junitmockito.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.infosys.junitmockito.modelnew.ItemRow
import com.infosys.junitmockito.network.MyApi
import com.infosys.junitmockito.network.NetworkConnectionInterceptor
import com.infosys.junitmockito.repositories.ItemRepository
import com.infosys.junitmockito.repositories.MuseumDataSource
import com.infosys.junitmockito.util.OperationCallback
import org.junit.Assert
import org.junit.Before
import androidx.lifecycle.Observer
import com.infosys.junitmockito.util.capture


import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@RunWith(MockitoJUnitRunner::class)
class ItemViewModelTest {
    @Mock
    private lateinit var museumDataSource: MuseumDataSource

    @Mock
    private lateinit var myApi: MyApi
    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<ItemRow>>
    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderMuseumsObserver: Observer<List<ItemRow>>
    @Rule
    @JvmField
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Rule
    @JvmField
    var initRule:MockitoRule = MockitoJUnit.rule()
    private val application = Mockito.mock(Application::class.java)
//    private  var myApi:MyApi = MyApi(networkConnectionInterceptor = NetworkConnectionInterceptor(application))

    private var itemRepository:ItemRepository = Mockito.mock(ItemRepository::class.java)
    private var itemViewModel:ItemViewModel? = null
    private lateinit var itemList: MutableList<ItemRow>




    private lateinit var museumEmptyList: List<ItemRow>
    private lateinit var museumList: List<ItemRow>

    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Before
    suspend fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(application).thenReturn(application)

        itemRepository = ItemRepository(myApi,application,museumDataSource)
        itemViewModel = ItemViewModel(itemRepository,application)

        mockData()
        setupObservers()
       /* var requestMap = HashMap<String, String>()
        requestMap.put("auth","")
        itemList = ArrayList()
        itemList.add(ItemRow("Title1", "123",""))
        itemList.add(ItemRow("Title2","456",""))

        Mockito.doReturn(itemList).`when`(itemRepository).getItemList(requestMap)
        itemViewModel = ItemViewModel(itemRepository,application)*/
    }
    fun `retrieve museums with ViewModel and Repository returns empty data`() {
        with(itemViewModel) {
            this?.loadMuseums()
            this!!.isViewLoading?.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            museums.observeForever(onRenderMuseumsObserver)
        }

        verify(museumDataSource, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(museumEmptyList)

        Assert.assertNotNull(itemViewModel!!.isViewLoading.value)
        Assert.assertTrue(itemViewModel!!.isEmptyList.value == true)
        Assert.assertTrue(itemViewModel!!.museums.value?.size == 0)
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns full data`() {
        with(itemViewModel) {
            this?.loadMuseums()
            this?.isViewLoading?.observeForever(isViewLoadingObserver)
            this!!.museums.observeForever(onRenderMuseumsObserver)
        }

        verify(museumDataSource, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(museumList)

        Assert.assertNotNull(itemViewModel!!.isViewLoading.value)
        Assert.assertTrue(itemViewModel!!.museums.value?.size == 3)
    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns an error`() {
        with(itemViewModel) {
            this?.loadMuseums()
            this?.isViewLoading?.observeForever(isViewLoadingObserver)
            this!!.museums.observeForever(onRenderMuseumsObserver)
        }
        verify(museumDataSource, times(1)).retrieveMuseums(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(itemViewModel!!.isViewLoading.value)
        Assert.assertNotNull(itemViewModel!!.onMessageError.value)
    }

    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver = mock(Observer::class.java) as Observer<List<ItemRow>>
    }

    private fun mockData() {
        museumEmptyList = emptyList()
        val mockList: MutableList<ItemRow> = mutableListOf()
        mockList.add(
            ItemRow(
                "Title 1",
                "Museo Nacional de Arqueología, Antropología e Historia del Perú",
                ""
            )
        )
        mockList.add(ItemRow("Title 1", "Museo de Sitio Pachacamac", ""))
        mockList.add(ItemRow("Title 3", "Casa Museo José Carlos Mariátegui", ""))

        museumList = mockList.toList()
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

   @Test
   fun itemListEmpty(){
       itemList = ArrayList()

   }
}