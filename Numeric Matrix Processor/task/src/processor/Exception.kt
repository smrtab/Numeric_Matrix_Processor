package processor

import java.lang.Exception

class ProcessorException(message: String) : Exception(message)
class ExitCommandException(message: String) : Exception(message)
