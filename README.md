<div align="center">

  <br/>
  <img src="assets/logo.png" alt="logo" width="200" height="auto" />

  <p>
    A simple sequence generator. 
  </p>
  <p>
    Meaning <b>sequence</b> in Japanese, <b>junjo</b> is a microservice that returns the next code in, well, a sequence or series.
  </p>

<!-- Badges -->
<p>
  <a href="https://github.com/pmarquez/junjo/graphs/contributors">
    <img src="https://img.shields.io/github/contributors/pmarquez/junjo" alt="contributors" />
  </a>
  <a href="">
    <img src="https://img.shields.io/github/last-commit/pmarquez/junjo" alt="last update" />
  </a>
  <a href="https://github.com/pmarquez/junjo/stargazers">
    <img src="https://img.shields.io/github/stars/pmarquez/junjo" alt="stars" />
  </a>
  <a href="https://github.com/pmarquez/junjo/issues/">
    <img src="https://img.shields.io/github/issues/pmarquez/junjo" alt="open issues" />
  </a>
  <a href="https://github.com/pmarquez/junjo/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/pmarquez/junjo.svg" alt="license" />
  </a>
</p>

<!-- h4>
    <a href="https://github.com/pmarquez/junjo/">View Demo</a>
  <span> · </span>
    <a href="https://github.com/pmarquez/junjo">Documentation</a>
  <span> · </span>
    <a href="https://github.com/pmarquez/junjo/issues/">Report Bug</a>
  <span> · </span>
    <a href="https://github.com/pmarquez/junjo/issues/">Request Feature</a>
  </h4 -->
</div>

<br />

<!-- TechStack -->
## Tech Stack
  <ul>
    <li><a href="https://undertow.io/">Undertow</a></li>
    <li><a href="https://www.mongodb.com/">MongoDB</a></li>
    <li><a href="https://www.docker.com/">Docker</a></li>
    <li><a href="https://adoptium.net/">Java 17</a></li>
  </ul>

## Issues
```bash
https://github.com/pmarquez/junjo/issues
```
## OpenAPI Doc
```bash
https://github.com/pmarquez/junjo/blob/main/junjoAPI.yaml
```
## Docker Repo
```bash
docker pull pmarquezh/junjo
```
## Release Notes

### v0.9.2
```
* Basic, but correct DTO validation schema in place.
```

### v0.9.1
```
* SequenceMapper usage is now autowired.
* Status messages NOT passed in the headers anymore. Please implement a correct validation schema.
```

### v0.9.0
```
* Moved the DTO mapping to the service layer.
* Added the first validation "(sequencePattern != null)" (SHAME ON ME TO BE DOING IT SO LATE).
* Controllers now respond with a "Message" header with an information about the status (WIP).
```

### v0.8.5
```
* Added GET /junjoAPI/1.0/sequences
* Added a SequenceDTO to info-receiving endpoints to improve security.
* Fixed all SonarLint observations.
* Ran SNYK for vulnerabilities. Fixes.
```
### v0.8.0
```bash
* Added YEAR pattern as LONG_YEAR "{YYYY}" or SHORT_YEAR "{YY}". the value defaults to the current year. If a year pattern does not comply with the expected format, it defaults to the LONG-YEAR "{YYYY}"
```
### v0.7.1
```bash
* MongoDB collection now is named "sequences".
```
### v0.7.0
```bash
* Retrieve a series of elements GET /junjoAPI/1.0/sequences/generate/:sequenceId/:quantity
```
### v0.6.1
```bash
* Code refactoring, better structure.
```
### v0.6.0
```bash
* "priorityType": "numeric" (pending thorough testing) working as expected.
```
### v0.5.0
```bash
* Added GET /junjoAPI/1.0/sequences/generate/:sequenceId
```
### v0.4.0
```bash
* Added UPDATE /junjoAPI/1.0/sequences/:sequenceId
```
### v0.3.0
```bash
* Added DELETE /junjoAPI/1.0/sequences/:sequenceId
```
### v0.2.0
```bash
* Added GET /junjoAPI/1.0/sequences/:sequenceId
```
### v0.1.0
```bash
* Added POST /junjoAPI/1.0/sequences
```