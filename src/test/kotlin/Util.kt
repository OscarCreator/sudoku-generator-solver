import org.junit.jupiter.api.Assertions

/**
 * Created by Oscar on 2020-08-27.
 */
infix fun Any.shouldBe(actual: Any){
    Assertions.assertEquals(this, actual)
}