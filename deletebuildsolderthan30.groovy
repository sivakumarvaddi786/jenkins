import hudson.model.*

// Specify the name of the Jenkins job you want to target
def targetJobName = "debug/print_env"

// Get the Jenkins instance
def jenkins = Jenkins.getInstance()

// Find the specific job by name
def targetJob = jenkins.getItemByFullName(targetJobName)

// Check if the job exists
if (targetJob instanceof Job) {
    // Get the current time in milliseconds
    def currentTimeMillis = System.currentTimeMillis()

    // List to store builds for deletion
    def buildsToDelete = []

    // Iterate over builds of the job
    targetJob.getBuilds().each { build ->
        // Get the build timestamp in milliseconds
        def buildTimeMillis = build.getTimeInMillis()

        // Calculate the age of the build in milliseconds
        def buildAgeMillis = currentTimeMillis - buildTimeMillis

        // Calculate the age of the build in days
        def buildAgeDays = buildAgeMillis / (1000 * 60 * 60 * 24)

        // Check if the build is older than three months (90 days)
        if (buildAgeDays > 90) {
            println("Job: ${targetJob.fullName}, Build Number: ${build.number}, Build Age: ${buildAgeDays} days")
            buildsToDelete.add(build)
        }
    }

    // Delete builds older than three months
    buildsToDelete.each { build ->
        build.delete()
        println("Deleted build: ${build.fullDisplayName}")
    }
} else {
    println("Job ${targetJobName} not found in Jenkins.")
}
