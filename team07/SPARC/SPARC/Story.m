//
//  Story.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "Story.h"

@implementation Story

@synthesize Description = ivDescription;
@synthesize Owner = ivOwner;
@synthesize Status = ivStatus;
@synthesize TaskList = ivTaskList;
@synthesize Title = ivTitle;

- (id)init
{
    self = [super init];
    if (self) {
        // Initialization code here.
    }
    
    return self;
}

@end
