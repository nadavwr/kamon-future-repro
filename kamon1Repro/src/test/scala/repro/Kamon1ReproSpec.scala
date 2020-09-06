package repro

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import kamon.Kamon
import kamon.context.Key
import kamon.context.Context
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class Kamon1ReproSpec extends FlatSpec with ScalaFutures {
  "an instrumented Future" should "respect lexical scope" in {
    val ContextKey: Key[Map[String, String]] =
      Key.local("logging_params", Map.empty)

    def currentParams(): Map[String, String] =
      Kamon.currentContext().get(ContextKey)

    def withParams[A](params: (String, String)*)(f: => A): A =
      Kamon.withContextKey(ContextKey, currentParams() ++ params)(f)

    val p1 = "p1" -> ""
    val p2 = "p2" -> ""
    val p3 = "p3" -> ""

    val keys = withParams(p1)(Future(
                                withParams(p2)(Future(()))
                                  .flatMap(_ => withParams(p3)(Future(currentParams())))
                              )).flatten.futureValue.keySet
    assert(keys == Set("p1", "p3"))
  }
}
