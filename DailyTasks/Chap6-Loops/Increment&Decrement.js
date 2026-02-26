let a = 5; 
console.log("value of a before b calc : "+a);
console.log("-----------------------------------------")
let b = a++ + ++a; 
/**
 * 5+1 + 1+5 
 * 6 + 6 = 12
 * */
console.log("value of a before c calc : "+a);
console.log("value of b before c calc : "+b);
console.log("-----------------------------------------")
let c = a++ + ++a + ++a + ++a + a++;  
/**
 * 7 + (1+8) + (1+9) + (1+10) + (11+1)
 * 7 + 9 + 10 + 11 + 11(no change as post incrment)  = 48
 */
console.log("value of a after c calc : "+a);
console.log("value of b after c calc : "+b);
console.log("value of c after c calc : "+c);