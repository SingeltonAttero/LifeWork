package ya.weber.lifework.domain

import ya.weber.lifework.presentation.state.LifeBox

interface GameOfLiveApi {
    val getMatrix: List<List<LifeBox>>
    fun survival(matrix: List<List<LifeBox>>): List<List<LifeBox>>
    fun addNewRandomBox(count: Int, matrix: List<List<LifeBox>>): List<List<LifeBox>>
    val fullSize: Int
}