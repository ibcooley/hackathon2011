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

- (NSString *)description
{
    NSMutableString *desc = [[NSMutableString alloc] init];
    [desc appendFormat:@"\nEmail: %@\n", [self EmailAddress]];
    [desc appendFormat:@"First: %@\n", [self FirstName]];
    [desc appendFormat:@"Last : %@\n", [self LastName]];
    [desc appendFormat:@"User : %@\n", [self UserName]];
    [desc appendFormat:@"Type : %@", ([self UserType] == kCustomer)?@"Customer":@"Team Member"];
    NSString *retVal = [NSString stringWithFormat:@"%@", desc];
    [desc release];
    return retVal;
}

@end
