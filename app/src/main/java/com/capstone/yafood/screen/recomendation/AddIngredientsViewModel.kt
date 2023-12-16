package com.capstone.yafood.screen.recomendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddIngredientsViewModel : ViewModel() {
    private val _listValue = arrayListOf("")
    val listValue: ArrayList<String> get() = _listValue

    private val _listInput = MutableLiveData(_listValue)
    val listInput: LiveData<ArrayList<String>> get() = _listInput

    fun addInput() {
        _listValue.add("")
        _listInput.value = ArrayList(_listValue)
    }

    fun deleteInput(position: Int) {
        _listValue.removeAt(position)
        _listInput.value = ArrayList(_listValue)
    }

    fun updateValue(value: String, position: Int) {
        _listValue[position] = value
    }

    fun clearList() {
        _listValue.clear()
        _listValue.add("")
        _listInput.value = ArrayList(_listValue)
    }
}