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

See the [API documentation](http://ogrodnek.github.io/crucible-client-scala/latest/api/#com.bizo.crucible.client.package) for more details.

