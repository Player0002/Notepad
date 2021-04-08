package com.danny.note.presentation.viewModel

import androidx.lifecycle.*
import com.danny.note.data.model.Color
import com.danny.note.data.model.Note
import com.danny.note.domain.usecase.color.DeleteColorUseCase
import com.danny.note.domain.usecase.color.GetSavedColorUseCase
import com.danny.note.domain.usecase.color.SaveColorUseCase
import com.danny.note.domain.usecase.note.DeleteNoteUseCase
import com.danny.note.domain.usecase.note.GetSavedNoteUseCase
import com.danny.note.domain.usecase.note.SaveNoteUseCase
import com.skydoves.colorpickerview.ColorEnvelope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(
    private val getSavedColorUseCase: GetSavedColorUseCase,
    private val saveColorUseCase: SaveColorUseCase,
    private val deleteColorUseCase: DeleteColorUseCase,
    private val getSavedNoteUseCase: GetSavedNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    val selectedFilter = MutableLiveData<List<Color>>()
    val selectedEdit = MutableLiveData<List<Color>>()
    val toastRequest = MutableLiveData<Event<String>>()
    val transitionRequest = MutableLiveData<Event<Boolean>>()
    val selectedColor = MutableLiveData<Color>()

    fun savedColor() = liveData {
        getSavedColorUseCase.execute().collect { emit(it) }
    }

    fun filteredEdit() = liveData {
        emit(getSavedNoteUseCase.execute().first())
        selectedFilter.asFlow().collect { colors ->
            getSavedNoteUseCase.execute().first().let {
                emit(
                    if (colors.isEmpty())
                        it
                    else it.filter { cmp ->
                        val filtered = (cmp.tags + colors).groupBy { it }.filter { it.value.size > 1 }
                        filtered.size == colors.size
                    }.distinct()
                )
            }
        }
    }

    fun addFilter(color : Color) = viewModelScope.launch{
        getSavedColorUseCase.execute().first().let {
            if(selectedFilter.value?.contains(color) == true) {
                toastRequest.postValue(Event("이미 등록된 필터입니다."))
                return@launch
            };
            selectedFilter.postValue( ArrayList<Color>(selectedFilter.value ?: listOf()).apply { add(color); distinct();}.reversed() )
            transitionRequest.postValue(Event(true))
        }
    }

    fun addEdit(color : Color) = viewModelScope.launch {
        getSavedColorUseCase.execute().first().let {
            if (selectedEdit.value?.contains(color) == true) {
                toastRequest.postValue(Event("이미 등록된 태그입니다."))
                return@launch
            };
            selectedEdit.postValue(ArrayList<Color>(selectedEdit.value ?: listOf()).apply {
                add(
                    color
                ); distinct()
            })
            transitionRequest.postValue(Event(true))
        }
    }

    fun saveNote(note : Note) = viewModelScope.launch {
        saveNoteUseCase.execute(note)
    }


    fun removeFilter(color : Color)  = viewModelScope.launch{
        getSavedColorUseCase.execute().first().let {
            selectedFilter.postValue( ArrayList<Color>(selectedFilter.value?: listOf()).apply { remove(color); distinct(); }.reversed() )
        }
    }
    fun removeEdit(color : Color)  = viewModelScope.launch{
        getSavedColorUseCase.execute().first().let {
            selectedEdit.postValue( ArrayList<Color>(selectedEdit.value?: listOf()).apply { remove(color); distinct() } )
        }
    }
    fun removeColor(color : Color) = viewModelScope.launch {
        removeFilter(color)
        removeEdit(color)
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