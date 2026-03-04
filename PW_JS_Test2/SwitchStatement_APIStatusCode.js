/**Switch Statement API Status Code
 *As an SDET, you receive an API response status code and 
 need to classify it. Write a JavaScript program using a 
 switch statement that takes an HTTP status code stored in a 
 variable and prints the category and a QA-friendly message.
- 200 → "PASS - OK: Request successful"
- 201 → "PASS - Created: Resource created successfully"
- 301 → "WARNING - Moved Permanently: URL has changed"
- 400 → "FAIL - Bad Request: Check request payload"
- 401 → "FAIL - Unauthorized: Check auth token"
- 403 → "FAIL - Forbidden: Insufficient permissions"
- 404 → "FAIL - Not Found: Check endpoint URL"
- 500 → "FAIL - Internal Server Error: Backend issue"
- Any other → "UNKNOWN - Unhandled status code"
 */

let statusCode = 401;
let response;

switch (statusCode) {
    case 200:
        response = "PASS - OK: Request successful";
        break;
    case 201:
        response = "PASS - Created: Resource created successfully";
        break;
    case 301:
        response = "WARNING - Moved Permanently: URL has changed";
        break;
    case 400:
        response = "Bad Request: Check request payload";
        break;
    case 401:
        response = "FAIL - Unauthorized: Check auth token";
        break;
    case 403:
        response = "FAIL - Forbidden: Insufficient permissions";
        break;
    case 404:
        response = "FAIL - Not Found: Check endpoint URL";
        break;
    case 500:
        response = "FAIL - Internal Server Error: Backend issue";
        break;

    default:
        response = "Unknown"; // This should never be reached due to validation
}

console.log("Response: " + response);

