//
//  Task.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "Task.h"

@implementation Task

@synthesize Deadline = ivDeadline;
@synthesize Description = ivDescription;
@synthesize Prerequisite = ivPrerequisite;
@synthesize Status = ivStatus;
@synthesize UserList = ivUserList;

- (id)init
{
    self = [super init];
    if (self) {
        // Initialization code here.
    }
    
    return self;
}

@end
