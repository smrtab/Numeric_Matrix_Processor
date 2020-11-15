package processor

fun main() {
    while (true) {
        try {
            Processor.handle()
        } catch (e: ProcessorException) {
            println(e.message)
        } catch (e: ExitCommandException) {
            break
        }
    }
}
