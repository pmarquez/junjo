# junjo
junjo: "sequence" in Japanese, is a microservice that returns the next code in, well, a sequence or series.

## Requirements
* JBoss Undertow 2.2.14.Final or newer
* MongoDB 4.0.9 or newer
* Spring Boot 2.6.3
* Java 17

## Issues
[GitHub Issue Tracker](https://github.com/pmarquez/junjo/issues)

## junjo OpenAPI Doc
[OpenAPI Doc] (https://github.com/pmarquez/junjo/blob/main/junjoAPI.yaml)

## Release Notes
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
