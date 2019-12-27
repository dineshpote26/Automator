# Automator

# What the Application does:
Allows user to add `url` and `schedule time` (daily(ex. 10pm daily) or at certain interval (ex. Every 10 mins)).
Users can see their previously added urls in a form of list (UI).
See them and edit them
At `schedule time`, App is supposed to make a POST request (Restful) to `url` with headers {‘Content-Type': 'application/json'}
Save the resulting reply (json format).
Perform action (mentioned below) given in the reply.
Users can go through the history results.


# In this project i am using following module

- MVVM :- architecture
- Room Database :- for Local storage
- Rectrofit :- APi calling
- Workmanger:- Scheduling for time

Step 1 (List)
![Image description](https://github.com/dineshpote26/Automator/blob/master/1.png)

Step 1 (Add Automator)
![Image description](https://github.com/dineshpote26/Automator/blob/master/2.png)

Step 3 (Edit Automator)
![Image description](https://github.com/dineshpote26/Automator/blob/master/3.png)

Step4 (Automator History)
![Image description](https://github.com/dineshpote26/Automator/blob/master/4.png)

Step5 (Automator Notificatio)
![Image description](https://github.com/dineshpote26/Automator/blob/master/5.png)
