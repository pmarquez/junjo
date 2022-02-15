# junjo
junjo: "sequence" in Japanese, is a microservice that returns the next code in, well, a sequence or series.

```mermaid
sequenceDiagram
    participant dotcom
    participant iframe
    participant viewscreen
    dotcom->>iframe: loads html w/ iframe url
    iframe->>viewscreen: request template
    viewscreen->>iframe: html & javascript
    iframe->>dotcom: iframe ready
    dotcom->>iframe: set mermaid data on iframe
    iframe->>iframe: render mermaid
```

## Requirements
* JBoss Undertow 2.2.14.Final or newer
* MongoDB 4.0.9 or newer
* Spring Boot 2.6.3
* Java 17

## Issues
[GitHub Issue Tracker](https://github.com/pmarquez/junjo/issues)

## junjo OpenAPI Doc
[OpenAPI Doc] (https://github.com/pmarquez/junjo/blob/main/junjoAPI.yaml)

## junjo Docker Repo
[docker pull pmarquezh/junjo](https://hub.docker.com/r/pmarquezh/junjo)

## Release Notes
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
