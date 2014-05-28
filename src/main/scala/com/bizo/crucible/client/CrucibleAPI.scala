package com.bizo.crucible.client

import com.bizo.crucible.client.model._

/**
 * Simple API client for Crucible's REST API.
 * 
 * see: https://docs.atlassian.com/fisheye-crucible/latest/wadl/crucible.html
 */
trait CrucibleAPI {
  def getProjects(): Seq[Project]
  def getUsers(): Seq[User]
  def getRepositories(): Seq[Repository]
  
  def getReviewsInState(state: ReviewState*): Seq[ReviewSummary]
  def getReview(id: PermaId): ReviewDetails
  def getReviewDetailsWithFilter(filter: PredefinedReviewFilter): Seq[ReviewDetails]
  def getReviewDetailsWithFilter(filter: ReviewFilter): Seq[ReviewDetails]
}
