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
    
    #act upon selected choice
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
            text = x.split(";")
            t = text[3][1:-1].lower().split(",")
            text[3] = t
            users.append(text)
        
        #process post data
        texts = post.split("\n")
        for x in texts:
            text = x.split(";")
            posts.append(text)
        
        
    elif inp == 2:
        #check whether an user can see a post or not
        
        #variable to check track if user can view post
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
                                    b = 1
               
                # if b was changed, the user cannot view the post; else they can view post
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

        #get friends of user
        for y in users:
            if y[0].lower() == user:
                friends = y[3]

        #new line for formatting
        print("")

        #go through each post
        for x in posts:
            b = 0
            
            #check if it wasnt written by the searched user
            if x[1].lower() != user:

                #check if post is public
                if x[2].lower() != "public":

                    #check if post publisher is a friend of searched user
                    if x[1].lower() not in friends:
                        #change variable to another value
                        b = 1

            #if variable remained unchanged, print post ID
            if b == 0:
                print(x[0])
        
    elif inp == 4:
        #get all users matching given location and outputs their display names
        
        #get location
        loc = input("Enter state location:\n").upper()

        #go through all users
        for x in users:
            #print all users matching the location
            if x[2].upper() == loc:
                print("\n", x[1])
        
    #print error message if user inputs an int that isn't 1-4    
    elif inp != 5:
        print("Option selected is nonexistent. PLease try again.")
    
