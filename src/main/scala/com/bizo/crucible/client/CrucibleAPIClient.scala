package com.bizo.crucible.client

import com.bizo.crucible.client.model._
import com.sun.jersey.api.client.config.DefaultClientConfig
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, writePretty}
import java.text.SimpleDateFormat


class CrucibleAPIClient(host: String, creds: CredentialsProvider) extends CrucibleAPI {
  val client = Client.create(config)
  
  implicit val formats = new DefaultFormats {
    // 2011-09-01T15:49:30.746-0700
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  }

  override def getProjects(): Seq[Project] = {
    val r = resource("/projects-v1").accept("application/json")
    
    val data = parse(r.get(classOf[String])) \ "projectData"
    
    data.extract[Seq[Project]]
  }
  
  override def getReview(id: PermaId): ReviewDetails = {
    val r = resource(s"/reviews-v1/${id.id}/details").accept("application/json")
    
    val review = parse(r.get(classOf[String]))
    
    val flattenReviewers: PartialFunction[JField, JField] = {
      case JField(field, JObject(List(("reviewer", reviewers)))) if field == "reviewers" => {
        JField(field, reviewers)
      }
    }

    review.transformField(flattenReviewers).extract[ReviewDetails]
  }
  
  override def getReviewsInState(state: ReviewState*): Seq[ReviewSummary] = {
    val states = state.mkString(",")
    
    val r = resource("/reviews-v1").queryParam("state", states).accept("application/json")
    
    val reviews = parse(r.get(classOf[String])) \ "reviewData"
    
    reviews.extract[Seq[ReviewSummary]]
  }


  
  override def getUsers(): Seq[User] = {
    val r = resource(s"/users-v1").accept("application/json")
    val users = parse(r.get(classOf[String])) \ "userData"
    
    users.extract[Seq[User]]
  }
  
  private def resource(path: String) = {
    val r = client.resource("https://%s/rest-service%s".format(host, path))
    r.addFilter(new HTTPBasicAuthFilter(creds.user, creds.password))
    
    r
  }
  
  private def config() = {
    val c = new DefaultClientConfig

    c
  }
}