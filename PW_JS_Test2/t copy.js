
let statusCode=200;

    switch (200) {
        case 0:
            statusCode = "PASS - OK: Request successful";
            break;
        case 1:
            statusCode = "PASS - Created: Resource created successfully";
            break;
        case 2:
            statusCode = "PASS - OK: Request successful";
            break;
        case 3:
           statusCode = "PASS - OK: Request successful";
            break;
        case 4:
            statusCode = "PASS - OK: Request successful";
            break;
        case 5:
            statusCode = "PASS - OK: Request successful";
            break;
        case 6:
            statusCode = "PASS - OK: Request successful";
            break;
        default:
            dayName = "Unknown"; // This should never be reached due to validation
    }

    return dayName;

