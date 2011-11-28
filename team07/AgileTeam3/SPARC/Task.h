//
//  Task.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Task : NSObject {

    NSDate *ivDeadline;
    
    NSMutableArray *ivUserList;
    
    NSString *ivDescription;
    NSString *ivStatus;
    
    Task *ivPrerequisite;
    
}

@property (nonatomic, retain) NSDate *Deadline;

@property (nonatomic, retain) NSMutableArray *UserList;

@property (nonatomic, retain) NSString *Description;
@property (nonatomic, assign) NSString *Status;

@property (nonatomic, retain) Task *Prerequisite;

@end