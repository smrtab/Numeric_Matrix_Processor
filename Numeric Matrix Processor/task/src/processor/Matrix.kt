package processor

import java.lang.Exception
import java.text.DecimalFormat

class Matrix(val rows: Int, val columns: Int) {

    var value: Array<DoubleArray> = Array(rows) { DoubleArray(columns) }

    object Collection {
        val list: MutableList<Matrix> = mutableListOf()

        fun isValid(): Boolean {
            val first = Collection.list.first()
            Collection.list.forEach {
                if (first != it && (it.rows != first.rows || it.columns != first.columns))
                    return false
            }
            return true
        }

        fun isValidForProduct(): Boolean {
            val first = Collection.list.first()
            Collection.list.forEach {
                if (first != it && first.columns != it.rows)
                    return false
            }
            return true
        }
    }

    object Calculate {
        fun sum(): Matrix {

            if (!Matrix.Collection.isValid()) {
                throw ProcessorException("The operation cannot be performed.")
            }

            var result = Collection.list[0]
            Collection.list.forEach {
                if (result != it) result += it
            }

            return result
        }

        fun subtract(): Matrix {

            if (!Matrix.Collection.isValid()) {
                throw ProcessorException("The operation cannot be performed.")
            }

            var result = Collection.list[0]
            Collection.list.forEach {
                if (result != it) result += it
            }

            return result
        }

        fun scalarProduct(scalar: Int) {
            Collection.list.map {
                it * scalar
            }
        }

        fun multiply(): Matrix {

            if (!Matrix.Collection.isValidForProduct()) {
                throw ProcessorException("The operation cannot be performed.")
            }

            var result = Collection.list[0]
            Collection.list.forEach {
                if (result != it) result *= it
            }

            return result
        }
    }

    operator fun plus(matrix: Matrix): Matrix {

        var result = Matrix(this.rows, this.columns)

        for (row in 0 until this.rows) {
            for (column in 0 until this.columns) {
                result.value[row][column] = this.value[row][column] + matrix.value[row][column]
            }
        }
        return result
    }

    operator fun minus(matrix: Matrix): Matrix {

        var result = Matrix(this.rows, this.columns)

        for (row in 0 until this.rows) {
            for (column in 0 until this.columns) {
                result.value[row][column] = this.value[row][column] - matrix.value[row][column]
            }
        }
        return result
    }

    operator fun times(scalar: Int): Matrix {

        var result = Matrix(this.rows, this.columns)

        for (row in 0 until this.rows) {
            for (column in 0 until this.columns) {
                result.value[row][column] = scalar * this.value[row][column]
            }
        }
        return result
    }

    operator fun times(scalar: Double): Matrix {

        var result = Matrix(this.rows, this.columns)

        for (row in 0 until this.rows) {
            for (column in 0 until this.columns) {
                result.value[row][column] = scalar * this.value[row][column]
            }
        }
        return result
    }

    operator fun times(matrix: Matrix): Matrix {

        var result = Matrix(this.rows, matrix.columns)

        for (row in 0 until this.rows) {
            for (colM in 0 until matrix.columns) {
                for (colT in 0 until this.columns) {
                    result.value[row][colM] += this.value[row][colT] * matrix.value[colT][colM]
                }
            }
        }
        return result
    }

    fun transpose(type: Option.Child): Matrix {

        return when (type) {
            Option.Child.MAIN_DIAGONAL -> {
                var result = Matrix(this.columns, this.rows)
                for (row in 0 until this.rows) {
                    for (column in 0 until this.columns) {
                        result.value[column][row] = this.value[row][column]
                    }
                }
                result
            }
            Option.Child.SIDE_DIAGONAL -> {
                var result = Matrix(this.columns, this.rows)
                for (row in 0 until this.rows) {
                    for (column in 0 until this.columns) {
                        result.value[this.rows - column - 1][this.columns - row - 1] = this.value[row][column]
                    }
                }
                result
            }
            Option.Child.VERTICAL_LINE -> {
                var result = Matrix(this.rows, this.columns)
                for (row in 0 until this.rows) {
                    result.value[row] = this.value[row].reversedArray()
                }
                result
            }
            Option.Child.HORIZONTAL_LINE -> {
                var result = Matrix(this.rows, this.columns)
                result.value = this.value.reversedArray()
                result
            }
        }
    }

    fun cofactorMatrix(): Matrix {

        val result = Matrix(this.rows, this.columns)

        for (row in 0 until this.rows) {
            for (column in 0 until this.columns) {

                val power = 2 + row + column
                val sign = if (power % 2 == 0) 1 else -1
                val matrix = this.getMinor(row = row, column = column)

                result.value[row][column] = sign * matrix.determinant()
            }
        }

        return result
    }

    fun determinant(): Double {

        if (this.rows == 2 && this.columns == 2) {
            return this.value[0][0] * this.value[1][1] - this.value[0][1] * this.value[1][0]
        }

        var result = 0.0
        for (column in 0 until this.columns) {

            val power = 2 + column
            val sign = if (power % 2 == 0) 1 else -1
            val matrix = this.getMinor(column = column)

            result += sign * this.value[0][column] * matrix.determinant()
        }
        return result
    }

    private fun getMinor(row: Int = 0, column: Int): Matrix {

        val matrix = Matrix(this.rows - 1, this.columns - 1)
        matrix.value = this.value.withIndex().filter {
            it.index != row
        }.map {
            val list = it.value.toMutableList()
            list.removeAt(column)
            list.toDoubleArray()
        }.toTypedArray()

        return matrix
    }

    fun isValidSquare(): Boolean {
        return this.rows == this.columns
    }

    override fun toString(): String {
        return this.value.joinToString("\n") { it ->
            it.joinToString(" ") { it ->
                val l = if (it % 1.0 != 0.0) {
                    String.format("%.2f", it)
                } else {
                    it.toInt().toString()
                }

                if (l.length < 5) {
                    " ".repeat(5 - l.length) + l
                } else {
                    l
                }
            }
        } + "\n"
    }
}