package test

import org.scalatest.{DoNotDiscover, Matchers, GivenWhenThen, FeatureSpec}

@DoNotDiscover class RelatedFeatureTest extends FeatureSpec with GivenWhenThen with Matchers with ConfiguredTestSuite {

  feature("Related links") {

    // Feature

    info("In order to continue reading more about the story")
    info("As a Guardian reader")
    info("I want to visit related links to the current article I am reading")

    // Metrics

    info("Increase average number of articles 'read' from 1.9% to 2.5%")

    // Features

    scenario("Shows related links for each article") {

      Given("there is an article 'Woman tortured during burglary tells of waterboarding ordeal'")
      goTo("/related/uk/2012/aug/07/woman-torture-burglary-waterboard-surrey") { browser =>
        import browser._
        Then("I should see the related links")
        $(".fc-item") should have length 8

      }
    }

    scenario("Shows article metadata for each related link") {

      Given("there is an article 'Woman tortured during burglary tells of waterboarding ordeal'")
      goTo("/related/uk/2012/aug/07/woman-torture-burglary-waterboard-surrey") { browser =>
        import browser._
        Then("I should see the headline, trail text for each of the first five related links")

        //We cannot guarantee what exactly gets returned from the content api as related is an algorithm
        //so just checking that items exist and not their values

        val article = findFirst("li")
        article.findFirst("a").getAttribute("href").length should be > 0
        article.findFirst("h2").getText.length should be > 0
        article.findFirst("time").getAttribute("data-timestamp") should not be empty

        findFirst("ul").find(".fc-item__title").size should be > 0
      }
    }

  }
}
