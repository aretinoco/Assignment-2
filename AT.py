# -*- coding: utf-8 -*-
"""
Created on Tue Mar 19 21:21:04 2024

@author: areti

"""

# C:\Users\areti\Downloads\Assignment-2\user-info.txt

# C:\Users\areti\Downloads\Assignment-2\post-info.txt

#input variable
inp = 0

#data variables
users = []
posts = []

#looping until program is terminated
while inp != 5:
    #menu
    print("\n1. Load input data")
    print("2. Check visibility")
    print("3. Retrieve posts")
    print("4. Search users by location")
    print("5. Exit")
    
    #get user input
    inp = int(input("Enter your choice as a number:\n"))
    
    #aacct upon selected choice
    if inp == 1:
        #load data
        
        #get files to user and post info and read them
        user = input("Enter the path of the user file:\n")
        user = open(user, "r")
        user = user.read()
        
        post = input("Enter the path of the post file:\n")
        post = open(post, "r")
        post = post.read()
        
        #process user data
        texts = user.split("\n") 
        for x in texts:
            # t = []
            text = x.split(";")
            t = text[3][1:-1].lower().split(",")
            # print(t)
            # for y in range(text[3]):
            #     print(text[3][y])
            #     t.append(text[3][y].lower()) 
            text[3] = t
            users.append(text)
            # print(x)
        print(users)
        
        #process post data
        texts = post.split("\n")
        for x in texts:
            text = x.split(";")
            posts.append(text)
            # print(x)
        print(posts)
        
        # print(users[0][[0, 1, 3]]
        
        # print(users['goldenlover1'])
        # print(posts['post3298'])
        
        
    elif inp == 2:
        #check whether an user can see a post or not
        
        b = -1
        
        #get username for user trying to access the post and post ID
        user = input("Enter username:\n").lower()
        pid = input("Enter post ID:\n").lower()
        
        #check clearance for post
        for x in posts:
            if pid == x[0].lower():
                if x[1].lower() != user:
                    if x[2].lower() != "public":
                        for y in users:
                            if y[0].lower() == user:
                                if x[1].lower() not in y[3]:
                                    # print("Access Denied")
                                    # break
                                    b = 1
                # sd
                if b == 1:
                    print("\nAccess Denied.\n")
                else:
                    print("\nAccess Permitted.\n")
    elif inp == 3:
        #get all posts a particular user can see
        
        friends = []
        # b = 0
        
        #get username for user
        user = input("Enter username:\n").lower()
        
        for y in users:
            if y[0].lower() == user:
                friends = y[3]
        
        for x in posts:
            b = 0
            if x[1].lower() != user:
                if x[2].lower() != "public":
                    if x[1].lower() not in friends:
                        print(x[1], friends)
                        b = 1
            if b == 0:
                print(x[0])
        
    elif inp == 4:
        #get all users matching given location and outputs their display names
        
        #get location
        loc = input("Enter state location:\n").upper()
        
        for x in users:
            if x[3].upper() == loc:
                print(x[2])
        
        
    elif inp != 5:
        print("Option selected is nonexistent. PLease try again.")
    