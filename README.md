# Spring Boot REST API
Spring Boot application that provides REST API for managing groups, 
teachers and ratings.
This is my first Spring Boot project, written for Programming Window and Mobile Application classes.

## Endpoints
- **POST**: /api/teacher - adds a new teacher
- **GET**: /api/teacher/csv - returns a csv file with all teachers 
- **DELETE**: /api/teacher/:id - deletes a teacher


- **POST**: /api/group - adds a new teacher group
- **GET**: /api/group - returns all groups
- **GET**: /api/group/:id/teacher - returns all teachers from group
- **GET**: /api/group/:id/fill - returns percentage of group filling
- **DELETE**: /api/group/:id - removes a group


- **POST**: /api/rate - adds a new group rate
- **DELETE**: /api/rate/:id - deletes a group rate