# crucible rest api for scala

[![Build Status](https://travis-ci.org/ogrodnek/crucible-client-scala.svg)](https://travis-ci.org/ogrodnek/crucible-client-scala)

Basic api client for [Crucible](https://www.atlassian.com/software/crucible/overview)'s [REST API](https://docs.atlassian.com/fisheye-crucible/latest/wadl/crucible.html)

# Example usage

```scala
val client = new CrucibleAPIClient(Env("CRUCIBLE_HOST"), new EnvironmentCredentialsProvider)

for (review <- client.getReviewsInState(ReviewState.Review)) {
  // do something with reviews...
}
```

See the [API documentation](http://ogrodnek.github.io/crucible-client-scala/latest/api/#com.bizo.crucible.client.CrucibleAPI) for more details.

Available in maven central as [crucible-client-scala_2.10](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22crucible-client-scala_2.10%22).