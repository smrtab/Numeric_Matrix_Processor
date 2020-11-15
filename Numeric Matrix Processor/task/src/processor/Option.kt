package processor

enum class Option(val number: Int, val title: String) {
    SUM(1, "Add matrices"),
    SCALAR_PRODUCT(2, "Multiply matrix by a constant"),
    MATRICES_PRODUCT(3, "Multiply matrices"),
    TRANSPOSE(4, "Transpose matrix"),
    DETERMINANT(5, "Calculate a determinant"),
    INVERSE(6, "Inverse matrix"),
    EXIT(0, "Exit");

    enum class Child(val parent: Int, val number: Int, val title: String) {
        MAIN_DIAGONAL(4, 1, "Main diagonal"),
        SIDE_DIAGONAL(4, 2, "Side diagonal"),
        VERTICAL_LINE(4, 3, "Vertical line"),
        HORIZONTAL_LINE(4, 4, "Horizontal line");
    }

    object Menu {
        fun show() {
            println(Menu)
        }
        fun findPoint(point: Int): Option {
            for (enum in Option.values()) {
                if (point == enum.number) return enum
            }

            throw ProcessorException("Wrong menu option")
        }
        override fun toString(): String {
            return Option.values().joinToString("\n") { "${it.number}. ${it.title}" }
        }
    }

    object Submenu{
        fun show(parent: Int) {
            val list = Submenu.findByParent(parent)
            println(list.joinToString("\n") { "${it.number}. ${it.title}" })
        }
        fun findByParent(parent: Int): MutableList<Option.Child> {
            val result = mutableListOf<Option.Child>()
            for (enum in Option.Child.values()) {
                if (parent == enum.parent) result.add(enum)
            }
            return result
        }
        fun findPoint(point: Int): Option.Child {
            for (enum in Option.Child.values()) {
                if (point == enum.number) return enum
            }

            throw ProcessorException("Wrong menu option")
        }
    }
}