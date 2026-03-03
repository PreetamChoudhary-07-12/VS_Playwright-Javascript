// ✅ Leap Year Checker:

// Create a program that determines whether a given year is a leap year. 
// A leap year is divisible by 4, but not by 100 unless 
// it is also divisible by 400. Use an if-else statement to make this determination.
// year = 2026 , no

let year=2028;
 if(year % 4==0 && year % 100!=0 || year % 400==0 ){
    console.log(`${year} is a leap year.`);
}
else{
    console.log(`${year} is not a leap year.`);
}