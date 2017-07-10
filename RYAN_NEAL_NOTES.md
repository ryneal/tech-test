# Ryan Neal Sky Betting & Gaming Technical Test

## Approach

I decided to implement the technical test using Spring MVC and Thymeleaf as the view. The driver for this was level of knowledge and amount of time I would have to complete.

I decided to manage the data file by a simple IO write of JSON Strings, which I could easily generate and parse using Googles Guava Core library.

I used Bootstrap just to make the UI look prettier.

I loosely followed a similar pattern for my PersonRepository as that of Spring JPA (just because I generally like JPA), but decided to approach the returning of an Optional rather than null handling.

## Considerations

* Security - I have included some form validation to ensure empty values are not inserted. The application correctly escapes displayed values reducing the change of XSS. CSRF protection is enabled by default when the security config is present.
* Performance - I have made some code optimisations. In terms of IO there could definitely be some improvement as I described above.
* Readability - I've followed standard Java coding practices, and also ensured that test names are descriptive of what they're actually testing. My code is structured in small methods which should be easy to understand. I have purposely avoided comments and Java Docs as I believe code should be simple enough to understand without the need for descriptive comments.
* Testability - I've aimed for a high level of test coverage (95% Method, 92% Line, 100% Class), but I've specifically focused on the tests driving value.
* Simplicity - The software is structured into small components that should be easy to understand in isolation, but also to facilitate testing.

## Future Improvements

* Handle the IO better. I considered using Redis for in-memory storage and having the backup capability to store the data to file periodically.
* Prettier UI errors. Add some custom views for the checked exceptions that are thrown.
* Separate into a REST controller with proper CRUD endpoints, and establish an Angular2 front end
