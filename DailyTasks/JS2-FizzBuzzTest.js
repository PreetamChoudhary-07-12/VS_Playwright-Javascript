/**FizzBuzz Test:
 * Write a program that prints numbers from 1 to 100. However, for multiples of 3, 
 * print "Fizz" instead of the number, and for multiples of 5, print "Buzz." 
 * For numbers that are multiples of both 3 and 5, print "FizzBuzz."
 * */


for(let i= 1; i<= 100; i++){
if( i % 3 === 0 ){
console.log("Fizz");
}else if( i % 5 === 0 ){
console.log("Buzz");
}else if( i % 3 === 0 && i % 5 === 0){
console.log("FizzBuzz");
}else{
    console.log(i);    
}
}
