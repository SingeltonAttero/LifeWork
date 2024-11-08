package ya.weber.lifework.presentation.state

data class GameOfLiveState(
    val tiles: List<List<LifeBox>>,
    val grid: Int
) {
    companion object {
        fun idle() = GameOfLiveState(emptyList(), 0)
    }
}

data class LifeBox(
    val rowIndex: Int,
    val columnIndex: Int,
    val isAlive: Boolean,
)