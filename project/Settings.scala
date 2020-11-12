object Settings {
  val maxSubmitFileSize = {
    val mb = 1024 * 1024
    10 * mb
  }

  val testResultsFileName = "grading-results-log"

  // time in seconds that we give scalatest for running
  val scalaTestTimeout = 850 // coursera has a 15 minute timeout anyhow
  val individualTestTimeout = 240


  val scalaTestReportFileProperty = "scalatest.reportFile"
  val scalaTestIndividualTestTimeoutProperty = "scalatest.individualTestTimeout"
  val scalaTestReadableFilesProperty = "scalatest.readableFiles"
  val scalaTestDefaultWeightProperty = "scalatest.defaultWeight"

}
