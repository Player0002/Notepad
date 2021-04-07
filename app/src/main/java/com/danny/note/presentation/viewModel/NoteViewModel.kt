package com.danny.note.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.danny.note.data.model.Color
import com.danny.note.domain.usecase.color.DeleteColorUseCase
import com.danny.note.domain.usecase.color.GetSavedColorUseCase
import com.danny.note.domain.usecase.color.SaveColorUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteViewModel(
    private val getSavedColorUseCase: GetSavedColorUseCase,
    private val saveColorUseCase: SaveColorUseCase,
    private val deleteColorUseCase: DeleteColorUseCase
) : ViewModel() {

    private val selectedFilter = MutableLiveData<List<Color>>()
    val toastRequest = MutableLiveData<Event<String>>()

    fun savedColor() = liveData {
        getSavedColorUseCase.execute().collect { emit(it) }
    }

    fun addFilter(color : Color) {
        selectedFilter.value = ArrayList<Color>(selectedFilter.value).apply { add(color) }
    }

    fun removeColor(color : Color) = viewModelScope.launch { deleteColorUseCase.execute(color) }

    fun addColor(r : Int, g : Int, b : Int, name : String) = viewModelScope.launch{
        savedColor().value?.let {
            val filtered = it.filter { cmp -> cmp.r == r && cmp.g == g && cmp.b == b }
            if (filtered.count() > 0) {
                toastRequest.value = Event("Already Registered : ${filtered.first().name}")
                return@launch
            }
        }
        saveColorUseCase.execute(Color(0, r,g,b, name));
    }
}