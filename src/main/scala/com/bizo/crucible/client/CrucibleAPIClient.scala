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
import com.sun.jersey.api.client.WebResource


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
  
  private val flattenReviewers: PartialFunction[JField, JField] = {
    case JField(field, JObject(List(("reviewer", reviewers)))) if field == "reviewers" => {
      JField(field, reviewers)
    }
  }
  
  override def getReview(id: PermaId): ReviewDetails = {
    val r = resource(s"/reviews-v1/${id.id}/details").accept("application/json")
    
    val review = parse(r.get(classOf[String]))

    review.transformField(flattenReviewers).extract[ReviewDetails]
  }
  
  override def getReviewsInState(state: ReviewState*): Seq[ReviewSummary] = {
    val states = state.mkString(",")
    
    val r = resource("/reviews-v1").queryParam("state", states).accept("application/json")
    
    val reviews = parse(r.get(classOf[String])) \ "reviewData"
    
    reviews.extract[Seq[ReviewSummary]]
  }
  
  override def getReviewDetailsWithFilter(filter: PredefinedReviewFilter): Seq[ReviewDetails] = {
    val r = resource(s"/reviews-v1/filter/${filter}/details")
    parseDetailedReviewData(r)    
  }
  
  override def getReviewDetailsWithFilter(filter: ReviewFilter): Seq[ReviewDetails] = {
    val _r = resource(s"/reviews-v1/filter/details")
    
    val fromFilter = filter.fromDate.map(("fromDate", _))
    val toFilter = filter.toDate.map(("toDate", _))
    val stateFilter = if (filter.states.isEmpty) None else Some(("states", filter.states.mkString(",")))
    
    val r = Seq(fromFilter, toFilter, stateFilter).flatten.toSeq.foldLeft(_r)((r, f) => r.queryParam(f._1, f._2.toString))
    
    parseDetailedReviewData(r)
  }
  
  private def parseDetailedReviewData(r: WebResource): Seq[ReviewDetails] = {
    val reviews = parse(r.accept("application/json").get(classOf[String])) \ "detailedReviewData"
    
    reviews.transformField(flattenReviewers).extract[Seq[ReviewDetails]]
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