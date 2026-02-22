# Project Idea 
For context, a speedcuber is a person who practices solving a Rubik's cube as fast as possible. My idea is to make a training app for speedcubers, to help accelerate their improvement. To do this, I plan on creating an application that allows the user to randomly generate a sufficiently difficult scramble, an record their solve times. I also plan on adding additional features that will benefit the user: 
- A mini search engine: By processing strings and referencing a pre-existing set of data, the user can look for the relevant resources, whether it is websites, or YouTube videos that contain guides on a skill they are trying to improve, such as developing their turning speed, or improving their recognition speed for different patterns. 
- A scheduler: A user can record their schedule, specifying what times during the week when they are free to practice. If they would like to target specific skills, the program can generate a practice schedule, that incorporates both standard practice *and* sessions dedicated to improving those specified skills. 
- Basic Analysis of Solves: A common speedcubing technique is timing your splits, or recording the time it takes to complete each step of the solution separately, rather than timing the entire solve. At each skill level, there are certain time ranges that one's splits should fall into, and by analyzing which splits are excessively long for a user, the program can help the user diagnose what they need to work on.  <br>

I also plan on creating a class to represent each user, and another class to represent a user base. Each user will have their own username and password, and they will be required to log into the application in order to access its features. Moreover, each user will have a field to store their practice sessions, which will be filled with their times. The user base class can be used to collect anonymous statistics on the app, such as the average time spent on it per week, the standard deviation in the time spend on it per week, or what features are the most frequently used.  

<details>
<summary>What Are These Terms?</summary>
CFOP is the standard method for advanced speedcubers to solve the Rubik's cube. Intermediate cubers and beginners commonly learn heavily simplified versions of the CFOP method, and gradually build up to knowing full CFOP. It stands for: Cross, F2L, OLL, and PLL. <br><br>

The C refers to the **C**ross, or in other words, solving the edge pieces on a chosen side considered to be the "bottom". 

F2L refers to solving the rest of the **F**irst **2** **L**ayers. 

OLL is the **O**rientation of the **L**ast **L**ayer, or solving the top side. 

PLL is the **P**ermutation of the **L**ast **L**ayer, or solving the rest of the cube by rearranging the positions of the pieces in the last layer, without affecting the fact that the top layer is already solved.

You can choose any side to be the bottom, but the most common choice by far is the white side, which consequentially means that the yellow side is on top. 
</details>

## Why Am I Interested in This? 
I am avid speedcuber, and I recently broke the sub-10 second barrier. While there are several existing web apps that are used for speedcubing, I have yet to encounter one that simultaneously acts as a productivity app. It is something I wish I had access to in the past: A resource that guides the user to help them improve when they are stuck at a certain skill level, and also one that allows them to visualize their improvement over time with informative statistics. 

## User Stories
Here are user stories of the form "I want to add an X to a Y" (Phase 1):
- As a user, I want to add a practice session to my account and specify the name of the session. 
- As a user, I want to add times to each practice session, recording the time taken to execute each solution.  

Here are user stories for the other features of my project (Phase 1):
- As a user, I want to see all of my solves as pairs of scrambles and solve times in a given practice session (List all X's in a Y)
- As a user, I want to practice with consistently difficult scrambles, and have the program randomly generate a scramble for me. 
- As a user, I want to define how much time I have to practice each day of the week and allow the program to procure me a practice schedule tailored to my areas of improvement. 
- As a user, I want my splits analyzed so I know what to improve on, and also be given the right resources to use.
- As a user, I want to be able to view useful statistics, such as my session averages. 

Here are user stories for data persistence (Phase 2): 
- As a user, I want to be able to save all my solve times for each of my practice sessions to file, if I so choose. 
- As a user, I want to be able to load my solve times for each of my practice sessions from file and continue practicing, if I so choose. 

Here are user stories some extensions of my project, if time permits: 
- As a user, I want to delete certain times from my sessions if there are clear outliers (Such as if my cube broke while executing an algorithm, and did not stop the timer while I put it back together). 
- As a user, I want to add an inspection timer to mimic the realism of standardized cubing competitions. 
- As a user, I want to apply +2 penalties, a common speedcubing practice where 2 seconds is added to the time, if I make a mistake and my cube is actually 1 move from solved when I stop the timer. 

<details>
<summary> Note </summary>
It would be logical to assume that without data persistence, there is no need to record several practice sessions each time we run the program. However, consider this: If user want to target specific areas of improvement, such as drilling algorithims to improve the speed at which they are executed, they might want to record that separately from a session dedicated to standard practice (Completing and timing a full solve). <br><br>

By "session averages," I mean more than just the mean of all the times. While doing long sessions, cubers also track their current average of 5 (ao5 for short), and current average of 12 (ao12 for short), or the averages of the 5 and 12 most recent solves respectively. It is a convention in speedcubing to have the fastest and slowest times dropped in the computation of both these averages. In other words, the ao5 is the mean of the middle 3 solves out of the 5 most recent times, and the ao12 is the mean of the middle 10 solves out of the 12 most recent times. 

Also: An "inspection timer" refers to a timer that starts before the user begins timing their. In standardized competition, a user is given 15 seconds to inspect the cube and plan as many steps into the solve as possible. 
</details>