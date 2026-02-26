//1️⃣ Print "Hello" 5 times
for(let i=0 ; i<5; i++)
{
    console.log("Hello");
}
console.log("------------------------------");

//2️⃣ Print numbers from 1 to 10
for(let i=1 ; i<=10; i++)
{
    console.log("numbers from 1 to 10 "+i);
}
console.log("------------------------------");
//3️⃣ Print even numbers from 1 to 20
for(let i=1 ; i<=20; i++)
{
    if(i % 2 === 0 )
    console.log("even numbers from 1 to 20 "+i);
}
console.log("------------------------------");
 // 4️⃣ Print the sum of first 10 natural numbers
 let sum = 0;
 for(let i=1 ; i<=10; i++)
{
     sum = sum + i;
}
 console.log("The sum of the first 10 natural numbers is: " + sum);
console.log("------------------------------");
 
// 5️⃣ Print the multiplication table of 5
let num = 5;
 for(let i=1 ; i<=10; i++)
{
     mul = num * i;
 console.log("The multiplication table of 5: " + mul);
}
console.log("------------------------------");