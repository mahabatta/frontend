package test

import org.scalatest.{Matchers, FlatSpec, DoNotDiscover}
import play.api.test._
import play.api.test.Helpers._

@DoNotDiscover class ContentCardControllerTest extends FlatSpec with Matchers with ConfiguredTestSuite  {

  val article = "/world/2014/nov/18/hereford-hospital-patient-tested-for-ebola"
  val callbackName = "aCallback"

  "Content Card Comntroller" should "200 when the content is found" in {
      val result = controllers.ContentCardController.render(article)(TestRequest())
      status(result) should be(200)
  }

  it should "return JSONP when callback is suppl;ed" in {
      val fakeRequest = FakeRequest(GET, s"/embed/card/${article}?callback=${callbackName}")
          .withHeaders("host" -> "localhost:9000")

    val result = controllers.ContentCardController.render(article)(fakeRequest)
    status(result) should be(200)
    contentType(result).get should be ("application/javascript")
    contentAsString(result) should startWith(s"""${callbackName}({\"html\"""") // the callback
  }

  it should "return JSON when .json format is supplied" in {
    val fakeRequest = FakeRequest(GET, s"/embed/card/${article}.json")
      .withHeaders("host" -> "localhost:9000")
      .withHeaders("Origin" -> "http://www.theorigin.com")

    val result = controllers.ContentCardController.render(article)(fakeRequest)
    status(result) should be(200)
    contentType(result).get should be("application/json")
    contentAsString(result) should startWith("{\"html\"")
  }
}