require 'open-uri'
require 'rexml/document'
require 'slack-ruby-client'

MAVEN_URL = "https://cboost.jfrog.io/artifactory/chartboost-mediation/com/chartboost/chartboost-mediation-sdk/maven-metadata.xml"
GITHUB_URL = "https://raw.githubusercontent.com/ChartBoost/chartboost-mediation-android-sdk-demo/main/compose/ChartboostMediationDemo/build.gradle.kts"

def fetch_url_content(url)
    begin
        URI.open(url).read
    rescue OpenURI::HTTPError => e
        puts "HTTP Error encountered: #{e.message}"
        exit(1)
    rescue StandardError => e
        puts "An error occurred while fetching data: #{e.message}"
        exit(1)
    end
end

def parse_maven_metadata(xml)
    doc = REXML::Document.new(xml)
    doc.elements['metadata/versioning/latest'].text
end

def extract_version_from_gradle(gradle_content)
    match = gradle_content.match(/implementation\("com\.chartboost:chartboost-mediation-sdk:(.+)"\)/)
    match[1] if match
end

def post_to_slack(message)
    Slack.configure do |config|
        config.token = ENV['MEDIATION_APP_SLACK_TOKEN']
    end
    client = Slack::Web::Client.new

    client.chat_postMessage(channel: '#helium-ci', text: message)
end

def main
    maven_xml = fetch_url_content(MAVEN_URL)
    latest_version = parse_maven_metadata(maven_xml)

    # TODO: The next PR will revert the "4.5.0". Hardcoding for testing purpospes.
    gradle_content = fetch_url_content(GITHUB_URL)
    current_version = "4.5.0" # extract_version_from_gradle(gradle_content)

    if current_version
        puts "Current integrated version: #{current_version}"
        puts "Latest version in production: #{latest_version}"

        # Do nothing if the current version is the latest version
        if current_version != latest_version
            message = <<~SLACK_MESSAGE
                :warning: *Mediation Demo App Update Alert* :warning:
                The Android demo app is currently using an outdated version of the Chartboost Mediation SDK.

                *Using*: `#{current_version}`
                *Latest*: `#{latest_version}`

                :arrow_right: *Action Required*: Update to the latest SDK version for the latest features and bug fixes.

                This message is auto-generated by a GitHub Action. It will be posted on a daily basis until the SDK version is updated.
            SLACK_MESSAGE

            post_to_slack(message)
        end
    else
        puts "Failed to find the current integrated version from Gradle."
    end
end

main
