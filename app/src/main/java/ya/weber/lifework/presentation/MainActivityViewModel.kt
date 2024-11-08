package ya.weber.lifework.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ya.weber.lifework.domain.GameOfLiveGenerator
import ya.weber.lifework.presentation.state.GameOfLiveState

class MainActivityViewModel : ViewModel() {
    private val _state = MutableStateFlow(createState())
    private val gameOfLiveGenerator
        get() = GameOfLiveGenerator(100)
    val state: Flow<GameOfLiveState>
        get() = _state
    private val startFlow = MutableStateFlow<Boolean>(false)

    init {
        var job: Job? = null
        startFlow
            .onEach { start ->
                job?.cancel()
                job = viewModelScope.launch {
                    while (start) {
                        val newMatrix = gameOfLiveGenerator.survival(_state.value.tiles)
                        delay(100)
                        _state.value = _state.value.copy(tiles = newMatrix)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun createState(): GameOfLiveState {
        return GameOfLiveState(gameOfLiveGenerator.getMatrix, gameOfLiveGenerator.fullSize)
    }

    fun pause() {
        startFlow.value = !startFlow.value
    }

    fun start() {
        startFlow.value = false
        _state.value = createState()
        startFlow.value = true
    }

    fun addRandom() {
        _state.value =
            _state.value.copy(
                tiles = gameOfLiveGenerator.addNewRandomBox(
                    _state.value.grid / GRID_DIVIDER_COUNT,
                    _state.value.tiles
                )
            )
    }

    companion object {
        private const val GRID_DIVIDER_COUNT = 10

    }

}