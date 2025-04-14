package com.example.abc_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abc_app.domain.model.ListItem
import com.example.abc_app.domain.usecase.GetListItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetListItemsUseCase
) : ViewModel() {

    private var originalList: List<ListItem> = useCase.getByPosition(0)

    private val _items = MutableLiveData<List<ListItem>>(originalList)
    val items: LiveData<List<ListItem>> = _items

    fun updateCarousel(position: Int) {
        originalList = useCase.getByPosition(position)
        _items.value = originalList
    }

    fun search(query: String) {
        _items.value = useCase.filter(originalList, query)
    }

    fun top3(list: List<ListItem>): List<Pair<Char, Int>> = useCase.top3Characters(list)

}