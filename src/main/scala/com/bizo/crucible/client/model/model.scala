package com.bizo.crucible.client.model

import java.util.Date

case class Project(
  id: Int,
  name: String,
  key: String,
  defaultRepositoryName: String,
  allowReviewersToJoin: Boolean,
  defaultReviewers: Seq[String],
  defaultDuration: Option[Int]
)

case class User(
  userName: String,
  displayName: String,
  avatarUrl: String
)

case class ReviewerState(
  userName: String, 
  completed: Boolean,
  completionStatusChangeDate: Option[Long],
  timeSpent: Option[Long]
)

case class PermaId(id: String)

trait Review {
  def permaId: PermaId
  def projectKey: String
  def name: String
  def description: Option[String]
  def author: User
  def creator: User
  def createDate: Date
  def dueDate: Option[Date]
  def closeDate: Option[Date]
}

case class ReviewSummary(
  permaId: PermaId,
  projectKey: String,
  name: String,
  description: Option[String],
  author: User,
  creator: User,
  createDate: Date,
  dueDate: Option[Date],
  closeDate: Option[Date]
) extends Review

case class ReviewDetails(
  permaId: PermaId,
  projectKey: String,
  name: String,
  description: Option[String],
  author: User,
  creator: User,
  createDate: Date,
  dueDate: Option[Date],
  closeDate: Option[Date],
  reviewers: Seq[ReviewerState]
) extends Review


sealed abstract class ReviewState
object ReviewState {
  case object Draft extends ReviewState
  case object Approval extends ReviewState
  case object Review extends ReviewState
  case object Summarize extends ReviewState
  case object Closed extends ReviewState
  case object Dead extends ReviewState
  case object Rejected extends ReviewState
  case object Unknown extends ReviewState
}