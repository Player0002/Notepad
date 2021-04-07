package com.danny.note.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.danny.note.data.model.Color
import com.danny.note.domain.usecase.color.DeleteColorUseCase
import com.danny.note.domain.usecase.color.GetSavedColorUseCase
import com.danny.note.domain.usecase.color.SaveColorUseCase
import com.skydoves.colorpickerview.ColorEnvelope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(
    private val getSavedColorUseCase: GetSavedColorUseCase,
    private val saveColorUseCase: SaveColorUseCase,
    private val deleteColorUseCase: DeleteColorUseCase
) : ViewModel() {

    val selectedFilter = MutableLiveData<List<Color>>()
    val toastRequest = MutableLiveData<Event<String>>()
    val transitionRequest = MutableLiveData<Event<Boolean>>()
    val selectedColor = MutableLiveData<Color>()

    fun savedColor() = liveData {
        getSavedColorUseCase.execute().collect { emit(it) }
    }

    fun addFilter(color : Color) = viewModelScope.launch{
        getSavedColorUseCase.execute().first().let {
            if(selectedFilter.value?.contains(color) == true) {
                toastRequest.postValue(Event("이미 등록된 필터입니다."))
                return@launch
            };
            selectedFilter.postValue( ArrayList<Color>(selectedFilter.value ?: listOf()).apply { add(color); distinct()} )
            transitionRequest.postValue(Event(true))
        }
    }

    fun removeFilter(color : Color)  = viewModelScope.launch{
        getSavedColorUseCase.execute().first().let {
            selectedFilter.postValue( ArrayList<Color>(selectedFilter.value?: listOf()).apply { remove(color); distinct() } )
        }
    }

    fun removeColor(color : Color) = viewModelScope.launch {
        removeFilter(color)
        deleteColorUseCase.execute(color)
    }

    fun getColor(envelope: ColorEnvelope) = viewModelScope.launch {
        val arr = envelope.argb
        val r = arr[1]
        val g = arr[2]
        val b = arr[3]

        getSavedColorUseCase.execute().first().let {
            val filtered = it.filter { cmp -> cmp.r == r && cmp.g == g && cmp.b == b }
            if(filtered.count() > 0) {
                selectedColor.postValue(filtered.first())
            }else {
                selectedColor.postValue( Color(null, r, g, b, "새로운 색"))
            }
        }
    }

    fun addColor(name : String) = viewModelScope.launch{
        selectedColor.value?.let { color ->
            getSavedColorUseCase.execute().first().let{
                val r = color.r
                val g = color.g
                val b = color.b
                val filtered = it.filter { cmp -> cmp.r == r && cmp.g == g && cmp.b == b}
                if (filtered.count() > 0) {
                    toastRequest.postValue(Event("Already Registered : ${filtered.first().name}"))
                }else {
                    saveColorUseCase.execute(color.apply {
                        this.name = name
                    });
                    transitionRequest.postValue(Event(true))
                }
            }
        }
    }
}