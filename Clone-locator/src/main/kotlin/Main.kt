import locator.Locator

    fun main(args: Array<String>) {
        val locator = Locator()
        locator.locate(args[0].toInt(), args[1])
    }
