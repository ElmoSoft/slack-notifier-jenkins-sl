package org.gradiant.jenkins.slack

import hudson.tasks.test.AbstractTestResultAction
import hudson.model.Actionable

@NonCPS
String getTestSummary(titleMessage = "Test status:\n\t", boolean isQase = false) {
  def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
  def summary = ""

  if (testResultAction != null) {
    def total = isQase ? testResultAction.getTotalCount() / 2 : testResultAction.getTotalCount()
    def failed = isQase ? testResultAction.getFailCount() / 2 : testResultAction.getFailCount()
    def skipped = isQase ? testResultAction.getSkipCount() / 2 : testResultAction.getSkipCount()

    summary = titleMessage
    summary += "Passed: " + (total - failed - skipped)
    summary += ", Failed: " + failed
    summary += ", Skipped: " + skipped
  } else {
    summary = "No tests found"
  }
  return summary
}
