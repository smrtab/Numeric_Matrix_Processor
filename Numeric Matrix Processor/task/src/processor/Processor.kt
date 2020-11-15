package processor

import java.util.*

class Processor {

    companion object {
        val scanner = Scanner(System.`in`)
        fun handle() {

            Option.Menu.show()

            print("Your choice: ")
            when (Option.Menu.findPoint(scanner.nextInt())) {
                Option.EXIT -> {
                    throw ExitCommandException("Bye!")
                }
                Option.SUM -> {
                    this.addMatrices()
                }
                Option.SCALAR_PRODUCT -> {
                    this.scalarProduct()
                }
                Option.MATRICES_PRODUCT -> {
                    this.matricesProduct()
                }
                Option.TRANSPOSE -> {
                    this.transpose()
                }
                Option.DETERMINANT -> {
                    this.determinant()
                }

                Option.INVERSE -> {
                    this.inverse()
                }
            }

            Matrix.Collection.list.clear()
        }

        private fun addMatrices() {
            this.requestCollection()
            println("The result is:")
            println(Matrix.Calculate.sum())
        }

        private fun scalarProduct() {

            val matrix = this.requestMatrix()

            print("Enter constant:")
            val scalar = scanner.nextInt()

            println("The result is:")
            println(matrix * scalar)
        }

        private fun matricesProduct() {
            this.requestCollection()
            println("The result is:")
            println(Matrix.Calculate.multiply())
        }

        private fun transpose() {
            Option.Submenu.show(Option.TRANSPOSE.number)

            print("Your choice: ")
            val type = Option.Submenu.findPoint(scanner.nextInt())

            val matrix = this.requestMatrix()

            println("The result is:")
            println(matrix.transpose(type))
        }

        private fun determinant() {
            val matrix = this.requestMatrix()

            if (!matrix.isValidSquare()) {
                throw ProcessorException("The operation cannot be performed.")
            }

            val result = matrix.determinant()

            println("The result is:")
            println( if (result % 1.0 != 0.0) {
                result
            } else {
                result.toInt()
            })
        }

        private fun inverse() {
            val matrix = this.requestMatrix()
            val determinant = matrix.determinant()

            if (determinant == 0.0) {
                throw ProcessorException("This matrix doesn't have an inverse.")
            }

            val cofactorMatrix = matrix.cofactorMatrix()
            val result = cofactorMatrix.transpose(Option.Child.MAIN_DIAGONAL) * (1 / determinant)

            println("The result is:")
            println(result)
        }

        private fun requestCollection() {
            for (order in arrayOf("first", "second")) {

                print("Enter size of $order matrix: ")
                val (rows, columns) = Array(2) { scanner.nextInt() }.also { it.toIntArray() }
                val matrix = Matrix(rows, columns)

                println("Enter $order matrix: ")
                matrix.value = Array(rows) { DoubleArray(columns) { scanner.nextDouble() } }

                Matrix.Collection.list.add(matrix)
            }
        }

        private fun requestMatrix(): Matrix {
            print("Enter size of matrix: ")
            val (rows, columns) = Array(2) { scanner.nextInt() }.also { it.toIntArray() }
            val matrix = Matrix(rows, columns)

            println("Enter matrix:")
            matrix.value = Array(rows) { DoubleArray(columns) { scanner.nextDouble() } }

            return matrix
        }
    }
}