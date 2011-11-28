//
//  StatusListItem.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "StatusListItem.h"

@implementation StatusListItem

@synthesize Deadline = ivDeadline;
@synthesize Description = ivDescription;
@synthesize ItemStatus = ivItemStatus;
@synthesize ItemType = ivItemType;
@synthesize LastUpdate = ivLastUpdate;

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
    [desc appendFormat:@"\nDeadline: %@\n", [self Deadline]];
    [desc appendFormat:@"Description: %@\n", [self Description]];
    [desc appendFormat:@"Item Status : %@\n", [self ItemStatus]];
    [desc appendFormat:@"Item Type : %@\n", [self ItemType]];
    [desc appendFormat:@"Last Update : %@", [self LastUpdate]];
    NSString *retVal = [NSString stringWithFormat:@"%@", desc];
    [desc release];
    return retVal;
}

@end
