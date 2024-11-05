package ya.weber.lifework.domain

import ya.weber.lifework.presentation.state.LifeBox
import kotlin.random.Random

class GameOfLiveGenerator(private val size: Int = 10) : GameOfLiveApi {

    override val getMatrix: List<List<LifeBox>>
        get() = List(size) { x ->
            List(size) { y ->
                LifeBox(x, y, Random.nextBoolean())
            }
        }

    override fun survival(matrix: List<List<LifeBox>>): List<List<LifeBox>> {
        val mapIndexed = matrix.mapIndexed { x, lifeBoxes ->
            lifeBoxes.mapIndexed { y, lifeBox ->
                val aliveNeighbours = getAliveNeighbours(matrix, x, y)
                lifeBox(lifeBox, aliveNeighbours, x, y)
            }
        }
        return mapIndexed
    }

    override fun addNewRandomBox(count: Int, matrix: List<List<LifeBox>>): List<List<LifeBox>> {
        val newMatrix = matrix.toMutableList()
        repeat(count) {
            val x = Random.nextInt(size)
            val y = Random.nextInt(size)
            val lifeBoxes = newMatrix[x].toMutableList()
            lifeBoxes[y] = LifeBox(x, y, true)
            newMatrix[x] = lifeBoxes
        }
        return newMatrix
    }

    private fun lifeBox(
        lifeBox: LifeBox,
        aliveNeighbours: Int,
        x: Int,
        y: Int
    ): LifeBox {
        return if (lifeBox.isAlive) {
            lifeBoxAlive(aliveNeighbours, x, y)
        } else {
            lifeBoxDeath(aliveNeighbours, x, y)
        }
    }

    private fun lifeBoxDeath(
        aliveNeighbours: Int,
        x: Int,
        y: Int
    ): LifeBox {
        return if (aliveNeighbours == 3) {
            LifeBox(x, y, true)
        } else {
            LifeBox(x, y, false)
        }
    }

    private fun lifeBoxAlive(
        aliveNeighbours: Int,
        x: Int,
        y: Int
    ): LifeBox {
        return if (aliveNeighbours < 2 || aliveNeighbours > 3) {
            LifeBox(x, y, false)
        } else {
            LifeBox(x, y, true)
        }
    }

    private fun getAliveNeighbours(matrix: List<List<LifeBox>>, x: Int, y: Int): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val xIndex = x + i
                val yIndex = y + j
                if (xIndex in 0 until size && yIndex in 0 until size) {
                    if (matrix[xIndex][yIndex].isAlive) {
                        count++
                    }
                }
            }
        }
        return count
    }

    override val fullSize: Int
        get() = size

}