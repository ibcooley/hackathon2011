//
//  User.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "User.h"

@implementation User

@synthesize EmailAddress = ivEmailAddress;
@synthesize FirstName = ivFirstName;
@synthesize LastName = ivLastName;
@synthesize UserName = ivUserName;
@synthesize UserType = ivUserType;

- (id)init
{
    self = [super init];
    if (self) {
        // Initialization code here.
    }
    
    return self;
}

@end
