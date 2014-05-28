package com.bizo.crucible.client

import com.bizo.crucible.client.model._

trait CrucibleAPI {
  def getProjects(): Seq[Project]
  def getUsers(): Seq[User]
  
  def getReviewsInState(state: ReviewState*): Seq[ReviewSummary]
  def getReview(id: PermaId): ReviewDetails
  def getReviewDetailsWithFilter(filter: PredefinedReviewFilter): Seq[ReviewDetails]
  def getReviewDetailsWithFilter(filter: ReviewFilter): Seq[ReviewDetails]
}
