# junjo
junjo: "sequence" in Japanese, is a microservice that returns the next code in, well, a sequence or series.

## Requirements
* JBoss Undertow 2.2.14.Final or newer
* MongoDB 4.0.9 or newer
* Spring Boot 2.6.6
* Java 17

## Issues
[GitHub Issue Tracker](https://github.com/pmarquez/junjo/issues)

## junjo OpenAPI Doc
[OpenAPI Doc] (https://github.com/pmarquez/junjo/blob/main/junjoAPI.yaml)

## junjo Docker Repo
[docker pull pmarquezh/junjo](https://hub.docker.com/r/pmarquezh/junjo)

## Release Notes### v0.8.5
* Added GET /junjoAPI/1.0/sequences.
* Added a SequenceDTO to info-receiving endpoints to improve security.
* Fixed all SonarLint observations.
* Updated Spring Boot to 2.6.6.
* Ran SNYK for vulnerabilities. Fixes.

### v0.8.0
* Added YEAR pattern as LONG_YEAR "{YYYY}" or SHORT_YEAR "{YY}". the value defaults to the current year. If a year pattern does not comply with the expected format, it defaults to the LONG-YEAR "{YYYY}"

### v0.7.1
* MongoDB collection now is named "sequences".

### v0.7.0
* Retrieve a series of elements /junjoAPI/1.0/sequences/generate/:sequenceId/:quantity.

### v0.6.1
* code refactoring, better structure.

### v0.6.0
* "priorityType": "numeric" (pending thorough testing) working as expected.

### v0.5.0
* Added GET /junjoAPI/1.0/sequences/generate/:sequenceId

### v0.4.0
* Added UPDATE /junjoAPI/1.0/sequences/:sequenceId

### v0.3.0
* Added DELETE /junjoAPI/1.0/sequences/:sequenceId

### v0.2.0
* Added GET    /junjoAPI/1.0/sequences/:sequenceId

### v0.1.0
* Added POST   /junjoAPI/1.0/sequences
