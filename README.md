Employee Management System with File Operations and External API Integration
Problem Statement:  
Develop a Spring Boot application that manages employee records and integrates external APIs. The system should include:

1. File Operations:
    - Upload Photo: Allow users to upload employee profile photos. Validate file types (e.g., .jpg, .png) and save them to a local directory or cloud storage.
    - Export: Export employee records to a CSV or Excel file.
    - Import: Provide functionality to import employee data from a CSV or Excel file.
2. Functional Programming:
    - Use Collection Framework to manage a list of employees.
    - Implement Functional Interfaces like Predicate, Function, Consumer, and Supplier to filter, map, process, and supply data.
    - Use Lambda Expressions to simplify operations and Method References for cleaner code.
    - Apply Stream API for filtering, sorting, and transforming employee data, and utilize the Optional class to handle potential null values.
3. External API Interaction:
    - Introduce HttpClient or FeignClient to interact with an external weather API. Call the API synchronously to fetch and display the current weather for the employee’s location.
    - Use JSON mapping to deserialize the weather data.