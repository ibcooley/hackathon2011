//
//  Task.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

typedef enum {
    kNoneAssigned,
    kNotStarted,
    kInProgress,
    kDone,
    kRejected
} TaskStatus;

#import <Foundation/Foundation.h>

@interface Task : NSObject {

    NSDate *ivDeadline;
    
    NSMutableArray *ivUserList;
    
    NSString *ivDescription;
    
    Task *ivPrerequisite;
    
    TaskStatus ivStatus;
    
}

@property (nonatomic, retain) NSDate *Deadline;

@property (nonatomic, retain) NSMutableArray *UserList;

@property (nonatomic, retain) NSString *Description;

@property (nonatomic, retain) Task *Prerequisite;

@property (nonatomic, assign) TaskStatus Status;

@end