splitwise-app
This repository contains the code for an expense sharing application called Splitwise. Splitwise allows users to add expenses and split them among different people, keeping track of balances between individuals.

Problem Statement
Create an expense sharing application where users can add expenses and split them among multiple people. The application should keep track of balances between users, showing who owes how much to whom.

Example
Suppose you live with three friends:

You (User1 with ID: u1)
User2 (u2)
User3 (u3)
User4 (u4)
This month's electricity bill is Rs. 1000. You can use the app to add the expense, stating that you paid 1000, and select all four people to split equally.

Input: u1 1000 4 u1 u2 u3 u4 EQUAL

For this transaction, everyone owes 250 to User1. The app should update the balances in each user's profile accordingly:

User2 owes User1: 250 (0+250)
User3 owes User1: 250 (0+250)
User4 owes User1: 250 (0+250)
Now, during the BBD sale on Flipkart, you buy some items for User2 and User3 as they asked you to. The total amounts for each person are different.

Input: u1 1250 2 u2 u3 EXACT 370 880

For this transaction, User2 owes 370 to User1, and User3 owes 880 to User1. The app should update the balances accordingly:

User2 owes User1: 620 (250+370)
User3 owes User1: 1130 (250+880)
User4 owes User1: 250 (250+0)
Later, you go out with your flatmates and bring your brother/sister along. User4 pays for everyone, and the expenses are split equally. However, you owe for two people.

Input: u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20

For this transaction, User1 owes 480 to User4, User2 owes 240 to User4, and User3 owes 240 to User4. The app should update the balances accordingly:

User1 owes User4: 230 (250-480)
User2 owes User1: 620 (620+0)
User2 owes User4: 240 (0+240)
User3 owes User1: 1130 (1130+0)
User3 owes User4: 240 (0+240)
Requirements
The application should fulfill the following requirements:

User: Each user should have a unique userID, name, email, and mobile number.
Expense: The expense can be split equally, exactly, or by percentage.
Users can add any amount, select any type of expense, and split it with any other users.
The percentage and amount provided can have up to two decimal places.
For percentage-based expenses, the total sum of percentage shares should be 100.
For exact expenses, the total sum of shares should be equal to the total amount.
The application should be capable of showing expenses for a single user and balances for everyone.
When asked to show balances, the application should only display balances where there is a non-zero amount owed.
The amounts should be rounded off to two decimal places.
The application should provide three types of input: expense, show all balances, and show balances for a single user.
The application should handle invalid inputs gracefully.
Input
The application accepts three types of input:

Expense: The format is EXPENSE <user-id-of-person-who-paid> <no-of-users> <space-separated-list-of-users> <EQUAL/EXACT/PERCENT> <space-separated-values-in-case-of-non-equal>. This input adds an expense to the system.
Show balances for all: The format is SHOW. This input displays balances for all users.
Show balances for a single user: The format is SHOW <user-id>. This input displays balances for the specified user.
Output
The application provides the following output:

When asked to show balances for all users, the application displays the balances in the format: <user-id-of-x> owes <user-id-of-y>: <amount>.
If there are no balances to display, the application prints "No balances" instead.
Sample Input and Output
Sample input:

sql
Copy code
SHOW
SHOW u1
EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL
SHOW u4
SHOW u1
EXPENSE u1 1250 2 u2 u3 EXACT 370 880
SHOW
EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20
SHOW u1
SHOW
Sample output:

yaml
Copy code
No balances
No balances
User4 owes User1: 250
User2 owes User1: 250
User3 owes User1: 250
User4 owes User1: 250
User2 owes User1: 620
User3 owes User1: 1130
User4 owes User1: 250
User1 owes User4: 230
User2 owes User1: 620
User3 owes User1: 1130
User1 owes User4: 230
User2 owes User1: 620
User2 owes User4: 240
User3 owes User1: 1130
User3 owes User4: 240
Optional Requirements
The following requirements are optional but can be implemented if time permits:

Adding an expense name and additional details such as notes and images.
Option to split expenses by share, allowing custom weightage for each person.
Show a passbook for a user, displaying all the transactions the user was part of.
Simplify expenses, where balances are simplified by combining multiple transactions between the same users.
Note: These optional requirements should be implemented in a way that minimally impacts the existing code and architecture.

Expectations
The expected deliverables for this project are as follows:

Functional and demonstrable code
Correct implementation of the specified functionality
Modular and readable code
Separation of concerns addressed
Code should be easily extensible and accommodate new requirements with minimal changes
A main method should be provided for easy testing of the code
(Optional) Unit tests, if possible
No need for a graphical user interface (GUI)
